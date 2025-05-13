package com.example.NovoTesteCrud.repository;

import com.example.NovoTesteCrud.domain.treino.Treino;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TreinoRepository extends JpaRepository<Treino, Long> {
}
