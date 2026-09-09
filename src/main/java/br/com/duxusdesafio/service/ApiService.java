package br.com.duxusdesafio.service;

import br.com.duxusdesafio.model.ComposicaoTime;
import br.com.duxusdesafio.model.Integrante;
import br.com.duxusdesafio.model.Time;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.Collection;

/**
 * Service que possuirá as regras de negócio para o processamento dos dados
 * solicitados no desafio!
 * <p>
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
    public Time timeDaData(LocalDate data, List<Time> todosOsTimes) {
        if (data == null || todosOsTimes == null) {
            return null;
        }
        for (Time time : todosOsTimes) {
            if (data.equals(time.getData())) {
                return time;
            }
        }
        return null;
    }

    /**
     * Vai retornar o integrante que estiver presente na maior quantidade de times
     * dentro do período
     */
    public Integrante integranteMaisUsado(LocalDate dataInicial, LocalDate dataFinal, List<Time> todosOsTimes) {
        List<Time> timesDoPeriodo = filtrarTimesPorPeriodo(dataInicial, dataFinal, todosOsTimes);
        if (timesDoPeriodo.isEmpty()) {
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
    public List<String> integrantesDoTimeMaisRecorrente(LocalDate dataInicial, LocalDate dataFinal, List<Time> todosOsTimes) {
        if (todosOsTimes == null || todosOsTimes.isEmpty()) {
            return new ArrayList<>();
        }
        List<Time> timesDoPeriodo = filtrarTimesPorPeriodo(dataInicial, dataFinal, todosOsTimes);
        if (timesDoPeriodo.isEmpty()) {
            return new ArrayList<>();
        }
        Map<List<String>, Integer> contagemFormacoes = new HashMap<>();
        for (Time time : timesDoPeriodo) {
            if (time.getComposicaoTime() != null && !time.getComposicaoTime().isEmpty()) {
                List<String> nomesIntegrantes = new ArrayList<>();
                for (ComposicaoTime composicao : time.getComposicaoTime()) {
                    if (composicao.getIntegrante() != null && composicao.getIntegrante().getNome() != null) {
                        nomesIntegrantes.add(composicao.getIntegrante().getNome());
                    }
                }
                // Garante que a ordem dos nomes nao altere a identificacao do time
                Collections.sort(nomesIntegrantes);
                int totalAtual = contagemFormacoes.getOrDefault(nomesIntegrantes, 0);
                contagemFormacoes.put(nomesIntegrantes, totalAtual + 1);
            }
        }
        List<String> formacaoMaisRecorrente = new ArrayList<>();
        int maiorContagem = 0;
        for (Map.Entry<List<String>, Integer> entry : contagemFormacoes.entrySet()) {
            if (entry.getValue() > maiorContagem) {
                maiorContagem = entry.getValue();
                formacaoMaisRecorrente = entry.getKey();
            }
        }
        return formacaoMaisRecorrente;
    }

    /**
     * Vai retornar a função mais recorrente nos times dentro do período
     */
    public String funcaoMaisRecorrente(LocalDate dataInicial, LocalDate dataFinal, List<Time> todosOsTimes) {
        Map<String, Long> placarDeFuncoes = new HashMap<>();

        for (Time time : todosOsTimes) {
            LocalDate dataTime = time.getData();

            boolean dentroDoPeriodo = (dataInicial == null || !dataTime.isBefore(dataInicial)) &&
                    (dataFinal == null || !dataTime.isAfter(dataFinal));

            if (dentroDoPeriodo) {
                for (ComposicaoTime composicao : time.getComposicaoTime()) {
                    String funcao = composicao.getIntegrante().getFuncao();
                    placarDeFuncoes.put(funcao, placarDeFuncoes.getOrDefault(funcao, 0L) + 1L);
                }
            }
        }

        String funcaoVencedora = ""; // Se não achar nada, devolve vazio
        long maiorPontuacao = 0L;

        for (Map.Entry<String, Long> entrada : placarDeFuncoes.entrySet()) {
            if (entrada.getValue() > maiorPontuacao) {
                maiorPontuacao = entrada.getValue();
                funcaoVencedora = entrada.getKey();
            }
        }

        return funcaoVencedora;
    }

    /**
     * Vai retornar o nome do Clube mais comum dentro do período
     */
    public String clubeMaisRecorrente(LocalDate dataInicial, LocalDate dataFinal, List<Time> todosOsTimes) {
        // Reaproveita a tabela de frequencia ja calculada e validada
        Map<String, Long> contagemClubes = contagemDeClubesNoPeriodo(dataInicial, dataFinal, todosOsTimes);
        if (contagemClubes == null || contagemClubes.isEmpty()) {
            return null;
        }
        String clubeMaisRecorrente = null;
        long maiorContagem = 0L;
        // Itera sobre o mapa para encontrar a chave com a maior ocorrencia
        for (Map.Entry<String, Long> entry : contagemClubes.entrySet()) {
            if (entry.getValue() > maiorContagem) {
                maiorContagem = entry.getValue();
                clubeMaisRecorrente = entry.getKey();
            }
        }

        return clubeMaisRecorrente;
    }


    /**
     * Vai retornar o número (quantidade) de aparições de cada Clube participante no período
     */
    public Map<String, Long> contagemDeClubesNoPeriodo(LocalDate dataInicial, LocalDate dataFinal, List<Time> todosOsTimes) {
        if (todosOsTimes == null || todosOsTimes.isEmpty()) {
            return new HashMap<>();
        }
        List<Time> timesDoPeriodo = filtrarTimesPorPeriodo(dataInicial, dataFinal, todosOsTimes);
        if (timesDoPeriodo.isEmpty()) {
            return new HashMap<>();
        }
        //HashMap garante a busca e insercao para a tabela
        Map<String, Long> contagemClubes = new HashMap<>();
        for (Time time : timesDoPeriodo) {
            String clube = time.getNomeDoClube();
            if (clube != null) {
                //Incrementa a contagem acumulada utilizando Long 0L e 1L
                contagemClubes.put(clube, contagemClubes.getOrDefault(clube, 0L) + 1L);
            }
        }
        return contagemClubes;
    }

    /**
     * Vai retornar o número (quantidade) de Funções dentro do período.
     * Dica - pense sobre repetições!
     */
    public Map<String, Long> contagemPorFuncao(LocalDate dataInicial, LocalDate dataFinal, List<Time> todosOsTimes) {
        // Usamos um Set para guardar os IDs dos integrantes e garantir que não haverá repetição
        Map<String, Set<Long>> integrantesUnicosPorFuncao = new HashMap<>();

        for (Time time : todosOsTimes) {
            LocalDate dataTime = time.getData();

            // Valida se a data do time está dentro do período (aceita null caso não passem data)
            boolean dentroDoPeriodo = (dataInicial == null || !dataTime.isBefore(dataInicial)) &&
                    (dataFinal == null || !dataTime.isAfter(dataFinal));

            if (dentroDoPeriodo) {
                for (ComposicaoTime composicao : time.getComposicaoTime()) {
                    String funcao = composicao.getIntegrante().getFuncao();
                    Long idIntegrante = composicao.getIntegrante().getId();

                    // Se a função ainda não existir no Map, cria uma lista (Set) vazia para ela
                    integrantesUnicosPorFuncao.putIfAbsent(funcao, new HashSet<>());

                    // Adiciona o ID do integrante (o Set ignora automaticamente se o ID já estiver lá)
                    integrantesUnicosPorFuncao.get(funcao).add(idIntegrante);
                }
            }
        }

        // Agora convertemos o Map de Sets para o formato de resposta (Map de Long)
        Map<String, Long> resultado = new HashMap<>();
        for (Map.Entry<String, Set<Long>> entry : integrantesUnicosPorFuncao.entrySet()) {
            // O tamanho do Set é a quantidade exata de pessoas únicas naquela função
            resultado.put(entry.getKey(), (long) entry.getValue().size());
        }

        return resultado;
    }
}
