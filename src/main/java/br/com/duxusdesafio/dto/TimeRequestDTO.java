package br.com.duxusdesafio.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class TimeRequestDTO {
    private String nomeDoClube;
    private LocalDate data;

    private List<Long>integranteIds;

}
