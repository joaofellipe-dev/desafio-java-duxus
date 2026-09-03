package br.com.duxusdesafio.service;

import br.com.duxusdesafio.model.ComposicaoTime;
import br.com.duxusdesafio.model.Integrante;
import br.com.duxusdesafio.model.Time;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

/**
 * Service que possuirá as regras de negócio para o processamento dos dados
 * solicitados no desafio!
 *
 * OBS ao candidato: PREFERENCIALMENTE, NÃO ALTERE AS ASSINATURAS DOS MÉTODOS!
 * Trabalhe com a proposta pura.
 *
 * @author carlosau
 */
@Service
public class ApiService {

    /**
     * Vai retornar um Time, com a composição do time daquela data
     */
    public Time timeDaData(LocalDate data, List<Time> todosOsTimes){
        if (data == null || todosOsTimes == null) {
            return null;
        }
        for (Time time : todosOsTimes){
            if(data.equals(time.getData())){
                return time;
            }
        }
        return null;
    }

    /**
     * Vai retornar o integrante que estiver presente na maior quantidade de times
     * dentro do período
     */
    public Integrante integranteMaisUsado(LocalDate dataInicial, LocalDate dataFinal, List<Time> todosOsTimes){
        List<Time> timesDoPeriodo = filtrarTimesPorPeriodo(dataInicial, dataFinal, todosOsTimes);
        if (timesDoPeriodo.isEmpty()){
            return null;
        }
        Map<Integrante, Integer> contagem = new HashMap<>();
        for (Time time : timesDoPeriodo) {
            if (time.getComposicaoTime() != null) {
                for (ComposicaoTime composicao : time.getComposicaoTime()) {
                    Integrante integrante = composicao.getIntegrante();
                    if (integrante != null) {
                        int totalAtual = contagem.getOrDefault(integrante, 0);
                        contagem.put(integrante, totalAtual + 1);
                    }
                }
            }
        }
        Integrante maisUsado = null;
        int maiorContagem = 0;
        for (Map.Entry<Integrante, Integer> entry : contagem.entrySet()) {
            if (entry.getValue() > maiorContagem) {
                maiorContagem = entry.getValue();
                maisUsado = entry.getKey();
            }
        }

        return maisUsado;
    }

    /**
     * Filtra e retorna apenas os times que jogaram dentro do periodo informado
     */
    private List<Time> filtrarTimesPorPeriodo(LocalDate dataInicial, LocalDate dataFinal, List<Time> todosOsTimes) {
        List<Time> filtrados = new ArrayList<>();

        for (Time time : todosOsTimes) {
            if (time == null || time.getData() == null) {
                continue;
            }

            LocalDate data = time.getData();

            boolean depoisOuIgualInicial = (dataInicial == null) || !data.isBefore(dataInicial);
            boolean antesOuIgualFinal = (dataFinal == null) || !data.isAfter(dataFinal);

            if (depoisOuIgualInicial && antesOuIgualFinal) {
                filtrados.add(time);
            }
        }

        return filtrados;
    }

    /**
     * Vai retornar uma lista com os nomes dos integrantes do time mais recorrente dentro do período.
     * OBS: Time é o clube + composição em determinada data
     */
    public List<String> integrantesDoTimeMaisRecorrente(LocalDate dataInicial, LocalDate dataFinal, List<Time> todosOsTimes){
        // TODO Implementar método seguindo as instruções!
        return null;
    }

    /**
     * Vai retornar a função mais recorrente nos times dentro do período
     */
    public String funcaoMaisRecorrente(LocalDate dataInicial, LocalDate dataFinal, List<Time> todosOsTimes){
        // TODO Implementar método seguindo as instruções!
        return null;
    }

    /**
     * Vai retornar o nome do Clube mais comum dentro do período
     */
    public String clubeMaisRecorrente(LocalDate dataInicial, LocalDate dataFinal, List<Time> todosOsTimes) {
        // TODO Implementar método seguindo as instruções!
        return null;
    }


    /**
     * Vai retornar o número (quantidade) de aparições de cada Clube participante no período
     */
    public Map<String, Long> contagemDeClubesNoPeriodo(LocalDate dataInicial, LocalDate dataFinal, List<Time> todosOsTimes){
        // TODO Implementar método seguindo as instruções!
        return null;
    }

    /**
     * Vai retornar o número (quantidade) de Funções dentro do período.
     * Dica - pense sobre repetições!
     */
    public Map<String, Long> contagemPorFuncao(LocalDate dataInicial, LocalDate dataFinal, List<Time> todosOsTimes){
        // TODO Implementar método seguindo as instruções!
        return null;
    }

}
