package com.example.NovoTesteCrud.domain.acad.dto;

import com.example.NovoTesteCrud.domain.acad.Academia;
import com.example.NovoTesteCrud.domain.acad.dto.enums.Estrutura;
import com.example.NovoTesteCrud.domain.acad.dto.enums.Servicos;
import com.example.NovoTesteCrud.domain.acad.dto.enums.TipoAcad;
import com.example.NovoTesteCrud.infra.FilterSpecification;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Objects;

public class AcademiaFilterDto implements FilterSpecification<Academia> {

    private final List<TipoAcad> tipos;
    private final List<Estrutura> estruturas;
    private final List<Servicos> servicos;

    public AcademiaFilterDto(List<TipoAcad> tipos, List<Estrutura> estruturas, List<Servicos> servicos) {
        this.tipos = tipos;
        this.estruturas = estruturas;
        this.servicos = servicos;
    }

    @Override
    public Specification<Academia> toSpecification() {
        Specification<Academia> spec = Specification.where(null);

        if (tipos != null && !tipos.isEmpty()) {
            spec = spec.and((root, query, cb) -> root.get("tipoAcad").in(tipos));
        }

        if (estruturas != null && !estruturas.isEmpty()) {
            for (Estrutura estrutura : estruturas) {
                spec = spec.and((root, query, cb) -> cb.isMember(estrutura, root.get("estruturas")));
            }
        }

        if (servicos != null && !servicos.isEmpty()) {
            for (Servicos servico : servicos) {
                spec = spec.and((root, query, cb) -> cb.isMember(servico, root.get("servicos")));
            }
        }

        return spec;
    }
}
