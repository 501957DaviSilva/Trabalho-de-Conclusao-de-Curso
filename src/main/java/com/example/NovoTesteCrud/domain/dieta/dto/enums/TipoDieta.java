package com.example.NovoTesteCrud.domain.dieta.dto.enums;

public enum TipoDieta {
    BULKING("Ganho de massa"),
    CUTTING("Perda de gordura");

    private final String nome;

    TipoDieta(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}
