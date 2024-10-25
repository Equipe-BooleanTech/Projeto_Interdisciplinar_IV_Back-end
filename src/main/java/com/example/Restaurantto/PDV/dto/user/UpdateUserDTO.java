package com.example.Restaurantto.PDV.dto.user;

public record UpdateUserDTO(
        String email,
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
        String function
        ) {
}
