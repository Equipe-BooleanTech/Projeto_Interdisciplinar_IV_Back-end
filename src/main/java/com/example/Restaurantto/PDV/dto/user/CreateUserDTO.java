package com.example.Restaurantto.PDV.dto.user;

import com.example.Restaurantto.PDV.enums.Role;

import java.util.UUID;


public record CreateUserDTO (UUID id,
                             String email,
                             Role role,
                             String fullName,
                             String phone,
                             String cpf,
                             String cep,
                             String address,
                             String city,
                             String state,
                             String neighborhood,
                             String password){
}
