package com.example.Restaurantto.PDV.dto.user;

public record UpdatePasswordDTO( String email,
                                 String currentPassword,
                                 String newPassword) {
}
