# DevShowcase API

API REST desenvolvida em Java/Spring Boot para a plataforma DevShowcase.

## Grupo

- ROSIMÁRIA COSTA LOPES
- MARIA APARECIDA PAIVA
- DANIEL DE MACEDO SILVA

## Tecnologias

- Java 21
- Spring Boot
- Spring Web
- Spring Data JPA
- Bean Validation
- H2 (desenvolvimento local)
- PostgreSQL (produção)
- Swagger / OpenAPI
- Maven
- Docker

## Relacionamentos

- Profile 1:N Project
- Project N:N Technology
- Project 1:N Feedback

## Endpoints

### Profiles
- POST `/api/profiles`
- GET `/api/profiles/{id}`

### Technologies
- POST `/api/technologies`
- GET `/api/technologies`

### Projects
- POST `/api/projects`
- GET `/api/projects?page=0&size=10`
- GET `/api/projects?technology=Java&page=0&size=10`
- POST `/api/projects/{id}/feedbacks`
- PUT `/api/projects/{id}/upvote`

## Feedback

Exemplo:

```json
{
  "authorName": "Maria",
  "rating": 5,
  "comment": "Projeto muito bom"
}
```

A nota deve estar entre 1 e 5. Após cada feedback, a média do projeto é recalculada e armazenada.

## Paginação e filtro

Exemplo:

```text
GET /api/projects?technology=Java&page=0&size=10
```

## Swagger

Com a aplicação em execução:

```text
http://localhost:8080/swagger-ui.html
```

OpenAPI JSON:

```text
http://localhost:8080/v3/api-docs
```

## Como executar localmente

Requisitos:
- Java 21
- Maven

```bash
mvn spring-boot:run
```

Por padrão, o projeto utiliza H2 em arquivo:

```text
jdbc:h2:file:./data/devshowcase
```

## Variáveis de ambiente para produção

Configure no provedor de nuvem:

```text
DB_URL=jdbc:postgresql://HOST:5432/BANCO
DB_USERNAME=usuario
DB_PASSWORD=senha
H2_CONSOLE_ENABLED=false
SHOW_SQL=false
```

A porta pode ser fornecida automaticamente pela variável `PORT`.

## Tratamento de erros

A API possui tratamento global para:
- 400 Bad Request
- 404 Not Found
- erros de validação
- conflitos de dados
- erros internos

As respostas são retornadas em JSON padronizado.
