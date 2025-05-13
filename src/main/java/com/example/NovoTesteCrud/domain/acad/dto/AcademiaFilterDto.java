package com.example.NovoTesteCrud.domain.acad.dto;

import com.example.NovoTesteCrud.domain.acad.Academia;
import com.example.NovoTesteCrud.domain.acad.enums.TipoAcad;
import com.example.NovoTesteCrud.infra.FilterSpecification;
import org.springframework.data.jpa.domain.Specification;

import java.util.Objects;

public class AcademiaFilterDto implements FilterSpecification<Academia> {
    private final TipoAcad tipoAcad;

    public AcademiaFilterDto(TipoAcad tipoAcad) {
        this.tipoAcad = tipoAcad;
    }

    @Override
    public Specification<Academia> toSpecification() {
        Specification<Academia> spec = Specification.where(null);

        if (Objects.nonNull(tipoAcad)) {
            spec = spec.and(
                    (root, query, cb) -> cb.equal(root.get("tipoAcad"), tipoAcad)
            );
        }

        return spec;
    }
}
