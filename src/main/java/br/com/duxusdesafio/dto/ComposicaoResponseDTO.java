package br.com.duxusdesafio.dto;

import br.com.duxusdesafio.model.Integrante;
import lombok.Getter;
import lombok.Setter;

/**
 * Representa o vínculo do integrante no time na resposta da API,
 * sem referenciar o Time de volta para evitar ciclo infinito.
 */
@Getter
@Setter
public class ComposicaoResponseDTO {
    private Long id;
    private Integrante integrante;

    public ComposicaoResponseDTO(Long id, Integrante integrante) {
        this.id = id;
        this.integrante = integrante;
    }
}
