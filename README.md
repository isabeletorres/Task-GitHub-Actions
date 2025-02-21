# GITHUB ACTIONS ( Testando API-SERVEREST )

## Pré-requisitos

### Ferramentas/Tecnologias:

- [Java JDK](https://www.oracle.com/java/technologies/javase/javase8-archive-downloads.html) - Como tecnologia (linguagem de programação)
- [TestNG](https://testng.org/) - Como TestRunner
- [Maven](https://maven.apache.org/) - Como gerenciador de dependências
- [IntelliJ IDEA](https://www.jetbrains.com/idea/download/) - Como sugestão de IDE

### Time QA
```
- Cainan Machado
- Isabele Torres
```
### Análise de Código com CodeQL

O projeto conta com uma pipeline configurada para realizar análise estática de segurança utilizando CodeQL. Essa análise permite identificar vulnerabilidades e problemas de segurança no código antes de sua implementação em produção. 

![image](https://github.com/user-attachments/assets/cf12552b-c7b6-498e-82fa-04d859a13827)

### Pipeline de Testes

A pipeline de testes executa automaticamente os testes na seguinte sequência:

HealthCheck: Verifica se o sistema está respondendo corretamente e se os serviços essenciais estão ativos.

Contrato: Testa a conformidade das APIs com os contratos definidos.

Funcionais: Valida se as funcionalidades do sistema estão operando conforme esperado.

![image](https://github.com/user-attachments/assets/a036fdc2-5307-46ca-9ceb-4aa1a211028d)

Report: Gera relatórios com os resultados dos testes para acompanhamento e análise e notificação via Discord

![Captura de tela 2025-02-20 135729](https://github.com/user-attachments/assets/dfd4539a-2b5b-41c3-b258-17e6b7dd0483)

![image](https://github.com/user-attachments/assets/f0b0cd68-0cf7-4bc0-b99a-02ce06fbe613)

### Arquitetura do projeto 


```
Projeto
│── .github
│   ├── workflows
│       ├── workflow-tests.yaml
│       ├── workflowCodeQL.yaml
│── src
│   ├── main
│   │   ├── java
│   │       ├── com
│   │           ├── vemser
│   │               ├── rest
│   │                   ├── client
│   │                       ├── BaseClient
│   │                       ├── LoginClient
│   │                       ├── ProdutoClient
│   │                       ├── UsuarioClient
│   │                   ├── data
│   │                       ├── factory
│   │                           ├── LoginDataFactory
│   │                           ├── ProdutoDataFactory
│   │                           ├── UsuarioDataFactory
│   │                   ├── model
│   │                       ├── Login
│   │                       ├── Produto
│   │                       ├── ProdutoResponse
│   │                       ├── Usuario
│   │                       ├── UsuarioResponse
│   │                   ├── utils
│   │                       ├── ConfigUtils
│   ├── test
│   │   ├── java
│   │       ├── com
│   │           ├── vemser
│   │               ├── rest
│   │                   ├── tests
│   │                       ├── login
│   │                           ├── LoginTest
│   │                       ├── produtos
│   │                           ├── BuscarProdutoTest
│   │                       ├── usuarios
│   │                           ├── AtualizarUsuariosTest
│   │                           ├── BuscarUsuariosTest
│   │                           ├── CadastrarUsuariosTest
│   │                           ├── DeleteUsuariosTest
│   │                       ├── HealthCheck
│   │   ├── resources
│   ├── tests-suites
│       ├── Contrato.xml
│       ├── Funcionais.xml
│       ├── HealthCheck.xml
```

### Clonar o projeto
```bash
 git clone https://github.com/isabeletorres/Task-GitHub-Actions.git
