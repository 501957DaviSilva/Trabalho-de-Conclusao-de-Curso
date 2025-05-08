package com.example.NovoTesteCrud.domain.treino.dto.enums;

public enum Tipos {
    MUSCULACAO("Musculação"),
    FUNCIONAL("Treinamento funcional"),
    CROSSFIT("CrossFit"),
    CALISTENIA("Calistenia"),
    HIIT("Treino HIIT");

    private final String nome;

    Tipos(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}