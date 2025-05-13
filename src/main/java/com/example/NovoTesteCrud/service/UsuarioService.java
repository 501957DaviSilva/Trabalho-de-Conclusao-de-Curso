package com.example.NovoTesteCrud.service;

import com.example.NovoTesteCrud.domain.personal.Personal;
import com.example.NovoTesteCrud.repository.PersonalRepository;
import com.example.NovoTesteCrud.domain.personal.RequestPersonal;
import com.example.NovoTesteCrud.domain.user.RequestUserAcad;
import com.example.NovoTesteCrud.domain.user.UserAcad;
import com.example.NovoTesteCrud.repository.UserAcadRepository;
import com.example.NovoTesteCrud.domain.useracadadmin.RequestUserAcadAdmin;
import com.example.NovoTesteCrud.domain.useracadadmin.UserAcadAdmin;
import com.example.NovoTesteCrud.repository.UserAcadAdminRepository;
import com.example.NovoTesteCrud.domain.useradmin.RequestUserAdmin;
import com.example.NovoTesteCrud.domain.useradmin.UserAdmin;
import com.example.NovoTesteCrud.repository.UserAdminRepository;
import com.example.NovoTesteCrud.domain.userbase.Usuario;
import com.example.NovoTesteCrud.domain.userbase.dto.IRequestUsuario;
import com.example.NovoTesteCrud.domain.acad.Academia;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UserAcadRepository userAcadRepository;
    @Autowired
    private UserAdminRepository userAdminRepository;
    @Autowired
    private UserAcadAdminRepository userAcadAdminRepository;
    @Autowired
    private PersonalRepository personalRepository;

    public List<UserAcad> buscarTodosUserAcad() {
        return userAcadRepository.findAll();
    }

    public List<UserAdmin> buscarTodosUserAdmin() {
        return userAdminRepository.findAll();
    }

    public List<UserAcadAdmin> buscarTodosUserAcadAdmin() {
        return userAcadAdminRepository.findAll();
    }

    public List<Personal> buscarTodosPersonal() {
        return personalRepository.findAll();
    }

    public Optional<Personal> buscarPersonalPorId(Long id) {
        return personalRepository.findById(id);
    }

    public Optional<Usuario> buscarUsuarioPorEmail(String email) {
        Optional<UserAcad> userAcad = userAcadRepository.findByUsuario_Email(email);
        if (userAcad.isPresent()) return Optional.of(userAcad.get().getUsuario());

        Optional<UserAdmin> userAdmin = userAdminRepository.findByUsuario_Email(email);
        if (userAdmin.isPresent()) return Optional.of(userAdmin.get().getUsuario());

        Optional<UserAcadAdmin> userAcadAdmin = userAcadAdminRepository.findByUsuario_Email(email);
        if (userAcadAdmin.isPresent()) return Optional.of(userAcadAdmin.get().getUsuario());

        Optional<Personal> personal = personalRepository.findByUsuario_Email(email);
        if (personal.isPresent()) return Optional.of(personal.get().getUsuario());

        return Optional.empty();
    }


    public void registrarUsuario(IRequestUsuario data) {
        switch (data.tipoUsuario().toLowerCase()) {
            case "useracad" -> {
                if (data instanceof RequestUserAcad userAcadData) {
                    userAcadRepository.save(new UserAcad(
                            userAcadData.id(),
                            userAcadData.name(),
                            userAcadData.email(),
                            userAcadData.senha(),
                            userAcadData.telefone(),
                            userAcadData.role()
                    ));
                }
            }
            case "useradmin" -> {
                if (data instanceof RequestUserAdmin userAdminData) {
                    userAdminRepository.save(new UserAdmin(
                            userAdminData.id(),
                            userAdminData.name(),
                            userAdminData.email(),
                            userAdminData.senha(),
                            userAdminData.telefone(),
                            userAdminData.role()
                    ));
                }
            }
            case "useracadadmin" -> {
                if (data instanceof RequestUserAcadAdmin adminData) {
                    userAcadAdminRepository.save(new UserAcadAdmin(
                            adminData.id(),
                            adminData.name(),
                            adminData.email(),
                            adminData.senha(),
                            adminData.telefone(),
                            adminData.cnpj(),
                            new Academia(adminData.academiaId()),
                            adminData.role()
                    ));
                }
            }
            case "personal" -> {
                if (data instanceof RequestPersonal personalData) {
                    personalRepository.save(new Personal(
                            personalData.id(),
                            personalData.name(),
                            personalData.email(),
                            personalData.senha(),
                            personalData.telefone(),
                            personalData.cref(),
                            personalData.role()
                    ));
                }
            }
            default -> throw new IllegalArgumentException("Tipo de usuário inválido: " + data.tipoUsuario());
        }
    }

    @Transactional
    public void atualizarUsuario(Long id, IRequestUsuario data) {
        switch (data.tipoUsuario().toLowerCase()) {
            case "useracad" -> {
                UserAcad user = userAcadRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("Usuário Acad não encontrado!"));
                if (data instanceof RequestUserAcad requestAcad) {
                    user.atualizarDados(requestAcad);
                    userAcadRepository.save(user);
                }
            }
            case "useradmin" -> {
                UserAdmin user = userAdminRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("Usuário Admin não encontrado!"));
                if (data instanceof RequestUserAdmin requestAdmin) {
                    user.atualizarDados(requestAdmin);
                    userAdminRepository.save(user);
                }
            }
            case "useracadadmin" -> {
                UserAcadAdmin user = userAcadAdminRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("Usuário Acad Admin não encontrado!"));
                if (data instanceof RequestUserAcadAdmin requestAcadAdmin) {
                    user.atualizarDados(requestAcadAdmin);
                    userAcadAdminRepository.save(user);
                }
            }
            case "personal" -> {
                Personal user = personalRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("Personal não encontrado!"));
                if (data instanceof RequestPersonal requestPersonal) {
                    user.atualizarDados(requestPersonal);
                    personalRepository.save(user);
                }
            }
            default -> throw new IllegalArgumentException("Tipo de usuário inválido: " + data.tipoUsuario());
        }
    }

    @Transactional
    public void deletarUsuario(Long id, String tipoUsuario) {
        switch (tipoUsuario.toLowerCase()) {
            case "useracad" -> userAcadRepository.deleteById(id);
            case "useradmin" -> userAdminRepository.deleteById(id);
            case "useracadadmin" -> userAcadAdminRepository.deleteById(id);
            case "personal" -> personalRepository.deleteById(id);
            default -> throw new IllegalArgumentException("Tipo de usuário inválido: " + tipoUsuario);
        }
    }
}
