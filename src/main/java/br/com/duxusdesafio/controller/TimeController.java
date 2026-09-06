package br.com.duxusdesafio.controller;

import br.com.duxusdesafio.dto.TimeRequestDTO;
import br.com.duxusdesafio.model.Time;
import br.com.duxusdesafio.service.TimeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/times")
public class TimeController {

    private final TimeService timeService;

    public TimeController(TimeService timeService) {
        this.timeService = timeService;
    }

    @PostMapping
    public ResponseEntity<Time> cadastrar(@RequestBody TimeRequestDTO dto) {
        Time timeSalvo = timeService.cadastrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(timeSalvo);
    }
    @GetMapping
    public ResponseEntity<List<Time>> listarTodos() {
        List<Time> times = timeService.listarTodos();
        return ResponseEntity.ok(times);
    }
}
