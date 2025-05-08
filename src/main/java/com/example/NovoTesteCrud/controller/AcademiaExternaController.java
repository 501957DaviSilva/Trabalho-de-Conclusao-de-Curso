package com.example.NovoTesteCrud.controller;

import com.example.NovoTesteCrud.domain.acad.dto.AcademiaExternaDTO;
import com.example.NovoTesteCrud.service.AcademiaExternaService;
import com.example.NovoTesteCrud.domain.acad.dto.AcademiaDetalhesDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/academias-externas")
public class AcademiaExternaController {

    @Autowired
    private AcademiaExternaService academiaExternaService;

    @GetMapping
    public List<AcademiaExternaDTO> buscarAcademias(@RequestParam String endereco) {
        return academiaExternaService.buscarAcademiasProximas(endereco);
    }

    @GetMapping("/detalhes")
    public AcademiaDetalhesDTO buscarDetalhes(@RequestParam String placeId) {
        return academiaExternaService.detalhesComoDTO(placeId);
    }
}
