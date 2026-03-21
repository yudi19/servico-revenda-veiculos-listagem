# Plan: Serviço de Veículos com Arquitetura Hexagonal

O serviço expõe 4 endpoints REST para cadastro, edição e listagem de veículos. A implementação segue arquitetura hexagonal (Ports & Adapters): o **domínio** é puro Java sem dependências de framework, a **camada de aplicação** define os casos de uso via interfaces (ports), e os **adapters** conectam REST (entrada) e JPA/H2 (saída). O build.gradle também precisará de correções de dependências.

---

## Steps

1. **Corrigir `build.gradle`** — substituir `spring-boot-starter-webmvc` por `spring-boot-starter-web`, remover o artefato inválido `spring-boot-h2console`, e adicionar `spring-boot-starter-data-jpa` e `spring-boot-starter-validation` (para validação dos DTOs com `@NotBlank`, `@NotNull`)

2. **Configurar `application.properties`** — adicionar configurações H2:
   - `spring.datasource.url=jdbc:h2:mem:revendasdb`
   - `spring.h2.console.enabled=true`
   - `spring.jpa.hibernate.ddl-auto=create-drop`
   - `spring.jpa.show-sql=true`

3. **Criar domínio** em `domain/`
   - `domain/model/StatusVeiculo.java` — enum com `DISPONIVEL`, `VENDIDO`, `RESERVADO`
   - `domain/model/Veiculo.java` — POJO puro com Lombok (`@Data`): `id`, `marca`, `modelo`, `ano`, `cor`, `preco`, `placa`, `status`
   - `domain/exception/VeiculoNaoEncontradoException.java` — `RuntimeException` com mensagem

4. **Criar ports de entrada** em `application/port/in/`
   - `CadastrarVeiculoUseCase.java` — interface com método `cadastrar(Veiculo veiculo): Veiculo`
   - `EditarVeiculoUseCase.java` — interface com método `editar(Long id, Veiculo veiculo): Veiculo`
   - `ListarVeiculosUseCase.java` — interface com método `listar(): List<Veiculo>`
   - `BuscarVeiculoUseCase.java` — interface com método `buscar(Long id): Veiculo`

5. **Criar port de saída** em `application/port/out/`
   - `VeiculoRepositoryPort.java` — interface com: `salvar(Veiculo)`, `buscarPorId(Long)`, `listarTodos()`, `atualizar(Long, Veiculo)`

6. **Criar serviço de aplicação** em `application/service/`
   - `VeiculoService.java` — `@Service`, implementa as 4 interfaces de input port, recebe `VeiculoRepositoryPort` via injeção de construtor, lança `VeiculoNaoEncontradoException` quando o veículo não é encontrado no `editar`/`buscar`

7. **Criar adapter de persistência** em `adapter/out/persistence/`
   - `VeiculoJpaEntity.java` — `@Entity @Table(name="veiculos")` com `@Id @GeneratedValue`, enum `StatusVeiculo` mapeado como `@Enumerated(STRING)`
   - `VeiculoJpaRepository.java` — interface que estende `JpaRepository<VeiculoJpaEntity, Long>`
   - `VeiculoPersistenceMapper.java` — métodos estáticos `toDomain(VeiculoJpaEntity)` e `toEntity(Veiculo)`
   - `VeiculoPersistenceAdapter.java` — `@Component`, implementa `VeiculoRepositoryPort`

8. **Criar adapter web (REST)** em `adapter/in/web/`
   - `VeiculoRequestDTO.java` — record com `@NotBlank`/`@NotNull` nos campos
   - `VeiculoResponseDTO.java` — record para a resposta
   - `VeiculoWebMapper.java` — converte entre DTO e domain `Veiculo`
   - `VeiculoController.java` — `@RestController @RequestMapping("/veiculos")`, injeta os 4 use cases via construtor:
     - `POST /veiculos` → 201 Created
     - `PUT /veiculos/{id}` → 200 OK
     - `GET /veiculos` → 200 OK + lista
     - `GET /veiculos/{id}` → 200 OK
   - `GlobalExceptionHandler.java` — `@RestControllerAdvice` que retorna 404 para `VeiculoNaoEncontradoException`

---

## Estrutura de pacotes final

```
com.revendas.revendas.listagem/
├── ListagemApplication.java
├── domain/
│   ├── model/Veiculo.java
│   ├── model/StatusVeiculo.java
│   └── exception/VeiculoNaoEncontradoException.java
├── application/
│   ├── port/in/  (4 interfaces de use case)
│   ├── port/out/ (VeiculoRepositoryPort)
│   └── service/  (VeiculoService)
└── adapter/
    ├── in/web/   (Controller, DTOs, Mapper, ExceptionHandler)
    └── out/persistence/ (JpaEntity, JpaRepository, Adapter, Mapper)
```

---

## Verification

- Subir a aplicação e acessar `http://localhost:8080/h2-console` para confirmar schema criado
- `POST /veiculos` com JSON para criar um veículo → espera 201
- `GET /veiculos` → lista com o veículo criado
- `PUT /veiculos/1` editando status → espera 200
- `GET /veiculos/999` → espera 404

---

## Decisions

- Domain `Veiculo` é POJO puro (sem anotações JPA) — separação clara entre domínio e infraestrutura
- `VeiculoJpaEntity` separado do domínio — cada camada tem sua própria representação
- Use cases como interfaces separadas (ISP) em vez de uma única interface monolítica
- H2 em memória com `create-drop` — adequado para ambiente acadêmico
