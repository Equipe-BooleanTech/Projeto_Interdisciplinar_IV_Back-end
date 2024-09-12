package com.example.Restaurantto.PDV.service;

import com.example.Restaurantto.PDV.dto.CreateUserDTO;
import com.example.Restaurantto.PDV.dto.JwtTokenDTO;
import com.example.Restaurantto.PDV.dto.LoginUserDTO;
import com.example.Restaurantto.PDV.model.ModelRole;
import com.example.Restaurantto.PDV.model.ModelUser;
import com.example.Restaurantto.PDV.model.ModelUserDetailsImpl;
import com.example.Restaurantto.PDV.repository.UserRepsitory;
import com.example.Restaurantto.PDV.security.SecurityConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepsitory userRepsitory;
    @Autowired
    private SecurityConfig securityConfig;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenService jwtTokenService;

    public void salvarUsuario(CreateUserDTO createUserDTO){
        ModelUser newUser = ModelUser.builder()
                .email(createUserDTO.email())
                .password(securityConfig.passwordEncoder().encode(createUserDTO.password()))
                .roles(List.of(ModelRole.builder().name(createUserDTO.role()).build()))
                .build();
        userRepsitory.save(newUser);
    }

    public JwtTokenDTO authenticarUsuario(LoginUserDTO loginUserDTO){
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                loginUserDTO.email(),
                loginUserDTO.password());

        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        ModelUserDetailsImpl modelUserDetails = (ModelUserDetailsImpl) authentication.getPrincipal();
        return new JwtTokenDTO(jwtTokenService.generateToken(modelUserDetails));

    }
}
