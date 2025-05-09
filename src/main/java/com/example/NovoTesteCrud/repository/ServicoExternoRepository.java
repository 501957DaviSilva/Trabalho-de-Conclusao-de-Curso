package com.example.NovoTesteCrud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.NovoTesteCrud.domain.acad.ServicoExterno;
import java.util.List;


public interface ServicoExternoRepository extends JpaRepository<ServicoExterno, Long>{
    List<ServicoExterno> findByAcademiaId(Long academiaId);
}
