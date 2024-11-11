
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
{
    "content": [
        {
            "id": "907044f1-00fd-4aa8-a385-800efc08fa61",
            "email": "admin@admin.com",
            "roles": "ROLE_ADMIN",
            "fullName": null,
            "phone": null,
            "cpf": null,
            "cep": null,
            "address": null,
            "addressNumber": 0,
            "city": null,
            "state": null,
            "neighborhood": null,
            "cnpj": null,
            "message": null,
            "enterprise": null,
            "isProspecting": false,
            "isEmployee": false,
            "function": null,
            "createdAt": null
        },
        {
            "id": "8192a4fe-144b-48a9-9e83-226559b2c273",
            "email": "teste@teste.com",
            "roles": "ROLE_USER",
            "fullName": "Nome Completo Exemplo",
            "phone": "11999999999",
            "cpf": "12345678901",
            "cep": "12345-678",
            "address": "Rua Exemplo, 100",
            "addressNumber": 100,
            "city": "Cidade Exemplo",
            "state": "Estado Exemplo",
            "neighborhood": "Bairro Exemplo",
            "cnpj": "12345678000199",
            "message": "string",
            "enterprise": "Empresa Exemplo",
            "isProspecting": false,
            "isEmployee": false,
            "function": null,
            "createdAt": null
        },
        {
            "id": "059ffdb4-d532-46b1-9baf-a0a0c09f6901",
            "email": "Ricardo2@chef.com",
            "roles": "ROLE_USER",
            "fullName": "stringsom",
            "phone": "string",
            "cpf": "string2",
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
            "function": "string",
            "createdAt": null
        },
        {
            "id": "5417349e-23cf-42a1-b135-a3943f6467e6",
            "email": "Ricardo882@chef.com",
            "roles": "ROLE_CHEF",
            "fullName": "string",
            "phone": "string",
            "cpf": "string24",
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
            "isEmployee": true,
            "function": "string",
            "createdAt": "2024-11-11"
        }
    ],
    "pageable": {
        "pageNumber": 0,
        "pageSize": 10,
        "sort": {
            "empty": true,
            "sorted": false,
            "unsorted": true
        },
        "offset": 0,
        "paged": true,
        "unpaged": false
    },
    "last": true,
    "totalPages": 1,
    "totalElements": 4,
    "size": 10,
    "number": 0,
    "sort": {
        "empty": true,
        "sorted": false,
        "unsorted": true
    },
    "first": true,
    "numberOfElements": 4,
    "empty": false
}
```

#### Rota de listagem de usuário pelo ID

```http
  GET /api/users/get-users-by-id/{id}
```
Retornará JSON da seguinte forma
```Json
{
    "id": "5417349e-23cf-42a1-b135-a3943f6467e6",
    "email": "Ricardo882@chef.com",
    "password": "$2a$10$3egL3aEqMzRoq3Te71mSVeBGM4GDlMScBDr9ff6dLUrrdc48IgS0i",
    "role": {
        "id": "a3b5d256-71f9-40bc-aa41-7e545e723e24",
        "name": "ROLE_CHEF"
    },
    "fullName": "string",
    "phone": "string",
    "cpf": "string24",
    "cep": "string",
    "address": "string",
    "addressNumber": 0,
    "city": "string",
    "state": "string",
    "neighborhood": "string",
    "enterprise": "string",
    "cnpj": "string",
    "message": "string",
    "function": "string",
    "createdAt": "2024-11-11",
    "employee": true,
    "prospecting": false,
    "readyForActivation": false
}
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
  POST /api/users/list-users-by-period?groupingType=yearly LISTAR POR ANO
```
Necessario o JSON da seguinte forma
```Json
{
    "startDate": "2024-11-01",
    "endDate": "2024-11-30"
}
```

Retornará JSON da seguinte forma
```Json
{
    "novembro2024": {
        "ingredients": [
            {
                "id": "5417349e-23cf-42a1-b135-a3943f6467e6",
                "email": "Ricardo882@chef.com",
                "roles": "ROLE_CHEF",
                "fullName": "string",
                "phone": "string",
                "cpf": "string24",
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
                "isEmployee": true,
                "function": "string",
                "createdAt": "2024-11-11"
            }
        ],
        "total": 1
    }
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
{
    "content": [
        {
            "id": "a13f6fd4-811f-4910-aefd-611e499524d9",
            "name": "teste update",
            "cnpj": "3225325132515",
            "contact": "13413248758",
            "phone": "2343415",
            "createdAt": null
        },
        {
            "id": "edc8ab4d-7429-48b7-8846-3913c332ad7e",
            "name": "teste data",
            "cnpj": "3225325132515",
            "contact": "13413248758",
            "phone": "2343415",
            "createdAt": "2024-11-11"
        }
    ],
    "pageable": {
        "pageNumber": 0,
        "pageSize": 10,
        "sort": {
            "empty": true,
            "sorted": false,
            "unsorted": true
        },
        "offset": 0,
        "paged": true,
        "unpaged": false
    },
    "last": true,
    "totalPages": 1,
    "totalElements": 2,
    "size": 10,
    "number": 0,
    "sort": {
        "empty": true,
        "sorted": false,
        "unsorted": true
    },
    "first": true,
    "numberOfElements": 2,
    "empty": false
}

```

#### Rota de listagem de Fornecedor pelo id

```http
  GET /api/products/get-supplier-by-id/{id}
```
Retornará JSON da seguinte forma
```Json
{
    "id": "a13f6fd4-811f-4910-aefd-611e499524d9",
    "name": "teste update",
    "cnpj": "3225325132515",
    "contact": "13413248758",
    "phone": "2343415",
    "createdAt": null
}

```

#### Rota de listagem de Fornecedor por tempo determinado

```http
  POST /api/products/list-suppliers-by-period LISTAR POR MÊS
  POST /api/users/list-suppliers-by-period?groupingType=weekly LISTAR POR SEMANA
  POST /api/users/list-suppliers-by-period?groupingType=yearly LISTAR POR ANO

```
Necessario o JSON da seguinte forma
```Json
{
    "startDate": "2024-01-01",
    "endDate": "2025-01-31"
}

```

Retornará JSON da seguinte forma
```Json
{
    "novembro2024": {
        "suppliers": [
            {
                "id": "edc8ab4d-7429-48b7-8846-3913c332ad7e",
                "name": "teste data",
                "cnpj": "3225325132515",
                "contact": "13413248758",
                "phone": "2343415",
                "createdAt": "2024-11-11"
            }
        ],
        "total": 1
    }
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
{
    "content": [
        {
            "id": "1152ebfa-81a2-44e4-bee1-2faf4998a438",
            "name": "teste2",
            "supplier": [
                {
                    "id": "a13f6fd4-811f-4910-aefd-611e499524d9",
                    "name": "teste update",
                    "cnpj": "3225325132515",
                    "contact": "13413248758",
                    "phone": "2343415",
                    "createdAt": null
                }
            ],
            "price": 2.99,
            "unit": "KG",
            "quantity": 1.0,
            "description": "teste",
            "isAnimalOrigin": false,
            "sif": " ",
            "createdAt": null
        },
        {
            "id": "2f0a3f60-b91f-460c-bcad-64c64c9302ca",
            "name": "teste com data",
            "supplier": [
                {
                    "id": "a13f6fd4-811f-4910-aefd-611e499524d9",
                    "name": "teste update",
                    "cnpj": "3225325132515",
                    "contact": "13413248758",
                    "phone": "2343415",
                    "createdAt": null
                }
            ],
            "price": 2.99,
            "unit": "KG",
            "quantity": 1.0,
            "description": "teste",
            "isAnimalOrigin": false,
            "sif": " ",
            "createdAt": null
        },
        {
            "id": "1a14fff9-1ddf-4692-a5bd-d0bafafd6c50",
            "name": "teste com data automatica",
            "supplier": [
                {
                    "id": "a13f6fd4-811f-4910-aefd-611e499524d9",
                    "name": "teste update",
                    "cnpj": "3225325132515",
                    "contact": "13413248758",
                    "phone": "2343415",
                    "createdAt": null
                }
            ],
            "price": 2.99,
            "unit": "KG",
            "quantity": 1.0,
            "description": "teste",
            "isAnimalOrigin": false,
            "sif": " ",
            "createdAt": "2024-11-10"
        }
    ],
    "pageable": {
        "pageNumber": 0,
        "pageSize": 10,
        "sort": {
            "empty": true,
            "sorted": false,
            "unsorted": true
        },
        "offset": 0,
        "paged": true,
        "unpaged": false
    },
    "last": true,
    "totalPages": 1,
    "totalElements": 3,
    "size": 10,
    "number": 0,
    "sort": {
        "empty": true,
        "sorted": false,
        "unsorted": true
    },
    "first": true,
    "numberOfElements": 3,
    "empty": false
}
```

#### Rota de listagem de Ingrediente pelo id

```http
  GET /api/products/get-ingredient-by-id/{id}
```
Retornará JSON da seguinte forma
```Json
{
    "id": "1152ebfa-81a2-44e4-bee1-2faf4998a438",
    "suppliers": [
        {
            "id": "a13f6fd4-811f-4910-aefd-611e499524d9",
            "name": "teste update",
            "cnpj": "3225325132515",
            "contact": "13413248758",
            "phone": "2343415",
            "createdAt": null
        }
    ],
    "name": "teste2",
    "price": 2.99,
    "unit": "KG",
    "quantity": 1.0,
    "description": "teste",
    "isAnimalOrigin": false,
    "sif": " ",
    "createdAt": null
}

```

#### Rota de listagem de Ingrediente por tempo determinado

```http
  POST /api/products/list-ingredients-by-period LISTAR POR MÊS
  POST /api/users/list-ingredients-by-period?groupingType=weekly LISTAR POR SEMANA
  POST /api/users/list-ingredients-by-period?groupingType=yearly LISTAR POR ANO

```
Necessario o JSON da seguinte forma
```Json
{
    "startDate": "2024-01-01",
    "endDate": "2025-01-31"
}

```

Retornará JSON da seguinte forma
```Json
{
    "Week 46, 2024": {
        "ingredients": [
            {
                "id": "1a14fff9-1ddf-4692-a5bd-d0bafafd6c50",
                "name": "teste com data automatica",
                "supplier": [
                    {
                        "id": "a13f6fd4-811f-4910-aefd-611e499524d9",
                        "name": "teste update",
                        "cnpj": "3225325132515",
                        "contact": "13413248758",
                        "phone": "2343415",
                        "createdAt": null
                    }
                ],
                "price": 2.99,
                "unit": "KG",
                "quantity": 1.0,
                "description": "teste",
                "isAnimalOrigin": false,
                "sif": " ",
                "createdAt": "2024-11-10"
            }
        ],
        "total": 1
    }
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
{
    "content": [
        {
            "id": "a2956211-d17f-4ac6-9896-64037cadb0ba",
            "name": "Ficha Técnica Pizza",
            "ingredients": [
                {
                    "name": "teste2",
                    "supplier": [
                        {
                            "id": "a13f6fd4-811f-4910-aefd-611e499524d9",
                            "name": "teste update",
                            "cnpj": "3225325132515",
                            "contact": "13413248758",
                            "phone": "2343415",
                            "createdAt": null
                        }
                    ],
                    "price": 2.99,
                    "unit": "KG",
                    "quantity": 1.0,
                    "description": "teste",
                    "isAnimalOrigin": false,
                    "sif": " ",
                    "createdAt": null
                }
            ],
            "createdAt": null
        },
        {
            "id": "be659b11-b34b-42cd-b916-328fb677a0e5",
            "name": "Ficha Técnica Pizza3",
            "ingredients": [
                {
                    "name": "teste2",
                    "supplier": [
                        {
                            "id": "a13f6fd4-811f-4910-aefd-611e499524d9",
                            "name": "teste update",
                            "cnpj": "3225325132515",
                            "contact": "13413248758",
                            "phone": "2343415",
                            "createdAt": null
                        }
                    ],
                    "price": 2.99,
                    "unit": "KG",
                    "quantity": 1.0,
                    "description": "teste",
                    "isAnimalOrigin": false,
                    "sif": " ",
                    "createdAt": null
                }
            ],
            "createdAt": "2024-11-11"
        }
    ],
    "pageable": {
        "pageNumber": 0,
        "pageSize": 10,
        "sort": {
            "empty": true,
            "sorted": false,
            "unsorted": true
        },
        "offset": 0,
        "paged": true,
        "unpaged": false
    },
    "last": true,
    "totalPages": 1,
    "totalElements": 2,
    "size": 10,
    "number": 0,
    "sort": {
        "empty": true,
        "sorted": false,
        "unsorted": true
    },
    "first": true,
    "numberOfElements": 2,
    "empty": false
}

```

#### Rota de listagem de ficha técnica pelo id

```http
  GET /api/datasheets/get-datasheet-by-id/{id}
```
Retornará JSON da seguinte forma
```Json
{
    "id": "be659b11-b34b-42cd-b916-328fb677a0e5",
    "name": "Ficha Técnica Pizza3",
    "ingredients": [
        {
            "id": "1152ebfa-81a2-44e4-bee1-2faf4998a438",
            "suppliers": [
                {
                    "id": "a13f6fd4-811f-4910-aefd-611e499524d9",
                    "name": "teste update",
                    "cnpj": "3225325132515",
                    "contact": "13413248758",
                    "phone": "2343415",
                    "createdAt": null
                }
            ],
            "name": "teste2",
            "price": 2.99,
            "unit": "KG",
            "quantity": 1.0,
            "description": "teste",
            "isAnimalOrigin": false,
            "sif": " ",
            "createdAt": null
        }
    ],
    "createdAt": "2024-11-11"
}
```

#### Rota de listagem de ficha técnica por tempo determinado

```http
  POST /api/datasheets/list-datasheets-by-period LISTAR POR MÊS
  POST /api/datasheets/list-datasheets-by-period?groupingType=weekly LISTAR POR SEMANA
  POST /api/datasheets/list-datasheets-by-period?groupingType=yearly LISTAR POR ANO

```
Necessario o JSON da seguinte forma
```Json
{
    "startDate": "2024-01-01",
    "endDate": "2025-01-31"
}

```

Retornará JSON da seguinte forma
```Json
{
    "Year 2024": {
        "ingredients": [
            {
                "id": "be659b11-b34b-42cd-b916-328fb677a0e5",
                "name": "Ficha Técnica Pizza3",
                "ingredients": [
                    {
                        "name": "teste2",
                        "supplier": [
                            {
                                "id": "a13f6fd4-811f-4910-aefd-611e499524d9",
                                "name": "teste update",
                                "cnpj": "3225325132515",
                                "contact": "13413248758",
                                "phone": "2343415",
                                "createdAt": null
                            }
                        ],
                        "price": 2.99,
                        "unit": "KG",
                        "quantity": 1.0,
                        "description": "teste",
                        "isAnimalOrigin": false,
                        "sif": " ",
                        "createdAt": null
                    }
                ],
                "createdAt": "2024-11-11"
            }
        ],
        "total": 1
    }
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
  POST /api/datasheets/list-datasheets-by-period?groupingType=yearly LISTAR POR ANO

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
{
    "content": [
        {
            "id": "fabaefdf-da30-4f09-b6a5-9ee52354a92e",
            "description": "pagamentos de salarios da cozinha",
            "category": "SALARIES",
            "amount": 10000.00,
            "paymentDate": "2024-10-10"
        },
        {
            "id": "aa66eced-7958-4b04-902e-452d198b579d",
            "description": "Compra de suprimentos de cozinha",
            "category": "INGREDIENTS",
            "amount": 50.00,
            "paymentDate": "2024-10-15"
        },
        {
            "id": "2c643480-ab6b-4140-93d9-d2809694cba9",
            "description": "Compra de suprimentos de cozinha",
            "category": "INGREDIENTS",
            "amount": 134.00,
            "paymentDate": "2024-10-20"
        },
        {
            "id": "a0b18ea5-a08b-4000-8792-35f620a204da",
            "description": "Compra de suprimentos de cozinha",
            "category": "INGREDIENTS",
            "amount": 13334.00,
            "paymentDate": "2024-10-22"
        },
        {
            "id": "b0b728af-2626-44f9-8d33-66335b37c550",
            "description": "Compra de suprimentos de cozinha",
            "category": "INGREDIENTS",
            "amount": 599.00,
            "paymentDate": "2024-10-30"
        },
        {
            "id": "c7047022-029e-41d6-b3de-601102391e06",
            "description": "Compra de suprimentos de cozinha",
            "category": "INGREDIENTS",
            "amount": 599.00,
            "paymentDate": "2024-10-30"
        }
    ],
    "pageable": {
        "pageNumber": 0,
        "pageSize": 10,
        "sort": {
            "empty": true,
            "sorted": false,
            "unsorted": true
        },
        "offset": 0,
        "paged": true,
        "unpaged": false
    },
    "last": true,
    "totalPages": 1,
    "totalElements": 6,
    "size": 10,
    "number": 0,
    "sort": {
        "empty": true,
        "sorted": false,
        "unsorted": true
    },
    "first": true,
    "numberOfElements": 6,
    "empty": false
}
```

#### Rota de listagem de despesa pelo id

```http
  GET /api/financials/get-expense-by-id/{id}
```
Retornará JSON da seguinte forma
```Json
{
    "id": "2c643480-ab6b-4140-93d9-d2809694cba9",
    "description": "Compra de suprimentos de cozinha",
    "category": "INGREDIENTS",
    "amount": 134.00,
    "paymentDate": "2024-10-20"
}
```

#### Rota de listagem de despesa por tempo determinado

```http
  POST /api/financials/list-expenses-by-period LISTAR POR MÊS
  POST /api/financials/list-expenses-by-period?groupingType=weekly LISTAR POR SEMANA
  POST /api/financials/list-expenses-by-period?groupingType=yearly LISTAR POR ANO

```
Necessario o JSON da seguinte forma
```Json
{
    "startDate": "2024-01-01",
    "endDate": "2025-01-31"
}

```

Retornará JSON da seguinte forma
```Json
{
    "outubro2024": {
        "expenses": [
            {
                "id": "fabaefdf-da30-4f09-b6a5-9ee52354a92e",
                "description": "pagamentos de salarios da cozinha",
                "category": "SALARIES",
                "amount": 10000.00,
                "paymentDate": "2024-10-10"
            },
            {
                "id": "aa66eced-7958-4b04-902e-452d198b579d",
                "description": "Compra de suprimentos de cozinha",
                "category": "INGREDIENTS",
                "amount": 50.00,
                "paymentDate": "2024-10-15"
            },
            {
                "id": "2c643480-ab6b-4140-93d9-d2809694cba9",
                "description": "Compra de suprimentos de cozinha",
                "category": "INGREDIENTS",
                "amount": 134.00,
                "paymentDate": "2024-10-20"
            },
            {
                "id": "a0b18ea5-a08b-4000-8792-35f620a204da",
                "description": "Compra de suprimentos de cozinha",
                "category": "INGREDIENTS",
                "amount": 13334.00,
                "paymentDate": "2024-10-22"
            },
            {
                "id": "b0b728af-2626-44f9-8d33-66335b37c550",
                "description": "Compra de suprimentos de cozinha",
                "category": "INGREDIENTS",
                "amount": 599.00,
                "paymentDate": "2024-10-30"
            },
            {
                "id": "c7047022-029e-41d6-b3de-601102391e06",
                "description": "Compra de suprimentos de cozinha",
                "category": "INGREDIENTS",
                "amount": 599.00,
                "paymentDate": "2024-10-30"
            }
        ],
        "total": 6
    }
}
```

#### Rota de listagem do total das despesas por periodo de tempo

```http
  POST /api/financials/total-expenses
```
Necessario o JSON da seguinte forma
```Json
{
    "startDate": "2024-01-01",
    "endDate": "2025-01-31"
}

```
Retornará JSON da seguinte forma
```Json
{
    "message": "Total de Despesas",
    "information": 24716.00
}

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
{
    "content": [
        {
            "id": "224d03ea-1933-4f55-af40-ad6b569ab696",
            "amount": 1200.00,
            "saleDate": "2024-01-15"
        },
        {
            "id": "461ab0ca-2d4a-4b32-881a-f8879b0ba510",
            "amount": 23600.00,
            "saleDate": "2024-01-20"
        },
        {
            "id": "260f2f97-aae9-4413-9884-48879fa912c6",
            "amount": 322.00,
            "saleDate": "2024-01-21"
        },
        {
            "id": "c694255a-53e4-42f4-a14c-2938d51eb0c3",
            "amount": 855.00,
            "saleDate": "2024-01-19"
        }
    ],
    "pageable": {
        "pageNumber": 0,
        "pageSize": 10,
        "sort": {
            "empty": true,
            "sorted": false,
            "unsorted": true
        },
        "offset": 0,
        "paged": true,
        "unpaged": false
    },
    "last": true,
    "totalPages": 1,
    "totalElements": 4,
    "size": 10,
    "number": 0,
    "sort": {
        "empty": true,
        "sorted": false,
        "unsorted": true
    },
    "first": true,
    "numberOfElements": 4,
    "empty": false
}

```

#### Rota de listagem de receita pelo id

```http
  GET /api/financials/get-revenue-by-id/{id}
```
Retornará JSON da seguinte forma
```Json
{
    "id": "260f2f97-aae9-4413-9884-48879fa912c6",
    "amount": 322.00,
    "saleDate": "2024-01-21"
}
```

#### Rota de listagem de receita por tempo determinado

```http
  POST /api/financials/list-revenues-by-period LISTAR POR MÊS
  POST /api/financials/list-revenues-by-period?groupingType=weekly LISTAR POR SEMANA
  POST /api/financials/list-revenues-by-period?groupingType=yearly LISTAR POR ANO

```
Necessario o JSON da seguinte forma
```Json
{
    "startDate": "2024-01-01",
    "endDate": "2025-01-31"
}

```

Retornará JSON da seguinte forma
```Json
{
    "janeiro2024": {
        "revenue": [
            {
                "id": "224d03ea-1933-4f55-af40-ad6b569ab696",
                "amount": 1200.00,
                "saleDate": "2024-01-15"
            },
            {
                "id": "461ab0ca-2d4a-4b32-881a-f8879b0ba510",
                "amount": 23600.00,
                "saleDate": "2024-01-20"
            },
            {
                "id": "260f2f97-aae9-4413-9884-48879fa912c6",
                "amount": 322.00,
                "saleDate": "2024-01-21"
            },
            {
                "id": "c694255a-53e4-42f4-a14c-2938d51eb0c3",
                "amount": 855.00,
                "saleDate": "2024-01-19"
            }
        ],
        "total": 4
    }
}
```

#### Rota de listagem do total das receita por periodo de tempo

```http
  POST /api/financials/total-revenue
```

Necessario o JSON da seguinte forma
```Json
{
    "startDate": "2024-01-01",
    "endDate": "2025-01-31"
}

```

Retornará JSON da seguinte forma
```Json
{
    "message": "Total de Receitas",
    "information": 25977.00
}
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

Retornará JSON da seguinte forma
```Json
{
    "startDate": "2024-01-01",
    "endDate": "2025-01-31",
    "totalRevenue": 25977.00,
    "totalExpenses": 24716.00,
    "finalBalance": 1261.00
}
```

