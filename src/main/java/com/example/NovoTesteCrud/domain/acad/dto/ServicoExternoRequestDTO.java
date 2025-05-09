package com.example.NovoTesteCrud.domain.acad.dto;

public record ServicoExternoRequestDTO(
        String nome,
        String tipo,
        String descricao,
        String contato,
        String link,
        Long academiaId
) {}
