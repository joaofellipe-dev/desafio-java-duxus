package br.com.duxusdesafio.service;

import br.com.duxusdesafio.dto.ComposicaoResponseDTO;
import br.com.duxusdesafio.dto.TimeRequestDTO;
import br.com.duxusdesafio.dto.TimeResponseDTO;
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
    public TimeResponseDTO cadastrar(TimeRequestDTO dto) {
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
        Time timeSalvo = timeRepository.save(time);

        return paraResponseDTO(timeSalvo);
    }
    public List<TimeResponseDTO> listarTodos() {
        List<Time> timesDoBanco = timeRepository.findAll();
        List<TimeResponseDTO> listaResposta = new ArrayList<>();

        // Percorre cada Time do banco e converte para DTO
        for (Time time : timesDoBanco) {
            listaResposta.add(paraResponseDTO(time));
        }

        return listaResposta;
    }

    /**
     * Converte uma entidade Time para TimeResponseDTO.
     */
    private TimeResponseDTO paraResponseDTO(Time time) {
        TimeResponseDTO dto = new TimeResponseDTO();
        dto.setId(time.getId());
        dto.setNomeDoClube(time.getNomeDoClube());
        dto.setData(time.getData());

        List<ComposicaoResponseDTO> composicoesDTO = new ArrayList<>();

        if (time.getComposicaoTime() != null) {
            for (ComposicaoTime composicao : time.getComposicaoTime()) {
                ComposicaoResponseDTO compDTO = new ComposicaoResponseDTO(composicao.getId(), composicao.getIntegrante());
                composicoesDTO.add(compDTO);
            }
        }
        dto.setComposicaoTime(composicoesDTO);
        return dto;
    }
}