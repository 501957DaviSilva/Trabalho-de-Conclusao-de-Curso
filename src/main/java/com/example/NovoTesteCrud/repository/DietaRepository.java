package com.example.NovoTesteCrud.repository;

import com.example.NovoTesteCrud.domain.dieta.Dieta;
import com.example.NovoTesteCrud.domain.treino.Treino;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DietaRepository extends JpaRepository<Dieta, Long>, JpaSpecificationExecutor<Dieta> {}
