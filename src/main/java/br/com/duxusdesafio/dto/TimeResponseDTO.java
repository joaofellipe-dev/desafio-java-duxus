package br.com.duxusdesafio.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

/**
 * DTO de resposta para consulta de times.
 * Garante que a estrutura relacional seja entregue de forma limpa.
 */
@Getter
@Setter
public class TimeResponseDTO{
    private Long id;
    private String nomeDoClube;
    private LocalDate data;
    private List<ComposicaoResponseDTO> composicaoTime;
}
