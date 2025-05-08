package com.example.NovoTesteCrud.domain.dieta.dto;

import com.example.NovoTesteCrud.domain.dieta.Dieta;
import com.example.NovoTesteCrud.domain.dieta.dto.enums.TipoDieta;
import com.example.NovoTesteCrud.infra.FilterSpecification;
import org.springframework.data.jpa.domain.Specification;

public class DietaFilterDto implements FilterSpecification<Dieta> {

    private final TipoDieta tipoDieta;

    public DietaFilterDto(TipoDieta tipoDieta) {
        this.tipoDieta = tipoDieta;
    }

    @Override
    public Specification<Dieta> toSpecification() {
        Specification<Dieta> spec = Specification.where(null);

        if (tipoDieta != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("tipoDieta"), tipoDieta));
        }

        return spec;
    }
}