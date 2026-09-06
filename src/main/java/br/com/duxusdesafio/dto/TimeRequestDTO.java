package br.com.duxusdesafio.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.List;

/**
 * DTO de entrada para criação de times via identificadores existentes.
 */
@Getter
@Setter
public class TimeRequestDTO {
    private String nomeDoClube;
    private LocalDate data;

    private List<Long> integranteIds;

}
