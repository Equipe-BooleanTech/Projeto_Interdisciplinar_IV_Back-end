package com.example.Restaurantto.PDV.dto.user;

import com.example.Restaurantto.PDV.enums.Role;
import com.example.Restaurantto.PDV.model.user.ModelRole;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record UserDTO(UUID id,
                      String email,
                      Role roles,
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
                      Boolean isEmployee,
                      String function,
                      @JsonIgnore String password,
                      LocalDate createdAt) {
}
