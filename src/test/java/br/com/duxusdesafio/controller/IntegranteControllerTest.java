package br.com.duxusdesafio.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class IntegranteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Deve cadastrar um integrante e retornar HTTP 201 Created")
    void deveCadastrarIntegrante() throws Exception {
        // Dados compativel com a versao do java para inserir dados
        String jsonCorpo = "{\"nome\": \"Michael Jordan\", \"funcao\": \"Ala\"}";

        // Configura a requisição
        MockHttpServletRequestBuilder requisicao = post("/integrantes");
        requisicao.contentType(MediaType.APPLICATION_JSON);
        requisicao.content(jsonCorpo);

        // Dispara a chamada
        ResultActions resposta = mockMvc.perform(requisicao);

        // Valida se o status retornado foi 201
        ResultMatcher statusCriado = status().isCreated();
        resposta.andExpect(statusCriado);
    }

    @Test
    @DisplayName("Deve listar os integrantes e retornar HTTP 200 OK")
    void deveListarIntegrantes() throws Exception {
        // Configura a requisição GET
        MockHttpServletRequestBuilder requisicao = get("/integrantes");

        // Dispara a chamada
        ResultActions resposta = mockMvc.perform(requisicao);

        // Valida se o status retornado foi 200
        ResultMatcher statusOk = status().isOk();
        resposta.andExpect(statusOk);
    }
}
