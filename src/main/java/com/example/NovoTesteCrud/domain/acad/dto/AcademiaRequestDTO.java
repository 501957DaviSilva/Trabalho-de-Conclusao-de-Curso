package com.example.NovoTesteCrud.domain.acad.dto;

import jakarta.validation.constraints.NotBlank;

public record AcademiaRequestDTO(
        @NotBlank(message = "O nome é obrigatório") String nome,
        @NotBlank(message = "O endereço é obrigatório") String endereco,
        @NotBlank(message = "O telefone é obrigatório") String telefone,
        @NotBlank(message = "CNPJ é obrigatorio") String cnpj,
        Long id
) {}
