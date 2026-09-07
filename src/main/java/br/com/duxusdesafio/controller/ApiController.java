package br.com.duxusdesafio.controller;

import br.com.duxusdesafio.dto.TimeDaDataDTO;
import br.com.duxusdesafio.model.ComposicaoTime;
import br.com.duxusdesafio.model.Integrante;
import br.com.duxusdesafio.model.Time;
import br.com.duxusdesafio.repository.TimeRepository;
import br.com.duxusdesafio.service.ApiService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/services")
public class ApiController {
    private final ApiService apiService;
    private final TimeRepository timeRepository;

    public ApiController(ApiService apiService, TimeRepository timeRepository) {
        this.apiService = apiService;
        this.timeRepository = timeRepository;
    }

    /**
     * Busca um time pela data exata e retorna HTTP 200 com os dados formatados ou HTTP 404 caso nao exista
     */
    @GetMapping("/time-da-data")
    public ResponseEntity<?> timeDaData(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {
        List<Time> todosOsTimes = timeRepository.findAll();

        Time time = apiService.timeDaData(data, todosOsTimes);
        if (time == null) {
            return ResponseEntity.notFound().build();
        }
        List<String> nomesIntegrantes = new ArrayList<>();
        if (time.getComposicaoTime() != null) {
            for (ComposicaoTime comp : time.getComposicaoTime()) {
                if (comp.getIntegrante() != null) {
                    nomesIntegrantes.add(comp.getIntegrante().getNome());
                }
            }
        }
        TimeDaDataDTO dto = new TimeDaDataDTO(time.getData(), time.getNomeDoClube(), nomesIntegrantes);
        return ResponseEntity.ok(dto);
    }

    /**
     * Busca o integrante mais frequente em um período que e opcional.
     * HTTP 200 com o integrante ou HTTP 404 caso não haja dados no período.
     */
    @GetMapping("/integrante-mais-usado")
    public ResponseEntity<?> integranteMaisUsado(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicial,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFinal) {

        List<Time> todosOsTimes = timeRepository.findAll();
        Integrante integrante = apiService.integranteMaisUsado(dataInicial, dataFinal, todosOsTimes);

        if (integrante == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(integrante);
    }

    @GetMapping("/integrantes-do-time-mais-recorrente")
    public ResponseEntity<?> integrantesDoTimeMaisRecorrente(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicial,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFinal) {

        List<Time> todosOsTimes = timeRepository.findAll();

        return ResponseEntity.ok(apiService.integrantesDoTimeMaisRecorrente(dataInicial, dataFinal, todosOsTimes));
    }

    @GetMapping("/funcao-mais-recorrente")
    public ResponseEntity<?> funcaoMaisRecorrente(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicial,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFinal) {

        List<Time> todosOsTimes = timeRepository.findAll();
        String funcao = apiService.funcaoMaisRecorrente(dataInicial, dataFinal, todosOsTimes);

        // Envelopa o resultado em um Map para padronizar a saída do JSON
        Map<String, String> resposta = new HashMap<>();
        resposta.put("Função", funcao != null ? funcao : "Nenhuma função encontrada");
        return ResponseEntity.ok(resposta);
    }

    @GetMapping("/clube-mais-recorrente")
    public ResponseEntity<?> clubeMaisRecorrente(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicial,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFinal) {

        List<Time> todosOsTimes = timeRepository.findAll();
        String clube = apiService.clubeMaisRecorrente(dataInicial, dataFinal, todosOsTimes);

        Map<String, String> resposta = new HashMap<>();
        resposta.put("Clube", clube != null ? clube : "Nenhum clube encontrado");
        return ResponseEntity.ok(resposta);
    }

    @GetMapping("/contagem-de-clubes")
    public ResponseEntity<?> contagemDeClubesNoPeriodo(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicial,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFinal) {

        List<Time> todosOsTimes = timeRepository.findAll();

        return ResponseEntity.ok(apiService.contagemDeClubesNoPeriodo(dataInicial, dataFinal, todosOsTimes));
    }

    @GetMapping("/contagem-por-funcao")
    public ResponseEntity<?> contagemPorFuncao(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicial,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFinal) {

        List<Time> todosOsTimes = timeRepository.findAll();

        return ResponseEntity.ok(apiService.contagemPorFuncao(dataInicial, dataFinal, todosOsTimes));
    }


}
