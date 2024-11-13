package com.example.Restaurantto.PDV.dto.auth;

import java.util.UUID;

public record LoginResponseDTO(UUID id,
                               String fullName,
                               String token) {
}
