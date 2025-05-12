package com.example.NovoTesteCrud.repository;

import com.example.NovoTesteCrud.domain.dieta.Dieta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DietaRepository extends JpaRepository<Dieta, Long> {}
