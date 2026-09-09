# Desafio Técnico Duxus 

Este projeto é a resolução do desafio técnico para desenvolvimento de uma API RESTful em Java com Spring Boot. O objetivo do sistema é gerenciar o cadastro de integrantes e times, além de fornecer endpoints analíticos sem a utilização de processamento em banco de dados.

---
## Como Executar o Projeto

1. Certifique-se de ter o **Java 11+** e o **Maven** instalados.
2. Clone este repositório ou extraia o arquivo compactado.
3. Importe o projeto na sua IDE de preferência (IntelliJ IDEA, Eclipse, etc.).
4. Execute a classe principal `DuxusDesafioApplication.java`.
5. A aplicação subirá um servidor Tomcat embutido na porta `8080` com um banco de dados H2 em memória.

### Acessando as Telas (Frontend)
A interface de usuário foi construída utilizando HTML e JavaScript puro (Vanilla), focando em simplicidade e isolamento, e é servida diretamente pelo Spring Boot.
Com o projeto rodando, acesse no seu navegador:
* **Cadastro de Integrantes:** [http://localhost:8080/integrantes.html](http://localhost:8080/integrantes.html)
* **Montagem de Times:** [http://localhost:8080/times.html](http://localhost:8080/times.html)

---
## Decisões Arquiteturais & Troubleshooting

1. **Processamento Estritamente em Memória (Regra de Negócio):**
  * Nenhum cálculo analítico foi delegado ao banco H2 via consultas nativas ou JPQL agregadas (`COUNT`, `SUM`, `GROUP BY`).
  * A camada de persistência limita-se a extrair o conjunto bruto via `findAll()`, transferindo todo o processamento de regras, contagens e filtros temporais para o `ApiService`.

2. **Isolamento de Contrato via DTO (`TimeDaDataDTO`):**
  * Criado especificamente para o endpoint `/time-da-data`.
  * Evita a exposição desnecessária da entidade interna `Time` e formata a resposta no padrão estipulado pelo desafio: nomes dos atletas mapeados como uma coleção simples de Strings (`List<String>`).

3. **Respostas HTTP Semânticas (`ResponseEntity`):**
  * `200 OK`: Operações resolvidas com sucesso (incluindo retornos de listas ou mapas vazios).
  * `201 Created`: Cadastro de novos recursos nas entidades básicas (`POST`).
  * `404 Not Found`: Resposta defensiva quando um recurso pontual não é encontrado na memória para a data ou intervalo informado.

4. **Distinção de Regras de Negócio (Contagem vs. Recorrência):**
    * Foi identificada a necessidade de separar as lógicas matemáticas de análise:
    * **`contagemPorFuncao`:** Focada na volume de profissionais. Utiliza a estrutura de dados `Set` para garantir a contagem de **integrantes únicos** por função no período, ignorando repetições do mesmo ID.
    * **`funcaoMaisRecorrente`:** Focada no uso em campo. Contabiliza o total de **escalações (aparições)** de cada função, permitindo que o mesmo integrante some múltiplos pontos caso participe de vários times.

---

## Catálogo de Endpoints Analíticos (`ApiController`)
**Rota Base:** `/api/services`

| Método | Endpoint | Parâmetros | Tipo de Retorno | Status Padrão |
| :--- | :--- | :--- | :--- | :--- |
| `GET` | `/time-da-data` | `data` (obrigatória) | `TimeDaDataDTO` | `200` / `404` |
| `GET` | `/integrante-mais-usado` | `dataInicial`, `dataFinal` (opcionais) | `Integrante` | `200` / `404` |
| `GET` | `/integrantes-do-time-mais-recorrente` | `dataInicial`, `dataFinal` (opcionais) | `List<Integrante>` | `200` |
| `GET` | `/funcao-mais-recorrente` | `dataInicial`, `dataFinal` (opcionais) | `Map<String, String>` | `200` |
| `GET` | `/clube-mais-recorrente` | `dataInicial`, `dataFinal` (opcionais) | `Map<String, String>` | `200` |
| `GET` | `/contagem-de-clubes` | `dataInicial`, `dataFinal` (opcionais) | `Map<String, Long>` | `200` |
| `GET` | `/contagem-por-funcao` | `dataInicial`, `dataFinal` (opcionais) | `Map<String, Long>` | `200` |

---

## Evidências de Validação (Pasta `/docs`)
O diretório `docs/` na raiz deste projeto contém as evidências de validação e garantia de qualidade:

1. **`testes-intellij/`**: Evidências de execução e barra verde da suíte de testes de integração via `MockMvc`, garantindo a resiliência dos 7 endpoints.
2. **`apidog/`**: Capturas das requisições reais HTTP demonstrando cenários de Sucesso (`200 OK`) e tratamento de exceções/recursos não encontrados (`404 Not Found`).# 🏆 Desafio Técnico Duxus - API Analítica de Escalação

Este projeto é a resolução do desafio técnico para desenvolvimento de uma API RESTful em Java com Spring Boot. O objetivo do sistema é gerenciar o cadastro de integrantes e times, além de fornecer endpoints analíticos sem a utilização de processamento em banco de dados.

---