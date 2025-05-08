package com.example.NovoTesteCrud.domain.acad.dto;

import java.util.List;

public record AcademiaDetalhesDTO(
        String nome,
        String endereco,
        String telefone,
        String site,
        double avaliacao,
        int totalAvaliacoes,
        boolean abertaAgora,
        List<String> horarioFuncionamento,
        String mapsLink,
        List<String> fotos
) {}