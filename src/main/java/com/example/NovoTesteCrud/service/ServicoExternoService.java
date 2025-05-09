package com.example.NovoTesteCrud.service;

import com.example.NovoTesteCrud.domain.acad.Academia;
import com.example.NovoTesteCrud.domain.acad.ServicoExterno;
import com.example.NovoTesteCrud.domain.acad.dto.ServicoExternoRequestDTO;
import com.example.NovoTesteCrud.repository.AcademiaRepository;
import com.example.NovoTesteCrud.repository.ServicoExternoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServicoExternoService {

    private final ServicoExternoRepository servicoRepository;
    private final AcademiaRepository academiaRepository;

    @Transactional
    public ServicoExterno registrar(ServicoExternoRequestDTO dto) {
        Academia academia = academiaRepository.findById(dto.academiaId())
                .orElseThrow(() -> new EntityNotFoundException("Academia não encontrada"));

        ServicoExterno s = ServicoExterno.builder()
                .nome(dto.nome())
                .tipo(dto.tipo())
                .descricao(dto.descricao())
                .contato(dto.contato())
                .link(dto.link())
                .academia(academia)
                .build();

        return servicoRepository.save(s);
    }

    public List<ServicoExterno> listarTodos() {
        return servicoRepository.findAll();
    }
    public List<ServicoExterno> listarPorAcademia(Long academiaId) {
        return servicoRepository.findByAcademiaId(academiaId);
    }
}
