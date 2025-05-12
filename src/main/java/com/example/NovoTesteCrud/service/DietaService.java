package com.example.NovoTesteCrud.service;

import com.example.NovoTesteCrud.domain.dieta.Dieta;
import com.example.NovoTesteCrud.domain.dieta.dto.DietaRequestDTO;
import com.example.NovoTesteCrud.domain.personal.Personal;
import com.example.NovoTesteCrud.domain.user.UserAcad;
import com.example.NovoTesteCrud.repository.DietaRepository;
import com.example.NovoTesteCrud.repository.PersonalRepository;
import com.example.NovoTesteCrud.repository.UserAcadRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DietaService {

    @Autowired
    private DietaRepository dietaRepository;

    @Autowired
    private UserAcadRepository userAcadRepository;

    @Autowired
    private PersonalRepository personalRepository;

    public List<Dieta> listarTodasDieta() {
        return dietaRepository.findAll();
    }

    public Dieta criarDieta(DietaRequestDTO dto) {
        Dieta dieta = new Dieta();
        dieta.setTitulo(dto.titulo());
        dieta.setDescricao(dto.descricao());
        dieta.setCalorias(dto.calorias());
        dieta.setObjetivo(dto.objetivo());

        if (dto.userAcadId() != null) {
            UserAcad userAcad = userAcadRepository.findById(dto.userAcadId())
                    .orElseThrow(() -> new EntityNotFoundException("Usuário acadêmico não encontrado"));
            dieta.setUserAcad(userAcad);
        } else if (dto.personalId() != null) {
            Personal personal = personalRepository.findById(dto.personalId())
                    .orElseThrow(() -> new EntityNotFoundException("Personal não encontrado"));
            dieta.setPersonal(personal);
        } else {
            throw new IllegalArgumentException("É necessário fornecer userAcadId ou personalId.");
        }

        return dietaRepository.save(dieta);
    }

    public Dieta atualizarDieta(Long id, DietaRequestDTO dto) {
        Dieta dieta = dietaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Dieta não encontrada"));
        dieta.setTitulo(dto.titulo());
        dieta.setDescricao(dto.descricao());
        dieta.setCalorias(dto.calorias());
        dieta.setObjetivo(dto.objetivo());
        return dietaRepository.save(dieta);
    }

    public void deletarDieta(Long id) {
        dietaRepository.deleteById(id);
    }

    public boolean usuarioPodeAlterar(Long dietaId) {
        var authUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = authUser.getUsername();

        return dietaRepository.findById(dietaId)
                .map(dieta -> {
                    if (dieta.getUserAcad() != null) {
                        return dieta.getUserAcad().getUsuario().getEmail().equals(email);
                    } else if (dieta.getPersonal() != null) {
                        return dieta.getPersonal().getUsuario().getEmail().equals(email);
                    }
                    return false;
                })
                .orElse(false);
    }
}
