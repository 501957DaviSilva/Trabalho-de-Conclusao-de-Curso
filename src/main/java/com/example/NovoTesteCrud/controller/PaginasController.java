package com.example.NovoTesteCrud.controller;

import com.example.NovoTesteCrud.service.AcademiaService;
import com.example.NovoTesteCrud.service.TreinoService;
import com.example.NovoTesteCrud.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PaginasController {

    @Autowired private AcademiaService academiaService;
    @Autowired private TreinoService treinoService;
    @Autowired private UsuarioService usuarioService;

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

    @GetMapping("/perfil-usuario")
    public String perfilUsuario() {
        return "paginas/perfil_usuario";
    }

    @GetMapping("/perfil-academias")
    public ModelAndView perfilAcademias() {
        ModelAndView modelAndView = new ModelAndView("paginas/perfil_academias");
        modelAndView.addObject("gyms", this.academiaService.buscarTodasAcademias());

        return modelAndView;
    }

    @GetMapping("/perfil-personais")
    public String perfilPersonais() {
        return "paginas/perfil_personais";
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

    @GetMapping("/detalhes-academias")
    public String detalhesAcademias() {
        return "paginas/detalhes_academias";
    }

    @GetMapping("/personais")
    public ModelAndView personais() {
        ModelAndView model = new ModelAndView("paginas/personais");
        model.addObject("personais", usuarioService.buscarTodosPersonal());

        return model;
    }

    @GetMapping("/detalhes-personais")
    public String detalhesPersonais() {
        return "paginas/detalhes_personais";
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
