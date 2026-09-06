package br.com.duxusdesafio.controller;

import br.com.duxusdesafio.dto.TimeRequestDTO;
import br.com.duxusdesafio.model.Time;
import br.com.duxusdesafio.service.TimeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Endpoints REST responsáveis pelo gerenciamento de equipes.
 */
@RestController
@RequestMapping("/times")
public class TimeController {

    private final TimeService timeService;

    public TimeController(TimeService timeService) {
        this.timeService = timeService;
    }

    /**
     * Cria uma equipe associando integrantes existentes.
     * <p>
     * Retorna 201 Created em caso de sucesso.
     * Retorna 400 Bad Request se a lista contiver IDs de integrantes inválidos ou inexistentes.
     */
    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody TimeRequestDTO dto) {
        try {
            Time timeSalvo = timeService.cadastrar(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(timeSalvo);
        } catch (IllegalArgumentException e) {
            // Captura falhas de validação de negócio e converte em erro semântico de cliente (400)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /**
     * Retorna a listagem completa de times cadastrados.
     */
    @GetMapping
    public ResponseEntity<List<Time>> listarTodos() {
        return ResponseEntity.ok(timeService.listarTodos());
    }
}

