package br.com.duxusdesafio.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO para recebimento e transferência de dados de Integrante.
 * Uso explícito de @Getter e @Setter para manter o controle fino dos métodos de acesso.
 */
@Getter
@Setter
public class IntegranteDTO {
    private String nome;
    private String funcao;

    public IntegranteDTO() {
    }
}
