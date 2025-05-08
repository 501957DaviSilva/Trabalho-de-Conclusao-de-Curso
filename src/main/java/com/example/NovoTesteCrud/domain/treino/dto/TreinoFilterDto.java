package com.example.NovoTesteCrud.domain.treino.dto;

import com.example.NovoTesteCrud.domain.treino.Treino;
import com.example.NovoTesteCrud.domain.treino.dto.enums.Cardio;
import com.example.NovoTesteCrud.domain.treino.dto.enums.Hipertrofia_Performace;
import com.example.NovoTesteCrud.domain.treino.dto.enums.Tipos;
import com.example.NovoTesteCrud.infra.FilterSpecification;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class TreinoFilterDto implements FilterSpecification<Treino> {

    private final List<Cardio> cardios;
    private final List<Hipertrofia_Performace> hipertrofias;
    private final List<Tipos> tiposTreino;

    public TreinoFilterDto(List<Cardio> cardios, List<Hipertrofia_Performace> hipertrofias, List<Tipos> tiposTreino) {
        this.cardios = cardios;
        this.hipertrofias = hipertrofias;
        this.tiposTreino = tiposTreino;
    }

    @Override
    public Specification<Treino> toSpecification() {
        Specification<Treino> spec = Specification.where(null);

        if (cardios != null && !cardios.isEmpty()) {
            for (Cardio cardio : cardios) {
                spec = spec.and((root, query, cb) -> cb.isMember(cardio, root.get("cardios")));
            }
        }

        if (hipertrofias != null && !hipertrofias.isEmpty()) {
            for (Hipertrofia_Performace h : hipertrofias) {
                spec = spec.and((root, query, cb) -> cb.isMember(h, root.get("hipertrofias")));
            }
        }

        if (tiposTreino != null && !tiposTreino.isEmpty()) {
            for (Tipos t : tiposTreino) {
                spec = spec.and((root, query, cb) -> cb.isMember(t, root.get("tiposTreino")));
            }
        }

        return spec;
    }
}
