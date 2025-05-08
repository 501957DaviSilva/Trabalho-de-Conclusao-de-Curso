package com.example.NovoTesteCrud.domain.acad.dto.enums;

public enum TipoAcad {
    CONVENCIONAL("Convencional"),
    CROSSFIT("CROSSFIT");

    private final String nome;

    TipoAcad(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}

