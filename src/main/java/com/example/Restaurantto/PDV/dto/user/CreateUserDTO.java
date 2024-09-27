package com.example.Restaurantto.PDV.dto.user;

import com.example.Restaurantto.PDV.enums.Role;

import java.util.UUID;


public record CreateUserDTO (String email,
                             String password,
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
                             String cnpj){
}
