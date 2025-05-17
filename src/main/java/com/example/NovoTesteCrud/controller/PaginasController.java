package com.example.NovoTesteCrud.controller;

import com.example.NovoTesteCrud.domain.user.UserAcad;
import com.example.NovoTesteCrud.domain.userbase.Role;
import com.example.NovoTesteCrud.domain.userbase.Usuario;
import org.springframework.web.bind.annotation.ModelAttribute;
import com.example.NovoTesteCrud.domain.dieta.dto.DietaFilterDto;
import com.example.NovoTesteCrud.domain.dieta.enums.TipoDieta;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;
import com.example.NovoTesteCrud.domain.dieta.dto.DietaRequestDTO;
import com.example.NovoTesteCrud.domain.dieta.dto.DietaResponseDTO;
import com.example.NovoTesteCrud.security.JwtUtil;
import com.example.NovoTesteCrud.service.AcademiaService;
import com.example.NovoTesteCrud.service.TreinoService;
import com.example.NovoTesteCrud.service.DietaService;
import com.example.NovoTesteCrud.service.UsuarioService;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class PaginasController {

    @Autowired private AcademiaService academiaService;
    @Autowired private TreinoService treinoService;
    @Autowired private UsuarioService usuarioService;
    @Autowired private DietaService dietaService;
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
    @GetMapping("/pagina-dietas")
    public ModelAndView paginaDietas(@RequestParam(required = false) TipoDieta tipoDieta) {
        var filtro = new DietaFilterDto(tipoDieta);
        var dietas = dietaService.buscarTodasDietasFiltradas(filtro)
                .stream().map(DietaResponseDTO::new).toList();

        ModelAndView model = new ModelAndView("paginas/dietas");
        model.addObject("dietas", dietas);
        return model;
    }

    @GetMapping("/dietas/{id}")
    public ModelAndView detalhesDieta(
            @PathVariable Long id,
            @CookieValue(value = "token", required = false) String token) {

        var dieta = this.dietaService.buscarDietaPorId(id);
        if (dieta == null) {
            return new ModelAndView("redirect:/pagina-dietas");
        }

        ModelAndView model = new ModelAndView("paginas/detalhes_dietas");
        model.addObject("dieta", dieta);

        try {
            String email = jwtUtil.extractEmail(token);
            boolean temPermissao = (dieta.getUserAcad() != null && dieta.getUserAcad().getUsuario().getEmail().equals(email)) ||
                    (dieta.getPersonal() != null && dieta.getPersonal().getUsuario().getEmail().equals(email));
            model.addObject("temPermissao", temPermissao);
        } catch (Exception e) {
            model.addObject("temPermissao", false);
        }

        return model;
    }



    @GetMapping("/cadastrar_dietas")
    public ModelAndView formCadastrarDieta(@CookieValue(value = "token", required = false) String token) {
        var model = new ModelAndView("paginas/cadastrar_dietas");
        model.addObject("dietaRequest", new DietaRequestDTO(null, "", "", 0, null, null, null, null));

        try {
            String email = jwtUtil.extractEmail(token);
            var role = jwtUtil.extractRole(token);

            if (role.name().contains("USERACAD")) {
                var userAcad = usuarioService.buscarTodosUserAcad()
                        .stream()
                        .filter(u -> u.getUsuario().getEmail().equals(email))
                        .findFirst()
                        .orElse(null);
                if (userAcad != null) model.addObject("userAcadId", userAcad.getId());
            }

            if (role.name().contains("PERSONAL")) {
                var personal = usuarioService.buscarTodosPersonal()
                        .stream()
                        .filter(p -> p.getUsuario().getEmail().equals(email))
                        .findFirst()
                        .orElse(null);
                if (personal != null) model.addObject("personalId", personal.getId());
            }

        } catch (Exception ignored) {
        }
        return model;
    }
    @PostMapping(value = "/dietas", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> salvarDietaJson(
            @RequestBody DietaRequestDTO dto,
            @CookieValue(value = "token", required = false) String token) {
        try {

            String email = jwtUtil.extractEmail(token);
            var role = jwtUtil.extractRole(token);

            if (role.name().contains("USERACAD")) {
                var userAcad = usuarioService.buscarTodosUserAcad()
                        .stream().filter(u -> u.getUsuario().getEmail().equals(email)).findFirst().orElse(null);
                if (userAcad != null) {
                    dto = new DietaRequestDTO(dto.id(), dto.titulo(), dto.descricao(), dto.calorias(), dto.objetivo(),
                            dto.tipoDieta(), userAcad.getId(), null);
                }
            }

            if (role.name().contains("PERSONAL")) {
                var personal = usuarioService.buscarTodosPersonal()
                        .stream().filter(p -> p.getUsuario().getEmail().equals(email)).findFirst().orElse(null);
                if (personal != null) {
                    dto = new DietaRequestDTO(dto.id(), dto.titulo(), dto.descricao(), dto.calorias(), dto.objetivo(),
                            dto.tipoDieta(), null, personal.getId());
                }
            }

            var nova = dietaService.criarDieta(dto);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .header("Location", "/dietas/" + nova.getId())
                    .build();

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



    @GetMapping("/dietas/{id}/editar")
    public ModelAndView editarDieta(@PathVariable Long id, @CookieValue(value = "token", required = false) String token) {

        var dieta = dietaService.listarTodasDieta().stream()
                .filter(d -> d.getId().equals(id)).findFirst().orElse(null);
        if (dieta == null) return new ModelAndView("redirect:/pagina-dietas");

        var model = new ModelAndView("paginas/cadastrar_dietas");
        model.addObject("dietaRequest", new DietaRequestDTO(
                dieta.getId(),
                dieta.getTitulo(),
                dieta.getDescricao(),
                dieta.getCalorias(),
                dieta.getObjetivo(),
                dieta.getTipoDieta(),
                dieta.getUserAcad() != null ? dieta.getUserAcad().getId() : null,
                dieta.getPersonal() != null ? dieta.getPersonal().getId() : null
        ));
        model.addObject("isUpdate", true);
        return model;
    }

    @PostMapping("/dietas/{id}/editar")
    public String atualizarDieta(
            @PathVariable Long id,
            @ModelAttribute DietaRequestDTO dto,
            @CookieValue(value = "token", defaultValue = "") String token
    ) {
        if (token.isBlank()) return "redirect:/login";

        String email = jwtUtil.extractEmail(token);
        var role = jwtUtil.extractRole(token);

        if (role.name().contains("USERACAD")) {
            var userAcad = usuarioService.buscarTodosUserAcad()
                    .stream()
                    .filter(u -> u.getUsuario().getEmail().equals(email))
                    .findFirst()
                    .orElse(null);
            if (userAcad != null) {
                dto = new DietaRequestDTO(dto.id(), dto.titulo(), dto.descricao(), dto.calorias(), dto.objetivo(), dto.tipoDieta(), userAcad.getId(), null);
            } else {
                return "redirect:/login";
            }
        } else if (role.name().contains("PERSONAL")) {
            var personal = usuarioService.buscarTodosPersonal()
                    .stream()
                    .filter(p -> p.getUsuario().getEmail().equals(email))
                    .findFirst()
                    .orElse(null);
            if (personal != null) {
                dto = new DietaRequestDTO(dto.id(), dto.titulo(), dto.descricao(), dto.calorias(), dto.objetivo(), dto.tipoDieta(), null, personal.getId());
            } else {
                return "redirect:/login";
            }
        } else {
            return "redirect:/login";
        }

        dietaService.atualizarDieta(id, dto);
        return "redirect:/dietas/" + id + "/detalhes";
    }

    @PostMapping("/dietas/{id}/deletar")
    public String deletarDieta(@PathVariable Long id) {
        dietaService.deletarDieta(id);
        return "redirect:/pagina-dietas";
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

    @GetMapping("/atualizar-usuario")
    public ModelAndView atualizarUser(
            @CookieValue(defaultValue = "", value = "token") String token
    ) {
        if (token == null || token.isBlank()) {
            return new ModelAndView("redirect:/login");
        }

        try {
            Role role = jwtUtil.extractRole(token);
            ModelAndView model = new ModelAndView("paginas/atualizar_usuario");
            String email = jwtUtil.extractEmail(token);
            switch (role) {
                case PERSONAL -> model.addObject("personal",
                        this.usuarioService.buscarTodosPersonal()
                                .stream()
                                .filter(p -> p.getEmail().equals(email))
                                .findFirst()
                                .orElse(null)
                );
                case USERADMIN -> model.addObject("userAdmin",
                        this.usuarioService.buscarTodosUserAdmin()
                                .stream()
                                .filter(p -> p.getEmail().equals(email))
                                .findFirst()
                                .orElse(null)
                );
                case USERACAD -> model.addObject("userAcad",
                        this.usuarioService.buscarTodosUserAcad()
                                .stream()
                                .filter(p -> p.getEmail().equals(email))
                                .findFirst()
                                .orElse(null)
                );
                case USERACADADMIN -> model.addObject("userAcadAdmin",
                        this.usuarioService.buscarTodosUserAcadAdmin()
                                .stream()
                                .filter(p -> p.getEmail().equals(email))
                                .findFirst()
                                .orElse(null)
                );
            }

            return model;
        } catch (Exception e) {
            return new ModelAndView("redirect:/login");
        }
    }
}
