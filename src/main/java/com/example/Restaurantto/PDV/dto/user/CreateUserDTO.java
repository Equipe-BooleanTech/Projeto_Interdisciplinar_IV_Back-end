package com.example.Restaurantto.PDV.dto.user;

import com.example.Restaurantto.PDV.enums.Role;

import java.util.List;
import java.util.UUID;


public record CreateUserDTO (UUID id,
                             String email,
                             Role role,
                             String name,
                             String lastName,
                             String phone,
                             String cpf,
                             String cep,
                             String address,
                             String city,
                             String state,
                             String neighborhood,
                             String password){
}
