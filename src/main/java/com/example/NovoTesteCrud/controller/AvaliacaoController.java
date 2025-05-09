package com.example.NovoTesteCrud.controller;

import com.example.NovoTesteCrud.domain.avaliacao.dto.AvaliacaoRequestDTO;
import com.example.NovoTesteCrud.domain.avaliacao.dto.AvaliacaoResponseDTO;
import com.example.NovoTesteCrud.service.AvaliacaoService;
import jakarta.validation.Valid;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/avaliacoes")
public class AvaliacaoController {

    @Autowired
    private AvaliacaoService avaliacaoService;

    @PreAuthorize("hasRole('USERADMIN')")
    @GetMapping
    public ResponseEntity<List<AvaliacaoResponseDTO>> buscarTodasAvaliacoes() {
        List<AvaliacaoResponseDTO> avaliacoes = avaliacaoService.buscarTodasAvaliacoes()
                .stream().map(AvaliacaoResponseDTO::new).toList();
        return ResponseEntity.ok(avaliacoes);
    }

    @PreAuthorize("@avaliacaoService.usuarioPodeVisualizar(#id) or hasRole('USERADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<AvaliacaoResponseDTO> buscarAvaliacaoPorId(@PathVariable Long id) {
        AvaliacaoResponseDTO feedback = new AvaliacaoResponseDTO(avaliacaoService.buscarAvaliacaoPorId(id));
        return ResponseEntity.ok(feedback);
    }

//    @PreAuthorize("hasAnyRole('USERADMIN', 'USERACADADMIN', 'USERACAD', 'PERSONAL')")
    @GetMapping("/academia/{academiaId}")
    public ResponseEntity<List<AvaliacaoResponseDTO>> buscarAvaliacaoPorAcademia(@PathVariable Long academiaId) {
        List<AvaliacaoResponseDTO> avaliacoes = avaliacaoService.buscarAvaliacaoPorAcademia(academiaId)
                .stream().map(AvaliacaoResponseDTO::new).toList();
        return ResponseEntity.ok(avaliacoes);
    }

//    @PreAuthorize("hasAnyRole('USERADMIN', 'USERACADADMIN', 'USERACAD', 'PERSONAL')")
    @GetMapping("/personal/{personalId}")
    public ResponseEntity<List<AvaliacaoResponseDTO>> buscarAvaliacaoPorPersonal(@PathVariable Long personalId) {
        List<AvaliacaoResponseDTO> avaliacoes = avaliacaoService.buscarAvaliacaoPorPersonal(personalId)
                .stream().map(AvaliacaoResponseDTO::new).toList();
        return ResponseEntity.ok(avaliacoes);
    }

    @PreAuthorize("@avaliacaoService.usuarioPodeVisualizarUsuario(#userId) or hasRole('USERADMIN')")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AvaliacaoResponseDTO>> buscarAvaliacaoPorUsuario(@PathVariable Long userId) {
        List<AvaliacaoResponseDTO> avaliacoes = avaliacaoService.buscarAvaliacaoPorUsuario(userId)
                .stream().map(AvaliacaoResponseDTO::new).toList();
        return ResponseEntity.ok(avaliacoes);
    }


    @PreAuthorize("hasAnyRole('USERADMIN','USERACAD', 'PERSONAL')")
    @PostMapping
    public ResponseEntity<Map<String, Object>> registrarAvaliacao(@RequestBody @Valid AvaliacaoRequestDTO data) {
        AvaliacaoResponseDTO avaliacaoResponseDTO = new AvaliacaoResponseDTO(avaliacaoService.registrarAvaliacao(data));

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Avaliacao cadastrada com sucesso!");
        response.put("feedback", avaliacaoResponseDTO);

        return ResponseEntity.ok(response);
    }


    @PreAuthorize("@avaliacaoService.usuarioPodeEditar(#id) or hasAnyRole('USERADMIN', 'PERSONAL')")
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> atualizarAvaliacao(@PathVariable Long id, @RequestBody @Valid AvaliacaoRequestDTO data) {
        AvaliacaoResponseDTO updatedFeedback = new AvaliacaoResponseDTO(avaliacaoService.atualizarAvaliacao(id, data));

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Avaliacao atualizada com sucesso!");
        response.put("feedback", updatedFeedback);

        return ResponseEntity.ok(response);
    }

    // Apenas ADMIN ou o USERACAD dono
    @PreAuthorize("@avaliacaoService.usuarioPodeEditar(#id) or hasAnyRole('USERADMIN', 'PERSONAL')")
    @DeleteMapping("/{id}/user/{userId}")
    public ResponseEntity<Map<String, String>> deletarAvaliacao(@PathVariable Long id, @PathVariable Long userId) {
        avaliacaoService.deletarAvaliacao(id, userId);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Avaliacao deletado com sucesso!");
        return ResponseEntity.ok(response);
    }
}

