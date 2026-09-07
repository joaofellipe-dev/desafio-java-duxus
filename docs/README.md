# Documentação Técnica e Evidências de Validação

Este diretório reúne as evidências visuais de execução dos testes automatizados e das chamadas HTTP via Apidog, bem como o detalhamento arquitetural das decisões tomadas no backend.

---

## 📁 Estrutura do Diretório

```text
docs/
├── README.md                  # Este documento
├── apidog/                    # Prints das chamadas HTTP e contratos da API
│   ├── 01-post-criar-integrante-201.png
│   ├── 02-post-criar-time-201.png
│   ├── 03-get-time-da-data-200.png
│   ├── 04-get-funcao-recorrente-200.png
│   ├── 05-get-contagem-clubes-200.png
│   └── 06-get-time-da-data-404.png
└── testes-intellij/           # Prints da suíte de testes de integração (MockMvc)
    ├── api-controller-test.png
    └── api-service-test.png