package br.com.duxusdesafio.service;

import br.com.duxusdesafio.dto.IntegranteDTO;
import br.com.duxusdesafio.model.Integrante;
import br.com.duxusdesafio.repository.IntegranteRepository;
import org.springframework.stereotype.Service;

@Service
public class IntegranteService {
    private final IntegranteRepository integranteRepository;

    public IntegranteService(IntegranteRepository integranteRepository) {
        this.integranteRepository = integranteRepository;
    }
    public Integrante cadastrar(IntegranteDTO dto) {
        Integrante integrante = new Integrante();
        integrante.setNome(dto.getNome());
        integrante.setFuncao(dto.getFuncao());

        return integranteRepository.save(integrante);
    }
    public List<Integrante> listarTodos(){
        return integranteRepository.findAll();
    }
}