package br.com.duxusdesafio.controller;

import br.com.duxusdesafio.dto.IntegranteDTO;
import br.com.duxusdesafio.model.Integrante;
import br.com.duxusdesafio.service.IntegranteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/integrantes")
public class IntegranteController {

    private final IntegranteService integranteService;

    public IntegranteController(IntegranteService integranteService){
        this.integranteService = integranteService;
    }
    @PostMapping
    public ResponseEntity<Integrante> cadastrar(@RequestBody IntegranteDTO dto) {
        Integrante integranteSalvo = integranteService.cadastrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(integranteSalvo);
    }

    @GetMapping
    public ResponseEntity<List<Integrante>> listarTodos() {
        List<Integrante> integrantes = integranteService.listarTodos();
        return ResponseEntity.ok(integrantes);
    }
}
