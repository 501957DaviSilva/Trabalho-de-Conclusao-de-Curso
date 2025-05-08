package com.example.NovoTesteCrud.domain.treino.dto.enums;

public enum Hipertrofia_Performace {
    PESO_LIVRE("Área de peso livre"),
    MAQUINAS("Máquinas de musculação"),
    SUPINO("Bancos de supino"),
    ANILHAS("Suporte para anilhas"),
    RACKS("Power racks");

    private final String nome;

    Hipertrofia_Performace(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}
