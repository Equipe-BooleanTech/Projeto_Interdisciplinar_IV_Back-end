package com.example.Restaurantto.PDV.service;

import com.example.Restaurantto.PDV.dto.CreateUserDTO;
import com.example.Restaurantto.PDV.dto.JwtTokenDTO;
import com.example.Restaurantto.PDV.dto.LoginUserDTO;
import com.example.Restaurantto.PDV.model.ModelRole;
import com.example.Restaurantto.PDV.model.ModelUser;
import com.example.Restaurantto.PDV.model.ModelUserDetailsImpl;
import com.example.Restaurantto.PDV.repository.UserRepository;
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
    private UserRepository userRepository;
    @Autowired
    private SecurityConfig securityConfig;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenService jwtTokenService;

    public void salvarUsuario(CreateUserDTO createUserDTO){
        if(userRepository.findByEmail(createUserDTO.email()).isPresent()){
            throw new RuntimeException("E-MAIL JÁ CADASTRADO");
        }
        ModelUser newUser = ModelUser.builder()
                .email(createUserDTO.email())
                .password(securityConfig.passwordEncoder().encode(createUserDTO.password()))
                .roles(List.of(ModelRole.builder().name(createUserDTO.role()).build()))
                .build();
        userRepository.save(newUser);
    }

    public JwtTokenDTO authenticarUsuario(LoginUserDTO loginUserDTO){
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                loginUserDTO.email(),
                loginUserDTO.password());

        try {
            Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
            ModelUserDetailsImpl modelUserDetails = (ModelUserDetailsImpl) authentication.getPrincipal();
            String token = jwtTokenService.generateToken(modelUserDetails);
            return new JwtTokenDTO(token);

        } catch (Exception e) {
            throw new RuntimeException("FALHA NA AUTENTICAÇÃO: " + e.getMessage());
        }
    }
}
