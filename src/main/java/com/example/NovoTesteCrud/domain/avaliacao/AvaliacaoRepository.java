package com.example.NovoTesteCrud.domain.avaliacao;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {
    List<Avaliacao> buscarPorAcademiaId(Long academiaId);
    List<Avaliacao> buscarPorPersonalId(Long personalId);
    List<Avaliacao> buscarPorUsuarioId(Long userId);
}
