package com.example.NovoTesteCrud.domain.treino.dto.enums;

public enum Cardio {
    CORRIDA("Esteira"),
    BICICLETA("Bicicleta ergométrica"),
    ESCADA("Escada simuladora"),
    REMO("Simulador de remo"),
    ELIPTICO("Elíptico");

    private final String nome;

    Cardio(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}
