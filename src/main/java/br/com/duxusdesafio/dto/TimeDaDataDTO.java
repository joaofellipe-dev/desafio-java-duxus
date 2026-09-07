package br.com.duxusdesafio.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

/**
 * Esta classe foi criada especificamente para o endpoint /time-da-data.
 * Isola a entidade Time e formata a saída JSON para conter apenas a lista de nomes
 * dos integrantes em formato de String.
 */
@Getter
@Setter
public class TimeDaDataDTO {
    private LocalDate data;
    private String clube;
    private List<String> integrantes;

    public TimeDaDataDTO() {
    }

    public TimeDaDataDTO(LocalDate data, String clube, List<String> integrantes) {
        this.data = data;
        this.clube = clube;
        this.integrantes = integrantes;
    }
}
