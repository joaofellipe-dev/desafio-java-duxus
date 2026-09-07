package br.com.duxusdesafio.controller;

import br.com.duxusdesafio.model.Time;
import br.com.duxusdesafio.model.Integrante;
import br.com.duxusdesafio.repository.IntegranteRepository;
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
public class TimeControllerTest {

    @Autowired
    private MockMvc mock;

    @Autowired
    private IntegranteRepository integranteRepository;

    @Test
    @DisplayName("Deve retornar HTTP 400 Bad Request ao tentar cadastrar time com integrante inexistente")
    void deveRetornarErroQuandoIntegranteNaoExistir() throws Exception {
        // ID 999999 que não existe no H2
        String jsonCorpo = "{\"nomeDoClube\":\"Lakers\",\"data\":\"2001-06-12\",\"integranteIds\": \"[999999]\"}";

        MockHttpServletRequestBuilder requisicao = post("/times");
        requisicao.contentType(MediaType.APPLICATION_JSON);
        requisicao.content(jsonCorpo);

        ResultActions resposta = mock.perform(requisicao);

        ResultMatcher statusErro = status().isBadRequest();
        resposta.andExpect(statusErro);
    }

    @Test
    @DisplayName("Deve listar os times e retornar HTTP 200 OK")
    void deveListarTimes() throws Exception {
        MockHttpServletRequestBuilder requisicao = get("/times");

        ResultActions resposta = mock.perform(requisicao);

        ResultMatcher statusOk = status().isOk();
        resposta.andExpect(statusOk);
    }
}