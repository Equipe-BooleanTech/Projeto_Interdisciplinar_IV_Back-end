package com.example.Restaurantto.PDV.dto;

import com.example.Restaurantto.PDV.enums.Role;

public record CreateUserDTO (String email, String password, Role role){
}
