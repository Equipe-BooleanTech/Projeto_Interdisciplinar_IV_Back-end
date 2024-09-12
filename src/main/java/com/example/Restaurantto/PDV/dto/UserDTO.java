package com.example.Restaurantto.PDV.dto;

import com.example.Restaurantto.PDV.enums.Role;

import java.util.List;

public record UserDTO(Long id, String email, List<Role> roles) {
}
