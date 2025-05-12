package com.example.NovoTesteCrud.domain.dieta;

import com.example.NovoTesteCrud.domain.personal.Personal;
import com.example.NovoTesteCrud.domain.user.UserAcad;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "dieta")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Dieta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    private String descricao;

    private Integer calorias;

    @Enumerated(EnumType.STRING)
    private Objetivo objetivo;

    @ManyToOne
    @JoinColumn(name = "user_acad_id")
    private UserAcad userAcad;

    @ManyToOne
    @JoinColumn(name = "personal_id")
    private Personal personal;

    public enum Objetivo {
        BULKING, CUTTING
    }
}
