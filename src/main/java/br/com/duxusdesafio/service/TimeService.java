package br.com.duxusdesafio.service;

import br.com.duxusdesafio.dto.TimeRequestDTO;
import br.com.duxusdesafio.model.ComposicaoTime;
import br.com.duxusdesafio.model.Integrante;
import br.com.duxusdesafio.model.Time;
import br.com.duxusdesafio.repository.IntegranteRepository;
import br.com.duxusdesafio.repository.TimeRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Regras de negócio para criação de equipes e associação relacional de integrantes.
 */
@Service
public class TimeService {

    private final TimeRepository timeRepository;
    private final IntegranteRepository integranteRepository;

    public TimeService(TimeRepository timeRepository, IntegranteRepository integranteRepository) {
        this.timeRepository = timeRepository;
        this.integranteRepository = integranteRepository;
    }

    /**
     * Cadastra um time garantindo que todos os atletas referenciados existam.
     * Monta o relacionamento intermediário (ComposicaoTime) antes de salvar em cascata.
     */
    public Time cadastrar(TimeRequestDTO dto) {
        Time time = new Time();
        time.setData(dto.getData());
        time.setNomeDoClube(dto.getNomeDoClube());

        // Busca em lote todos os integrantes solicitados pelos seus identificadores
        List<Integrante> integrantes = integranteRepository.findAllById(dto.getIntegranteIds());

        // Validação defensiva: impede times com referências fantasmas ou vazias
        if (integrantes.isEmpty() || integrantes.size() != dto.getIntegranteIds().size()) {
            throw new IllegalArgumentException("Um ou mais IDs de integrantes não foram encontrados no banco.");
        }

        // Constrói a tabela de junção mantendo a referência bidirecional necessária para a cascata
        List<ComposicaoTime> composicoes = new ArrayList<>();
        for (Integrante integrante : integrantes) {
            ComposicaoTime composicao = new ComposicaoTime();
            composicao.setTime(time);
            composicao.setIntegrante(integrante);
            composicoes.add(composicao);
        }

        time.setComposicaoTime(composicoes);
        return timeRepository.save(time);
    }

    public List<Time> listarTodos() {
        return timeRepository.findAll();
    }
}
