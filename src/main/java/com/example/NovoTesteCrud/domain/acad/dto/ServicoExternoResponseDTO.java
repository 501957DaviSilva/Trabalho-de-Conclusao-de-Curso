package com.example.NovoTesteCrud.domain.acad.dto;

import com.example.NovoTesteCrud.domain.acad.ServicoExterno;

public record ServicoExternoResponseDTO(
        Long id,
        String nome,
        String tipo,
        String descricao,
        String contato,
        String link,
        Long academiaId
) {
    public ServicoExternoResponseDTO(ServicoExterno s) {
        this(s.getId(), s.getNome(), s.getTipo(), s.getDescricao(), s.getContato(), s.getLink(), s.getAcademia().getId());
    }
}