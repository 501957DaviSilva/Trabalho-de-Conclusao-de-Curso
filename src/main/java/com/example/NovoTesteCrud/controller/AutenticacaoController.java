package com.example.NovoTesteCrud.controller;

import com.example.NovoTesteCrud.domain.autenticacao.dto.AutenticacaoRegister;
import com.example.NovoTesteCrud.domain.autenticacao.dto.AutenticacaoRequestDTO;
import com.example.NovoTesteCrud.domain.autenticacao.dto.AutenticacaoResponseDTO;
import com.example.NovoTesteCrud.domain.personal.Personal;
import com.example.NovoTesteCrud.domain.user.UserAcad;
import com.example.NovoTesteCrud.domain.useracadadmin.UserAcadAdmin;
import com.example.NovoTesteCrud.domain.useradmin.UserAdmin;
import com.example.NovoTesteCrud.repository.PersonalRepository;
import com.example.NovoTesteCrud.repository.UserAcadAdminRepository;
import com.example.NovoTesteCrud.repository.UserAcadRepository;
import com.example.NovoTesteCrud.repository.UserAdminRepository;
import com.example.NovoTesteCrud.security.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AutenticacaoController {

    @Autowired private AuthenticationManager authManager;
    @Autowired private JwtUtil jwtUtil;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private UserAcadRepository userAcadRepository;
    @Autowired private UserAdminRepository userAdminRepository;
    @Autowired private UserAcadAdminRepository userAcadAdminRepository;
    @Autowired private PersonalRepository personalRepository;

    @PostMapping("/login")
    public ResponseEntity<AutenticacaoResponseDTO> login(@RequestBody @Valid AutenticacaoRequestDTO dto) {
        try {
            // Autentica usuário
            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(dto.email(), dto.senha())
            );

            // Verifica se o usuário existe no banco e gera o token
            String token = generateTokenForUser(dto.email());

            if (token != null) {
                return ResponseEntity.ok(new AutenticacaoResponseDTO(token));
            }

            throw new UsernameNotFoundException("Usuário não encontrado");
        } catch (AuthenticationException e) {
            throw new RuntimeException("Credenciais inválidas", e);
        }
    }

    private String generateTokenForUser(String email) {
        Optional<UserAcad> userAcadOpt = userAcadRepository.findByUsuario_Email(email);
        if (userAcadOpt.isPresent()) {
            var user = userAcadOpt.get();
            return jwtUtil.generateToken(user.getUsuario().getEmail(), user.getRole());
        }

        Optional<UserAdmin> userAdminOpt = userAdminRepository.findByUsuario_Email(email);
        if (userAdminOpt.isPresent()) {
            var user = userAdminOpt.get();
            return jwtUtil.generateToken(user.getUsuario().getEmail(), user.getRole());
        }

        Optional<UserAcadAdmin> userAcadAdminOpt = userAcadAdminRepository.findByUsuario_Email(email);
        if (userAcadAdminOpt.isPresent()) {
            var user = userAcadAdminOpt.get();
            return jwtUtil.generateToken(user.getUsuario().getEmail(), user.getUsuario().getRole());
        }

        Optional<Personal> personalOpt = personalRepository.findByUsuario_Email(email);
        if (personalOpt.isPresent()) {
            var user = personalOpt.get();
            return jwtUtil.generateToken(user.getUsuario().getEmail(), user.getUsuario().getRole());
        }

        return null;
    }

    @PostMapping("/register")
    public String register(@RequestBody @Valid AutenticacaoRegister dto) {
        // Verifica se o email já existe
        if (userExists(dto.getEmail())) {
            throw new IllegalArgumentException("Email já cadastrado");
        }

        switch (dto.getTipoUsuario().toLowerCase()) {
            case "useracad" -> {
                var user = new UserAcad(
                        null,
                        dto.getNome(),
                        dto.getEmail(),
                        passwordEncoder.encode(dto.getSenha()),
                        dto.getTelefone(),
                        dto.getRoleAsEnum()
                );
                userAcadRepository.save(user);
            }

            case "useradmin" -> {
                var user = new UserAdmin(
                        null,
                        dto.getNome(),
                        dto.getEmail(),
                        passwordEncoder.encode(dto.getSenha()),
                        dto.getTelefone(),
                        dto.getRoleAsEnum()
                );
                userAdminRepository.save(user);
            }

            case "useracadadmin" -> {
                var user = new UserAcadAdmin(
                        null,
                        dto.getNome(),
                        dto.getEmail(),
                        passwordEncoder.encode(dto.getSenha()),
                        dto.getTelefone(),
                        dto.getCnpj(),
                        null,
                        dto.getRoleAsEnum()
                );
                userAcadAdminRepository.save(user);
            }

            case "personal" -> {
                var user = new Personal(
                        null,
                        dto.getNome(),
                        dto.getEmail(),
                        passwordEncoder.encode(dto.getSenha()),
                        dto.getTelefone(),
                        dto.getCref(),
                        dto.getRoleAsEnum()
                );
                personalRepository.save(user);
            }

            default -> throw new IllegalArgumentException("Tipo de usuário inválido.");
        }

        return "Usuário registrado com sucesso!";
    }

    private boolean userExists(String email) {
        return userAcadRepository.findByUsuario_Email(email).isPresent() ||
                userAdminRepository.findByUsuario_Email(email).isPresent() ||
                userAcadAdminRepository.findByUsuario_Email(email).isPresent() ||
                personalRepository.findByUsuario_Email(email).isPresent();
    }
}
