# eds-server

API simples com MongoDB para persistir usuários.

## Endpoints

- `POST /users` — Cria um usuário. Body:
  ```json
  { "login": "alice", "password": "secret" }
  ```
- `GET /users` — Lista usuários (id + login).

## Configuração

- Porta: `8081`
- Mongo (dev): `mongodb://localhost:27017`, database `server-db`

> Em testes, usamos Testcontainers (Mongo 6).

## Como rodar (dev)

```bash
# É necessário um Mongo local rodando na porta 27017.
# Dica: usando Docker
docker run -d --name mongo -p 27017:27017 mongo:6

# Build e run
./mvnw clean spring-boot:run
```

## Teste manual

```bash
# Criar usuário diretamente no server
curl -X POST http://localhost:8081/users \
     -H "Content-Type: application/json" \
     -d '{ "login": "bob", "password": "123" }'

# Listar usuários
curl http://localhost:8081/users
```

