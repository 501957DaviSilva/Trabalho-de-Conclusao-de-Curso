package com.example.NovoTesteCrud.controller;

import com.example.NovoTesteCrud.domain.treino.dto.TreinoResponseDTO;
import com.example.NovoTesteCrud.domain.treino.dto.TreinoRequestDTO;
import com.example.NovoTesteCrud.domain.treino.dto.TreinoFilterDto;
import com.example.NovoTesteCrud.domain.treino.dto.enums.Tipos;
import com.example.NovoTesteCrud.domain.treino.dto.enums.Hipertrofia_Performace;
import com.example.NovoTesteCrud.domain.treino.dto.enums.Cardio;

import com.example.NovoTesteCrud.service.TreinoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@PreAuthorize("permitAll()")
@RestController
@RequestMapping("/treino")
public class TreinoController {

    @Autowired
    private TreinoService treinoService;

    @GetMapping
    public ResponseEntity<List<TreinoResponseDTO>> buscarTodosTreinos() {
        return ResponseEntity.ok(treinoService.buscarTodosTreinos());
    }
    @GetMapping("/filtro")
    public List<TreinoResponseDTO> buscarFiltrados(
            @RequestParam(required = false) List<Cardio> cardios,
            @RequestParam(required = false) List<Hipertrofia_Performace> hipertrofias,
            @RequestParam(required = false) List<Tipos> tiposTreino
    ) {
        TreinoFilterDto filtro = new TreinoFilterDto(cardios, hipertrofias, tiposTreino);
        return treinoService.buscarTreinosFiltrados(filtro);
    }


    @PreAuthorize("hasAnyRole('USERADMIN','USERACAD', 'PERSONAL')")
    @PostMapping
    public ResponseEntity<Map<String, Object>> registrarTreinos(@RequestBody TreinoRequestDTO data) {
        TreinoResponseDTO treinoResponseDTO = treinoService.registrarTreinos(data);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Treino cadastrado com sucesso!");
        response.put("treino", treinoResponseDTO);

        return ResponseEntity.ok(response);
    }


    @PreAuthorize("@treinoService.usuarioPodeEditar(#id) or hasAnyRole('USERADMIN', 'PERSONAL')")
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> atualizarTreinos(@PathVariable Long id, @RequestBody @Valid TreinoRequestDTO data) {
        TreinoResponseDTO updatedTreino = treinoService.atualizarTreinos(id, data);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Treino atualizado com sucesso!");
        response.put("treino", updatedTreino);

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("@treinoService.usuarioPodeEditar(#id) or hasAnyRole('USERADMIN', 'PERSONAL')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deletarTreinos(@PathVariable Long id) {
        treinoService.deletarTreinos(id);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Treino deletado com sucesso!");

        return ResponseEntity.ok(response);
    }
}
