
# Documentação da API
## Criado via Swagger
http://localhost:8081/swagger-ui/index.html

## Usuários

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

#### Rota de criação de usuário completo

```http
  POST /api/users/create-complete
```
Necessario o JSON da seguinte forma
```Json
{
  "email": "email@email.com",
  "role": "ROLE_ADMIN",
  "fullName": "string",
  "phone": "string",
  "cpf": "string",
  "cep": "string",
  "address": "string",
  "addressNumber": 0,
  "city": "string",
  "state": "string",
  "neighborhood": "string",
  "cnpj": "string",
  "message": "string",
  "enterprise": "string",
  "isProspecting": false,
  "password": "12345",
  "isEmployee": true,
  "function": "string"
}
```

#### Rota de criação de usuário prospecção

```http
  POST /api/users/prospects
```
Necessario o JSON da seguinte forma
```Json
{
  "email": "teste@teste.com",
  "fullName": "string",
  "phone": "string",
  "enterprise": "string",
  "message": "string",
  "isProspecting": true,
  "isEmployee": false
}
```

#### Rota de ativação de usuário prospecção

```http
  PUT /api/users/activate/{id}
```
Necessario o JSON da seguinte forma
```Json
{
  "cpf": "12345678901",
  "cep": "12345-678",
  "address": "Rua Exemplo, 100",
  "addressNumber": 100,
  "city": "Cidade Exemplo",
  "state": "Estado Exemplo",
  "neighborhood": "Bairro Exemplo",
  "cnpj": "12345678000199",
  "phone": "11999999999",
  "fullName": "Nome Completo Exemplo",
  "enterprise": "Empresa Exemplo",
  "message": "Mensagem Exemplo",
  "isEmployee": false,
  "password": "1234"
}

```


#### Rota de atualização de usuário

```http
  PUT /api/users/update/{id}
```
Necessario o JSON da seguinte forma
```Json
{
  "email": "email@email.com",
  "fullName": "string",
  "phone": "string",
  "cpf": "string",
  "cep": "string",
  "address": "string",
  "addressNumber": 3333,
  "city": "string",
  "state": "string",
  "neighborhood": "string",
  "cnpj": "string",
  "message": "string",
  "enterprise": "string",
  "isProspecting": true,
  "isEmployee": false,
  "function": "string"
}

```
Não há como alterar email, Role e senha por esta rota

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

#### Rota de listagem de usuário pelo ID

```http
  GET /api/users/get-users-by-id/{id}
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

#### Rota de atualização de role de usuário

```http
  PUT /api/users/roles/{id}
```
Necessario o JSON da seguinte forma
```Json
{
  "roles": "ROLE_USER"
}
```

#### Rota de listagem de usuário por tempo determinado

```http
  POST /api/users/list-users-by-period  LISTAR POR MÊS
  POST /api/users/list-users-by-period?groupingType=weekly LISTAR POR SEMANA
  POST /api/users/list-users-by-period?groupingType=weekly LISTAR POR ANO
```
Necessario o JSON da seguinte forma
```Json
{
    "startDate": "2024-11-01",
    "endDate": "2024-11-30"
}
```

## Produtos

### Fornecedor

#### Rota de criação de Fornecedor

```http
  POST /api/products/create-supplier
```
Necessario o JSON da seguinte forma
```Json
{
  "name": "teste data",
  "cnpj": "3225325132515",
  "contact": "13413248758",
  "phone": "2343415"
}
```

#### Rota de atualização de Fornecedor

```http
  PUT /api/products/update-supplier/{id}
```
Necessario o JSON da seguinte forma
```Json
{
  "name": "teste data",
  "cnpj": "3225325132515",
  "contact": "13413248758",
  "phone": "2343415"
}
```

#### Rota de exclusão de Fornecedor

```http
  DELETE /api/products/delete-supplier/{id}
```
Não há necessidade de Json

#### Rota de listagem de todos os Fornecedores

```http
  GET /api/products/get-supplier
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

#### Rota de listagem de Fornecedor pelo id

```http
  GET /api/products/get-supplier-by-id/{id}
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

#### Rota de listagem de Fornecedor por tempo determinado

```http
  POST /api/products/list-suppliers-by-period LISTAR POR MÊS
  POST /api/users/list-suppliers-by-period?groupingType=weekly LISTAR POR SEMANA
  POST /api/users/list-suppliers-by-period?groupingType=weekly LISTAR POR ANO

```
Necessario o JSON da seguinte forma
```Json
{
    "startDate": "2024-01-01",
    "endDate": "2025-01-31"
}

```

### Ingrediente

#### Rota de criação de Ingrediente

```http
  POST /api/products/create-ingredient
```
Necessario o JSON da seguinte forma
```Json
{
  "name": "teste",
  "price": 2.99,
  "unit": "KG",
  "quantity": 1.00,
  "description": "teste",
  "isAnimalOrigin": false,
  "sif": " ",
  "supplier": [
    {
      "name": "teste update"
    }
  ]
}
```

#### Rota de atualização de Ingrediente
```http
  PUT /api/products/update-ingredient/{id}
```
Necessario o JSON da seguinte forma
```Json
{
  "name": "teste1234",
  "price": 2.99,
  "unit": "KG",
  "quantity": 1.00,
  "description": "teste",
  "isAnimalOrigin": false,
  "sif": " ",
  "supplier": [
    {
      "name": "João dos sabores"
    }
  ]
}
```

#### Rota de exclusão de Ingrediente

```http
  DELETE /api/products/delete-ingredient/{id}
```
Não há necessidade de Json

#### Rota de listagem de todos os Ingredientes

```http
  GET /api/products/get-ingredients
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

#### Rota de listagem de Ingrediente pelo id

```http
  GET /api/products/get-ingredient-by-id/{id}
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

#### Rota de listagem de Ingrediente por tempo determinado

```http
  POST /api/products/list-ingredients-by-period LISTAR POR MÊS
  POST /api/users/list-ingredients-by-period?groupingType=weekly LISTAR POR SEMANA
  POST /api/users/list-ingredients-by-period?groupingType=weekly LISTAR POR ANO

```
Necessario o JSON da seguinte forma
```Json
{
    "startDate": "2024-01-01",
    "endDate": "2025-01-31"
}

```

## Fichas Técnicas

#### Rota de criação de ficha técnica

```http
  POST /api/datasheets/create-datasheet
```
Necessario o JSON da seguinte forma
```Json
{
  "name": "Ficha Técnica Pizza3",
  "ingredients": [    {
      "name": "teste2"
    }
  ]
}
```

#### Rota de atualização de ficha técnica
```http
  PUT /api/datasheets/update-datasheet/{id}
```
Necessario o JSON da seguinte forma
```Json
{
  "name": "Ficha Técnica Pizza3",
  "ingredients": [    {
      "name": "teste2"
    }
  ]
}
```

#### Rota de exclusão de ficha técnica

```http
  DELETE /api/datasheets/delete-datasheet/{id}
```
Não há necessidade de Json

#### Rota de listagem de todas os Fichas Técnicas

```http
  GET /api/datasheets/get-datasheets
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

#### Rota de listagem de ficha técnica pelo id

```http
  GET /api/datasheets/get-datasheet-by-id/{id}
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

#### Rota de listagem de ficha técnica por tempo determinado

```http
  POST /api/datasheets/list-datasheets-by-period LISTAR POR MÊS
  POST /api/datasheets/list-datasheets-by-period?groupingType=weekly LISTAR POR SEMANA
  POST /api/datasheets/list-datasheets-by-period?groupingType=weekly LISTAR POR ANO

```
Necessario o JSON da seguinte forma
```Json
{
    "startDate": "2024-01-01",
    "endDate": "2025-01-31"
}

```

## Grupo de Fichas Técnicas

#### Rota de criação de ficha técnica

```http
  POST /api/datasheets/create-datasheet
```
Necessario o JSON da seguinte forma
```Json
{
  "name": "Ficha Técnica Pizza3",
  "ingredients": [    {
      "name": "teste2"
    }
  ]
}
```

#### Rota de atualização de ficha técnica
```http
  PUT /api/datasheets/update-datasheet/{id}
```
Necessario o JSON da seguinte forma
```Json
{
  "name": "Ficha Técnica Pizza3",
  "ingredients": [    {
      "name": "teste2"
    }
  ]
}
```

#### Rota de exclusão de ficha técnica

```http
  DELETE /api/datasheets/delete-datasheet/{id}
```
Não há necessidade de Json

#### Rota de listagem de todas os Fichas Técnicas

```http
  GET /api/datasheets/get-datasheets
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

#### Rota de listagem de ficha técnica pelo id

```http
  GET /api/datasheets/get-datasheet-by-id/{id}
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

#### Rota de listagem de ficha técnica por tempo determinado

```http
  POST /api/datasheets/list-datasheets-by-period LISTAR POR MÊS
  POST /api/datasheets/list-datasheets-by-period?groupingType=weekly LISTAR POR SEMANA
  POST /api/datasheets/list-datasheets-by-period?groupingType=weekly LISTAR POR ANO

```
Necessario o JSON da seguinte forma
```Json
{
    "startDate": "2024-01-01",
    "endDate": "2025-01-31"
}

```

## Controle Financeiro

### Despesas

#### Rota de criação de Despesa

```http
  POST /api/financials/create-expense
```
Necessario o JSON da seguinte forma
```Json
{
    "description": "Compra de suprimentos de cozinha",
    "category": "INGREDIENTS",
    "amount": 599.00,
    "paymentDate": "2024-10-30"
}
```

#### Rota de atualização de despesa
```http
  PUT /api/financials/update-expense/{id}
```
Necessario o JSON da seguinte forma
```Json
{
    "description": "pagamentos de salarios da cozinha",
    "category": "SALARIES",
    "amount": 10000.00,
    "paymentDate": "2024-10-10"
}
```

#### Rota de exclusão de despesa

```http
  DELETE /api/financials/delete-expense/{id}
```
Não há necessidade de Json

#### Rota de listagem de todas as despesas

```http
  GET /api/financials/get-all-expenses
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

#### Rota de listagem de despesa pelo id

```http
  GET /api/financials/get-expense-by-id/{id}
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

#### Rota de listagem de despesa por tempo determinado

```http
  POST /api/financials/list-expenses-by-period LISTAR POR MÊS
  POST /api/financials/list-expenses-by-period?groupingType=weekly LISTAR POR SEMANA
  POST /api/financials/list-expenses-by-period?groupingType=weekly LISTAR POR ANO

```
Necessario o JSON da seguinte forma
```Json
{
    "startDate": "2024-01-01",
    "endDate": "2025-01-31"
}

```

#### Rota de listagem do total das despesas por periodo de tempo

```http
  POST /api/financials/total-expenses
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

### Receitas

#### Rota de criação de receita

```http
  POST /api/financials/create-revenue
```
Necessario o JSON da seguinte forma
```Json
{
    "amount": 50000.00,
    "saleDate": "2024-01-20"
}
```

#### Rota de atualização de receita
```http
  PUT /api/financials/update-revenue{id}
```
Necessario o JSON da seguinte forma
```Json
{
    "amount": 50000.00,
    "saleDate": "2024-01-20"
}
```

#### Rota de exclusão de receita

```http
  DELETE /api/financials/delete-revenue/{id}
```
Não há necessidade de Json

#### Rota de listagem de todas as Receitas

```http
  GET /api/financials/get-all-revenues
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

#### Rota de listagem de receita pelo id

```http
  GET /api/financials/get-revenue-by-id/{id}
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

#### Rota de listagem de receita por tempo determinado

```http
  POST /api/financials/list-revenues-by-period LISTAR POR MÊS
  POST /api/financials/list-revenues-by-period?groupingType=weekly LISTAR POR SEMANA
  POST /api/financials/list-revenues-by-period?groupingType=weekly LISTAR POR ANO

```
Necessario o JSON da seguinte forma
```Json
{
    "startDate": "2024-01-01",
    "endDate": "2025-01-31"
}

```

#### Rota de listagem do total das receita por periodo de tempo

```http
  POST /api/financials/total-revenue
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

#### Rota de calculo de fluxo de caixa

```http
  POST /api/financials/cash-flow
```
Necessario o JSON da seguinte forma
```Json
{
    "startDate": "2024-01-01",
    "endDate": "2025-01-31"
}

```

