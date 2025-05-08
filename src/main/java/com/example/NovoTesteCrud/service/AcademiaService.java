package com.example.NovoTesteCrud.service;

import com.example.NovoTesteCrud.domain.acad.Academia;
import com.example.NovoTesteCrud.domain.acad.dto.AcademiaFilterDto;
import com.example.NovoTesteCrud.repository.AcademiaRepository;
import com.example.NovoTesteCrud.domain.acad.dto.AcademiaRequestDTO;
import com.example.NovoTesteCrud.repository.UserAcadAdminRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

@Service
public class AcademiaService {

    @Autowired
    private AcademiaRepository repository;
    @Autowired
    private UserAcadAdminRepository userAcadAdminRepository;

    public List<Academia> buscarTodasAcademias() {
        return repository.findAll();
    }

    public List<Academia> buscarTodasAcademiasFiltradas(AcademiaFilterDto filter) {
        Specification<Academia> spec = filter.toSpecification();

        return repository.findAll(spec);
    }

    @Transactional
    public Academia registrarAcademia(AcademiaRequestDTO data) {
        Academia newAcademia = new Academia();
        newAcademia.setNome(data.nome());
        newAcademia.setEndereco(data.endereco());
        newAcademia.setTelefone(data.telefone());

        return repository.save(newAcademia);
    }

    @Transactional
    public Academia atualizarAcademia(AcademiaRequestDTO data, Long id) {
        Optional<Academia> optionalAcademia = repository.findById(id);
        if (optionalAcademia.isPresent()) {
            Academia academia = optionalAcademia.get();
            academia.setNome(data.nome());
            academia.setEndereco(data.endereco());
            academia.setTelefone(data.telefone());
            return academia;
        } else {
            throw new EntityNotFoundException("Academia não encontrada!");
        }
    }

    @Transactional
    public void deletarAcademia(Long id) {
        Optional<Academia> optionalAcademia = repository.findById(id);
        if (optionalAcademia.isPresent()) {
            repository.delete(optionalAcademia.get());
        } else {
            throw new EntityNotFoundException("Academia não encontrada!");
        }
    }

    public boolean usuarioPodeGerenciar(Long academiaId) {
        var authUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = authUser.getUsername();

        return userAcadAdminRepository.findByUsuario_Email(email)
                .map(user -> user.getAcademia() != null && user.getAcademia().getId().equals(academiaId))
                .orElse(false);
    }
}
