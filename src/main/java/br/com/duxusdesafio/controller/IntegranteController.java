package br.com.duxusdesafio.controller;

import br.com.duxusdesafio.dto.IntegranteDTO;
import br.com.duxusdesafio.model.Integrante;
import br.com.duxusdesafio.service.IntegranteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Endpoints REST responsáveis pelo ciclo de vida dos integrantes.
 */
@RestController
@RequestMapping("/integrantes")
public class IntegranteController {

    private final IntegranteService integranteService;

    public IntegranteController(IntegranteService integranteService) {
        this.integranteService = integranteService;
    }

    /**
     * Cadastra um novo atleta.
     * Retorna 201 Created para indicar a criação bem-sucedida do recurso no servidor.
     */
    @PostMapping
    public ResponseEntity<Integrante> cadastrar(@RequestBody IntegranteDTO dto) {
        Integrante salvo = integranteService.cadastrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    /**
     * Recupera todos os integrantes cadastrados na base.
     */
    @GetMapping
    public ResponseEntity<List<Integrante>> listarTodos() {
        return ResponseEntity.ok(integranteService.listarTodos());
    }
}
