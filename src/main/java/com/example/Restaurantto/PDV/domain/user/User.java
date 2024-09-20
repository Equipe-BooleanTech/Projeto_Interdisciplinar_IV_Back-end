package com.example.Restaurantto.PDV.domain.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class User {

    private String name;
    private String lastName;
    private String phone;
    private String cpf;
    private String cep;
    private String address;
    private String city;
    private String state;
    private String neighborhood;
    private String email;
}
