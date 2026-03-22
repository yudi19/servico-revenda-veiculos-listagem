# 🚗 Serviço de Listagem — Revenda de Veículos

[![CI/CD Pipeline](https://github.com/yudi19/servico-revenda-veiculos-listagem/actions/workflows/deploy.yml/badge.svg)](https://github.com/yudi19/servico-revenda-veiculos-listagem/actions/workflows/deploy.yml)
[![SonarQube](https://github.com/yudi19/servico-revenda-veiculos-listagem/actions/workflows/sonar-pr.yml/badge.svg)](https://github.com/yudi19/servico-revenda-veiculos-listagem/actions/workflows/sonar-pr.yml)

Microserviço responsável pelo catálogo de veículos da plataforma de revenda. Desenvolvido com **Java 21**, **Spring Boot 4** e arquitetura hexagonal, expõe uma API REST para cadastro, edição e consulta de veículos com controle de status.

## 📋 Sumário

- [🚗 Serviço de Listagem — Revenda de Veículos](#-serviço-de-listagem--revenda-de-veículos)
  - [📋 Sumário](#-sumário)
  - [🎯 Sobre o Projeto](#-sobre-o-projeto)
    - [Funcionalidades Principais](#funcionalidades-principais)
  - [🏗 Arquitetura](#-arquitetura)
    - [Camadas](#camadas)
  - [🛠 Tecnologias](#-tecnologias)
  - [📦 Pré-requisitos](#-pré-requisitos)
  - [🚀 Instalação e Execução](#-instalação-e-execução)
    - [Executando Localmente](#executando-localmente)
    - [Executando com Docker](#executando-com-docker)
  - [📚 Documentação da API](#-documentação-da-api)
    - [Base URL](#base-url)
    - [🚗 Endpoints de Veículos](#-endpoints-de-veículos)
      - [1. Cadastrar Veículo](#1-cadastrar-veículo)
      - [2. Editar Veículo](#2-editar-veículo)
      - [3. Buscar Veículo por ID](#3-buscar-veículo-por-id)
      - [4. Listar Todos os Veículos](#4-listar-todos-os-veículos)
    - [Exemplos com cURL](#exemplos-com-curl)
  - [🧪 Testes](#-testes)
  - [🔄 CI/CD](#-cicd)
    - [Secrets necessários no repositório](#secrets-necessários-no-repositório)
  - [☁️ Infraestrutura](#️-infraestrutura)
  - [📄 Licença](#-licença)
  - [👥 Autores](#-autores)

## 🎯 Sobre o Projeto

O serviço de listagem centraliza o gerenciamento do estoque de veículos da plataforma. Permite cadastrar novos veículos, editar suas informações, consultar por ID e listar todo o catálogo disponível. É consumido pelos demais microserviços (como o de pagamentos) para obter os dados do veículo durante uma venda.

### Funcionalidades Principais

- ✅ Cadastro de veículos com validação de dados
- ✅ Edição de informações e status do veículo
- ✅ Consulta de veículo por ID
- ✅ Listagem completa do catálogo
- ✅ Controle de status: `DISPONIVEL`, `RESERVADO`, `VENDIDO`

## 🏗 Arquitetura

O projeto segue os princípios da Arquitetura Hexagonal:

```
src/main/java/com/revendas/revendas/listagem/
├── domain/                          # Camada de Domínio
│   ├── model/                       # Entidades: Veiculo, StatusVeiculo
│   └── exception/                   # VeiculoNaoEncontradoException
├── application/                     # Camada de Aplicação
│   ├── port/
│   │   ├── in/                      # Casos de uso (interfaces)
│   │   └── out/                     # Portas de saída (interfaces)
│   └── service/                     # VeiculoService
└── adapter/                         # Camada de Infraestrutura
    ├── in/
    │   └── web/                     # Controller REST + DTOs + Mapper
    └── out/
        └── persistence/             # Repositório JPA (H2)
```

### Camadas

- **Domain**: Entidade `Veiculo` com campos de identificação, precificação e status do estoque
- **Application**: Casos de uso `CadastrarVeiculo`, `EditarVeiculo`, `BuscarVeiculo`, `ListarVeiculos`
- **Adapter IN**: Controller REST (`/veiculos`) com validação via Bean Validation
- **Adapter OUT**: Repositório JPA com banco H2 em memória

## 🛠 Tecnologias

- **Java 21** — Linguagem de programação
- **Spring Boot 4** — Framework principal
- **Spring Data JPA** — Persistência de dados
- **H2 Database** — Banco de dados em memória
- **Lombok** — Redução de boilerplate
- **Gradle** — Gerenciamento de dependências e build
- **Docker** — Containerização
- **GitHub Actions** — CI/CD
- **JUnit 5 + Mockito** — Testes unitários
- **JaCoCo** — Cobertura de testes (mínimo 80%)

## 📦 Pré-requisitos

- **Java 21** ou superior
- **Gradle 8+** (ou use o wrapper incluído)
- **Docker** (opcional)

## 🚀 Instalação e Execução

### Executando Localmente

1. **Clone o repositório:**
```bash
git clone https://github.com/yudi19/servico-revenda-veiculos-listagem.git
cd servico-revenda-veiculos-listagem
```

2. **Execute a aplicação:**
```bash
./gradlew bootRun
```

A API estará disponível em `http://localhost:8080`  
Console H2: `http://localhost:8080/h2-console`

### Executando com Docker

1. **Build da imagem:**
```bash
docker build -t servico-revenda-veiculos-listagem .
```

2. **Execute o container:**
```bash
docker run -p 8080:8080 servico-revenda-veiculos-listagem
```

## 📚 Documentação da API

### Base URL
```
Local:      http://localhost:8080
Produção:   http://<ECS_PUBLIC_IP>:8080
```

---

### 🚗 Endpoints de Veículos

#### 1. Cadastrar Veículo

```http
POST /veiculos
Content-Type: application/json
```

**Corpo da Requisição:**
```json
{
  "marca": "Toyota",
  "modelo": "Corolla",
  "ano": 2023,
  "cor": "Prata",
  "preco": 120000.00,
  "placa": "ABC-1234",
  "status": "DISPONIVEL"
}
```

**Resposta de Sucesso (201 Created):**
```json
{
  "id": 1,
  "marca": "Toyota",
  "modelo": "Corolla",
  "ano": 2023,
  "cor": "Prata",
  "preco": 120000.00,
  "placa": "ABC-1234",
  "status": "DISPONIVEL"
}
```

---

#### 2. Editar Veículo

```http
PUT /veiculos/{id}
Content-Type: application/json
```

Mesmo corpo da requisição do cadastro. Retorna o veículo atualizado com status `200 OK`.

---

#### 3. Buscar Veículo por ID

```http
GET /veiculos/{id}
```

**Resposta de Sucesso (200 OK):** objeto `Veiculo` completo.

**Resposta de Erro (404 Not Found):**
```json
{
  "status": 404,
  "mensagem": "Veículo não encontrado com id: 99"
}
```

---

#### 4. Listar Todos os Veículos

```http
GET /veiculos
```

**Resposta de Sucesso (200 OK):** array com todos os veículos cadastrados.

**Status possíveis:** `DISPONIVEL` | `RESERVADO` | `VENDIDO`

---

### Exemplos com cURL

```bash
BASE="http://localhost:8080"

# Cadastrar veículo
curl -s -X POST "$BASE/veiculos" \
  -H "Content-Type: application/json" \
  -d '{"marca":"Toyota","modelo":"Corolla","ano":2023,"cor":"Prata","preco":120000.00,"placa":"ABC-1234","status":"DISPONIVEL"}'

# Listar todos os veículos
curl -s "$BASE/veiculos"

# Buscar veículo por ID
curl -s "$BASE/veiculos/1"

# Editar veículo
curl -s -X PUT "$BASE/veiculos/1" \
  -H "Content-Type: application/json" \
  -d '{"marca":"Toyota","modelo":"Corolla","ano":2023,"cor":"Branco","preco":118000.00,"placa":"ABC-1234","status":"RESERVADO"}'
```

## 🧪 Testes

```bash
# Executar todos os testes
./gradlew test

# Relatório de cobertura (JaCoCo)
open build/reports/jacoco/test/html/index.html
```

Cobertura de testes unitários nas camadas:
- **Domain Model**: campos e construção da entidade `Veiculo`
- **Application Service**: todos os casos de uso com Mockito
- **Controller**: testes de integração com MockMvc
- **Persistence Adapter**: mapeamento e persistência JPA

## 🔄 CI/CD

Pipeline automatizado via GitHub Actions:

- Ao **abrir PR para `main`** (`.github/workflows/sonar-pr.yml`):
  - ✅ Build e execução dos testes
  - ✅ Geração do relatório JaCoCo
  - ✅ Análise de qualidade no **SonarQube**

- Ao **fazer merge na `main`** (`.github/workflows/deploy.yml`):
  - ✅ Build com Gradle
  - ✅ Execução dos testes
  - ✅ Build e push da imagem Docker para **Amazon ECR**
  - ✅ Deploy automático no **Amazon ECS Fargate**
  - ✅ Aguarda estabilização do serviço

### Secrets necessários no repositório

| Secret | Descrição |
|--------|----------|
| `AWS_ACCESS_KEY_ID` | Access key com permissões ECR + ECS |
| `AWS_SECRET_ACCESS_KEY` | Secret key correspondente |
| `SONAR_TOKEN` | Token de autenticação do SonarCloud |
| `SONAR_HOST_URL` | URL do servidor Sonar (ex: `https://sonarcloud.io`) |

## ☁️ Infraestrutura

**Recursos AWS:**
- **ECS Cluster**: `revendas-cluster`
- **ECS Service**: `servico-revenda-veiculos-listagem`
- **ECR Repository**: `servico-revenda-veiculos-listagem`
- **CloudWatch Logs**: `/ecs/servico-listagem`
- **Região**: `us-east-1`

## 📄 Licença

Este projeto foi desenvolvido como trabalho acadêmico para a Pós-Tech FIAP.

## 👥 Autores

- **Fabio** — [@yudi19](https://github.com/yudi19)
