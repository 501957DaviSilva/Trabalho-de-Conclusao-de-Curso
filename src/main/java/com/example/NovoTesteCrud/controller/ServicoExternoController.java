package com.example.NovoTesteCrud.controller;

import com.example.NovoTesteCrud.domain.acad.dto.ServicoExternoRequestDTO;
import com.example.NovoTesteCrud.domain.acad.dto.ServicoExternoResponseDTO;
import com.example.NovoTesteCrud.service.ServicoExternoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/servicos-externos")
@RequiredArgsConstructor
public class ServicoExternoController {

    private final ServicoExternoService service;

    @PostMapping
    @PreAuthorize("hasAnyRole('USERADMIN', 'USERACADADMIN')")
    public ResponseEntity<?> criar(@RequestBody @Valid ServicoExternoRequestDTO dto) {
        var entity = service.registrar(dto);
        return ResponseEntity.ok(Map.of(
                "message", "Serviço externo cadastrado com sucesso!",
                "servico", new ServicoExternoResponseDTO(entity)
        ));
    }
    @GetMapping("/academia/{id}")
    public ResponseEntity<List<ServicoExternoResponseDTO>> listarPorAcademia(@PathVariable Long id) {
        var list = service.listarPorAcademia(id)
                .stream()
                .map(ServicoExternoResponseDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(list);
    }

    @GetMapping
    public ResponseEntity<List<ServicoExternoResponseDTO>> listar() {
        var list = service.listarTodos()
                .stream()
                .map(ServicoExternoResponseDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }
}
