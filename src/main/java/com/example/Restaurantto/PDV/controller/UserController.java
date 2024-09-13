package com.example.Restaurantto.PDV.controller;

import com.example.Restaurantto.PDV.dto.CreateUserDTO;
import com.example.Restaurantto.PDV.dto.JwtTokenDTO;
import com.example.Restaurantto.PDV.dto.LoginUserDTO;
import com.example.Restaurantto.PDV.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<JwtTokenDTO> loginUsuario(@RequestBody LoginUserDTO loginUserDTO) {
        JwtTokenDTO token = userService.authenticarUsuario(loginUserDTO);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }
    @PostMapping("/create")
    public ResponseEntity<Void> salvarUsuario(@RequestBody CreateUserDTO createUserDTO){
        userService.salvarUsuario(createUserDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
