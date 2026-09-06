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

@Service
public class TimeService {

    private final TimeRepository timeRepository;
    private final IntegranteRepository integranteRepository;

    public TimeService(TimeRepository timeRepository, IntegranteRepository integranteRepository) {
        this.timeRepository = timeRepository;
        this.integranteRepository = integranteRepository;
    }

    public Time cadastrar(TimeRequestDTO dto) {
        Time time = new Time();
        time.setData(dto.getData());

        List<Integrante> integrantes = integranteRepository.findAllById(dto.getIntegranteIds());

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
