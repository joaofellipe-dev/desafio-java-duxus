package br.com.duxusdesafio.controller;

import br.com.duxusdesafio.dto.TimeRequestDTO;
import br.com.duxusdesafio.model.Time;
import br.com.duxusdesafio.service.TimeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/times")
public class TimeController {

    private final TimeService timeService;

    public TimeController(TimeService timeService) {
        this.timeService = timeService;
    }

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody TimeRequestDTO dto) {
        try {
            Time timeSalvo = timeService.cadastrar(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(timeSalvo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

        @GetMapping
        public ResponseEntity<List<Time>> listarTodos () {
            return ResponseEntity.ok(timeService.listarTodos());
        }
    }

