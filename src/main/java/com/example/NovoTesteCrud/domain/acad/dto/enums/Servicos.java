package com.example.NovoTesteCrud.domain.acad.dto.enums;

public enum Servicos {
    AVALIACAO_INICIAL("Avaliação física inicial"),
    NUTRICIONAL("Acompanhamento nutricional"),
    PERSONAL("Personal trainer disponível"),
    TREINOS("Treinos personalizados"),
    APP("Aplicativo da academia"),
    WHATSAPP("Atendimento via WhatsApp"),
    VINT_QUATRO_HORAS("Atendimento 24h");

    private final String nome;

    Servicos(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}
