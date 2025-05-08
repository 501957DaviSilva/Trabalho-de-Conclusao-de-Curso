package com.example.NovoTesteCrud.repository;

import com.example.NovoTesteCrud.domain.acad.Academia;
import com.example.NovoTesteCrud.domain.treino.Treino;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TreinoRepository extends JpaRepository<Treino, Long>, JpaSpecificationExecutor<Treino> {
}
