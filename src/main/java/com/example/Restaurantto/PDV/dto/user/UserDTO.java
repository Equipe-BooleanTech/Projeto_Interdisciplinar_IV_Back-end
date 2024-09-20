package com.example.Restaurantto.PDV.dto.user;

import com.example.Restaurantto.PDV.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

public record UserDTO(Long id,
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
