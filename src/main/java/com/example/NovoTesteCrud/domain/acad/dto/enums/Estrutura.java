package com.example.NovoTesteCrud.domain.acad.dto.enums;

public enum Estrutura {
    ESTACIONAMENTO("Estacionamento próprio"),
    VESTIARIO("Vestiário masculino e feminino"),
    ACESSIBILIDADE("Acessibilidade"),
    BEBEDOURO("Bebedouro / Água filtrada"),
    ARMARIOS("Armários individuais"),
    AR_CONDICIONADO("Ar-condicionado"),
    WIFI("Wi-Fi gratuito");

    private final String nome;

    Estrutura(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}
