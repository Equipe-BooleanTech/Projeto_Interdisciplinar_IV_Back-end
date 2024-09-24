package com.example.Restaurantto.PDV.dto.user;

import com.example.Restaurantto.PDV.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;
import java.util.UUID;

public record UserDTO(UUID id,
                      String email,
                      List<Role> roles,
                      String name,
                      String lastName,
                      String phone,
                      String cpf,
                      String cep,
                      String address,
                      String city,
                      String state,
                      String neighborhood,
                      @JsonIgnore String password) {
}
