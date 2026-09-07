package br.com.duxusdesafio.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class TimeDaDataDTO {
    private LocalDate data;
    private String clube;
    private List<String> integrantes;

    public TimeDaDataDTO(){
    }
    public TimeDaDataDTO(LocalDate data, String clube, List<String> integrantes){
       this.data = data;
       this.clube=clube;
       this.integrantes = integrantes;
    }
}
