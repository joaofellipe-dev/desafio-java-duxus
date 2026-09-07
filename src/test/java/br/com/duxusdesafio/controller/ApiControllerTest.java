package br.com.duxusdesafio.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("1. Deve retornar 404 Not Found para Time da Data quando não houver time (Banco Vazio)")
    void deveRetornarTimeDaData() throws Exception {
        // Valida o comportamento defensivo do endpoint. A API não deve gerar exceção (HTTP 500),
        // mas sim responder adequadamente que o recurso não existe na data especificada.
        mockMvc.perform(get("/api/services/time-da-data").param("data", "2021-05-10"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("2. Deve retornar 404 Not Found para Integrante Mais Usado quando banco estiver vazio")
    void deveRetornarIntegranteMaisUsado() throws Exception {
        // Valida que a rota existe e trata corretamente a falta de dados no banco retornando 404.
        mockMvc.perform(get("/api/services/integrante-mais-usado"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("3. Deve retornar 200 OK para Integrantes do Time Mais Recorrente")
    void deveRetornarIntegrantesDoTimeMaisRecorrente() throws Exception {
        // Valida se a rota está mapeada corretamente e acessível.
        // Mesmo sem dados, o endpoint deve retornar um JSON vazio com status 200 OK, não falhando.
        mockMvc.perform(get("/api/services/integrantes-do-time-mais-recorrente"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("4. Deve retornar 200 OK para Função Mais Recorrente")
    void deveRetornarFuncaoMaisRecorrente() throws Exception {
        mockMvc.perform(get("/api/services/funcao-mais-recorrente"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("5. Deve retornar 200 OK para Clube Mais Recorrente")
    void deveRetornarClubeMaisRecorrente() throws Exception {
        mockMvc.perform(get("/api/services/clube-mais-recorrente"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("6. Deve retornar 200 OK para Contagem de Clubes")
    void deveRetornarContagemDeClubes() throws Exception {
        mockMvc.perform(get("/api/services/contagem-de-clubes"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("7. Deve retornar 200 OK para Contagem por Função")
    void deveRetornarContagemPorFuncao() throws Exception {
        mockMvc.perform(get("/api/services/contagem-por-funcao"))
                .andExpect(status().isOk());
    }
}