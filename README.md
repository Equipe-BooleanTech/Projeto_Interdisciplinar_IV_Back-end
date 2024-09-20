
# Documentação da API
## Endpoints Públicos

#### Rota de criação de usuário

```http
  POST /api/users/create
```
Necessario o JSON da seguinte forma
```Json
{
  "email": "teste@teste.com",
  "role": "ROLE_ADMIN",
  "name": "João",
  "lastName": "Silva",
  "phone": "11999999999",
  "cpf": "123.456.789-87",
  "cep": "12345-678",
  "address": "Rua Exemplo, 123",
  "city": "São Paulo",
  "state": "SP",
  "neighborhood": "Centro",
  "password": "senhaSegura123"
}

```

#### Rota de login de usuário

```http
  POST /api/users/login
```
Necessario o JSON da seguinte forma
```Json
{
    "email":"teste@teste.com",
    "password": "senhaSegura123"
}
```
Retornará o token JWT

## Endpoints com autenticação para qualquer role
### Todas estas rotas necessitão passar o token para poder ser acessada

#### Rota de listagem de usuário

```http
  GET /api/users/get-users
```
Retornará JSON da seguinte forma
```Json
[
    {
        "id": 103,
        "email": "MODELO@teste.com",
        "roles": [
            "ROLE_ADMIN"
        ],
        "name": "João Atualizado",
        "lastName": "Silva Atualizado",
        "phone": "11988888888",
        "cpf": "123.456.789-180",
        "cep": "54321-678",
        "address": "Rua Atualizada, 321",
        "city": "Rio de Janeiro",
        "state": "RJ",
        "neighborhood": "Zona Sul"
    },
    {
        "id": 203,
        "email": "testechef@teste.com",
        "roles": [
            "ROLE_CHEF"
        ],
        "name": "João",
        "lastName": "Silva",
        "phone": "11999999999",
        "cpf": "123.456.789-87",
        "cep": "12345-678",
        "address": "Rua Exemplo, 123",
        "city": "São Paulo",
        "state": "SP",
        "neighborhood": "Centro"
    }
]

```

#### Rota de atualização de usuário

```http
  PUT /api/users/update/{id}
```
Necessario o JSON da seguinte forma
```Json
{
  "role": "ROLE_ADMIN",
  "name": "João Atualizado",
  "lastName": "Silva Atualizado",
  "phone": "11988888888",
  "cpf": "123.456.789-180",
  "cep": "54321-678",
  "address": "Rua Atualizada, 321",
  "city": "Rio de Janeiro",
  "state": "RJ",
  "neighborhood": "Zona Sul"
}

```
Não há como alterar email e senha por esta rota

#### Rota de exclusão de usuário

```http
  DELETE /api/users/delete/{id}
```
Não há necessidade de Json

#### Rota de atualização de senha de usuário

```http
  PUT /api/users/update-password
```
Necessario o JSON da seguinte forma
```Json

{
  "email": "testecheff@teste.com",
  "currentPassword": "senhaSegura123",
  "newPassword": "senhaSeguraAtualizada123"
}


```
