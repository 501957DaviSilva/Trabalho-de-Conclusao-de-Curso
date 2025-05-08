package com.example.NovoTesteCrud.domain.acad.dto;

public record AcademiaExternaDTO(
        String nome,
        String endereco,
        String placeId,
        double avaliacao,
        int totalAvaliacoes,
        String photoReference,
        String status
) {}
