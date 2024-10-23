package com.example.Restaurantto.PDV.dto.user;

import com.example.Restaurantto.PDV.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;
import java.util.UUID;


public record CreateUserDTO (
                             String email,
                             Role role,
                             String fullName,
                             String phone,
                             String cpf,
                             String cep,
                             String address,
                             int addressNumber,
                             String city,
                             String state,
                             String neighborhood,
                             String cnpj,
                             String message,
                             String enterprise,
                             Boolean isProspecting,
                             String password,
                             Boolean isEmployee,
                             String function){
}
