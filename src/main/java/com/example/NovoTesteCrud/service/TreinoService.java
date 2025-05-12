package com.example.NovoTesteCrud.service;

import com.example.NovoTesteCrud.domain.exercicios.Exercicio;
import com.example.NovoTesteCrud.domain.treino.*;
import com.example.NovoTesteCrud.domain.treino.dto.TreinoRequestDTO;
import com.example.NovoTesteCrud.domain.user.UserAcad;
import com.example.NovoTesteCrud.repository.UserAcadRepository;
import com.example.NovoTesteCrud.domain.treino.dto.TreinoResponseDTO;
import com.example.NovoTesteCrud.repository.TreinoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TreinoService {

    @Autowired
    private TreinoRepository treinoRepository;

    @Autowired
    private UserAcadRepository userRepository;

    public List<TreinoResponseDTO> buscarTodosTreinos() {
        return treinoRepository.findAll().stream().map(TreinoResponseDTO::new).toList();
    }

    @Transactional
    public TreinoResponseDTO registrarTreinos(TreinoRequestDTO data) {
        UserAcad user = userRepository.findById(data.userId())
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado!"));

        Treino treino = new Treino();
        treino.setNome(data.nome());
        treino.setDescricao(data.descricao());
        treino.setUser(user);

        List<Exercicio> exercicios = data.exercicios().stream().map(req -> {
            Exercicio exercicio = new Exercicio();
            exercicio.setNome(req.nome());
            exercicio.setSeries(req.series());
            exercicio.setRepeticoes(req.repeticoes());
            exercicio.setGrupoMuscular(req.grupoMuscular());
            exercicio.setTreino(treino);
            return exercicio;
        }).toList();

        treino.setExercicios(exercicios);
        treinoRepository.save(treino);

        return new TreinoResponseDTO(treino);
    }


    @Transactional
    public TreinoResponseDTO atualizarTreinos(Long id, TreinoRequestDTO data) {
        Treino treino = treinoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Treino não encontrado!"));

        UserAcad user = userRepository.findById(data.userId())
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado!"));

        treino.setNome(data.nome());
        treino.setDescricao(data.descricao());
        treino.setUser(user);

        treino.getExercicios().clear();

        List<Exercicio> novosExercicios = data.exercicios().stream().map(req -> {
            Exercicio exercicio = new Exercicio();
            exercicio.setNome(req.nome());
            exercicio.setSeries(req.series());
            exercicio.setRepeticoes(req.repeticoes());
            exercicio.setGrupoMuscular(req.grupoMuscular());
            exercicio.setTreino(treino);
            return exercicio;
        }).toList();

        treino.getExercicios().addAll(novosExercicios);

        treinoRepository.save(treino);
        return new TreinoResponseDTO(treino);
    }

    @Transactional
    public void deletarTreinos(Long id) {
        Treino treino = treinoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Treino não encontrado!"));
        treinoRepository.delete(treino);
    }
}
