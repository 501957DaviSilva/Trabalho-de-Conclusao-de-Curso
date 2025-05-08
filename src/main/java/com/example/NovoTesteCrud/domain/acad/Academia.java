package com.example.NovoTesteCrud.domain.acad;

import com.example.NovoTesteCrud.domain.atvd.Atividade;
import com.example.NovoTesteCrud.domain.acad.dto.enums.TipoAcad;
import com.example.NovoTesteCrud.domain.acad.dto.enums.Estrutura;
import com.example.NovoTesteCrud.domain.acad.dto.enums.Servicos;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.List;

@Data
@Entity
@Table(name = "academias")
@NoArgsConstructor
@AllArgsConstructor
public class Academia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String endereco;
    private String telefone;
    @Enumerated(EnumType.STRING)
    private TipoAcad tipoAcad;

    @ElementCollection(targetClass = Estrutura.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "academia_estruturas", joinColumns = @JoinColumn(name = "academia_id"))
    @Column(name = "estrutura")
    private Set<Estrutura> estruturas;

    @ElementCollection(targetClass = Servicos.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "academia_servicos", joinColumns = @JoinColumn(name = "academia_id"))
    @Column(name = "servico")
    private Set<Servicos> servicos;

    @OneToMany(mappedBy = "academia", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Atividade> activities;


    public Academia(Long id) {
        this.id = id;
    }
}
