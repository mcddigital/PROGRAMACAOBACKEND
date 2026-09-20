# DevShowcase API

Projeto da disciplina para implementação da fundação arquitetural da plataforma DevShowcase API.

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
- H2 Database
- Maven

## Relacionamentos

- Profile 1:N Project
- Project N:N Technology
- Project 1:N Feedback

## Endpoints

### Profile
- POST `/api/profiles`
- GET `/api/profiles/{id}`

### Technology
- POST `/api/technologies`
- GET `/api/technologies`

### Project
- POST `/api/projects`
- GET `/api/projects`

## Como executar

Requisitos:
- Java 21
- Maven

No terminal:

```bash
mvn spring-boot:run
```

A API ficará disponível em:

```text
http://localhost:8080
```

Console H2:

```text
http://localhost:8080/h2-console
```

Use no H2 Console:

```text
JDBC URL: jdbc:h2:file:./data/devshowcase
User Name: sa
Password: deixe vazio
```

## Exemplos

Criar perfil:

```json
{
  "name": "Daniel de Macedo Silva",
  "email": "daniel@email.com",
  "bio": "Desenvolvedor",
  "githubUrl": "https://github.com/daniel"
}
```

Criar tecnologia:

```json
{
  "name": "Java"
}
```

Criar projeto:

```json
{
  "title": "DevShowcase API",
  "description": "API de portfólio de desenvolvedores",
  "repositoryUrl": "https://github.com/seu-usuario/devshowcase-api",
  "profileId": 1,
  "technologyIds": [1]
}
```
