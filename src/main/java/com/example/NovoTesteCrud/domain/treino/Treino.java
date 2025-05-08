package com.example.NovoTesteCrud.domain.treino;

import com.example.NovoTesteCrud.domain.exercicios.Exercicio;
import com.example.NovoTesteCrud.domain.user.UserAcad;
import com.example.NovoTesteCrud.domain.treino.dto.enums.Hipertrofia_Performace;
import com.example.NovoTesteCrud.domain.treino.dto.enums.Cardio;
import com.example.NovoTesteCrud.domain.treino.dto.enums.Tipos;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name = "treinos")
public class Treino {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserAcad user;

    @ElementCollection(targetClass = com.example.NovoTesteCrud.domain.treino.dto.enums.Cardio.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "treino_cardio", joinColumns = @JoinColumn(name = "treino_id"))
    @Column(name = "cardio")
    private List<Cardio> cardios;

    @ElementCollection(targetClass = com.example.NovoTesteCrud.domain.treino.dto.enums.Hipertrofia_Performace.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "treino_hipertrofia", joinColumns = @JoinColumn(name = "treino_id"))
    @Column(name = "hipertrofia")
    private List<Hipertrofia_Performace> hipertrofias;

    @ElementCollection(targetClass = com.example.NovoTesteCrud.domain.treino.dto.enums.Tipos.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "treino_tipos", joinColumns = @JoinColumn(name = "treino_id"))
    @Column(name = "tipo_treino")
    private List<Tipos> tiposTreino;


    @OneToMany(mappedBy = "treino", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Exercicio> exercicios;

}
