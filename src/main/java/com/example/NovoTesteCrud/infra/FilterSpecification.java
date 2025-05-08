package com.example.NovoTesteCrud.infra;

import org.springframework.data.jpa.domain.Specification;

public interface FilterSpecification<T> {
    public Specification<T> toSpecification();
}
