package com.example.NovoTesteCrud.controller;

import com.example.NovoTesteCrud.domain.acad.Academia;
import com.example.NovoTesteCrud.domain.useracadadmin.UserAcadAdmin;
import com.example.NovoTesteCrud.domain.userbase.Role;
import com.example.NovoTesteCrud.security.JwtUtil;
import com.example.NovoTesteCrud.service.AcademiaService;
import com.example.NovoTesteCrud.service.TreinoService;
import com.example.NovoTesteCrud.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class PaginasController {

    @Autowired private AcademiaService academiaService;
    @Autowired private TreinoService treinoService;
    @Autowired private UsuarioService usuarioService;
    @Autowired private JwtUtil jwtUtil;

    @GetMapping("/")
    public String index() {
        return "paginas/index";
    }

    @GetMapping("/login")
    public String login() {
        return "paginas/login";
    }

    @GetMapping("/cadastro")
    public String cadastro() {
        return "paginas/cadastro";
    }

    @GetMapping("/reset-senha")
    public String resetSenha() {
        return "paginas/reset-senha";
    }

    @GetMapping("/troca-senha")
    public String trocaSenha() {
        return "paginas/troca_senha";
    }

    @GetMapping("/senha")
    public String senha() {
        return "paginas/senha";
    }

    @GetMapping("/perfil")
    public ModelAndView perfil(
            @CookieValue(defaultValue = "", value = "token") String token
    ) {
        if (token == null || token.isBlank()) {
            return new ModelAndView("redirect:/login");
        }

        try {
            Role role = jwtUtil.extractRole(token);

            String email = jwtUtil.extractEmail(token);
            var user = this.usuarioService.buscarUsuarioPorEmail(email).orElseThrow(Exception::new);

            return switch (role) {
                case USERADMIN -> new ModelAndView("paginas/perfil_usuario").addObject("user", user);
                case USERACAD -> new ModelAndView("paginas/perfil_useracad").addObject("user", user);
                case USERACADADMIN -> new ModelAndView("paginas/perfil_academias")
                        .addObject("gyms", this.academiaService.buscarTodasAcademias())
                        .addObject("user", user);
                case PERSONAL -> new ModelAndView("paginas/perfil_personais").addObject("user", user);
                default -> new ModelAndView("paginas/index");
            };
        } catch (Exception e) {
            return new ModelAndView("redirect:/login");
        }
    }

    @GetMapping("/academias")
    public ModelAndView academias() {
        ModelAndView model = new ModelAndView("paginas/academias");

        model.addObject("gyms", this.academiaService.buscarTodasAcademias());

        return model;
    }

    @GetMapping("/cadastrar-academia")
    public String cadastrarAcademia() {
        return "paginas/cadastrar_academia";
    }

    @GetMapping("/academias/{id}")
    public ModelAndView detalhesAcademias(
            @PathVariable Long id
    ) {
        ModelAndView model = new ModelAndView("paginas/detalhes_academias");

        model.addObject("gym", this.academiaService.buscarAcademiaPorId(id));

        return model;
    }

    @GetMapping("/personais")
    public ModelAndView personais() {
        ModelAndView model = new ModelAndView("paginas/personais");
        model.addObject("personais", usuarioService.buscarTodosPersonal());

        return model;
    }

    @GetMapping("/personais/{id}")
    public ModelAndView detalhesPersonais(
            @PathVariable Long id
    ) {
        ModelAndView model = new ModelAndView("paginas/detalhes_personais");
        model.addObject("personal", this.usuarioService.buscarPersonalPorId(id));

        return model;
    }

    @GetMapping("/treinos")
    public ModelAndView treinos() {
        ModelAndView model = new ModelAndView("paginas/treinos");
        model.addObject("trainings", this.treinoService.buscarTodosTreinos());

        return model;
    }

    @GetMapping("/detalhes-treinos")
    public String detalhesTreinos() {
        return "paginas/detalhes_treinos";
    }

    @GetMapping("/cadastrar-treino")
    public String cadastrarTreino() {
        return "paginas/cadastrar_treino";
    }
}
