package com.example.Restaurantto.PDV.dto.user;

import com.example.Restaurantto.PDV.enums.Role;

import java.util.UUID;

public record ProspectingUserDTO(String email,
                                 Role role,
                                 String fullName,
                                 String phone,
                                 String enterprise,
                                 String message,
                                 boolean isProspecting,
                                 boolean isEmployee){
}
