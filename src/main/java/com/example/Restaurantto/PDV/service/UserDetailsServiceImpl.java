package com.example.Restaurantto.PDV.service;

import com.example.Restaurantto.PDV.model.ModelUser;
import com.example.Restaurantto.PDV.model.ModelUserDetailsImpl;
import com.example.Restaurantto.PDV.repository.UserRepsitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepsitory userRepsitory;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ModelUser modelUser = userRepsitory.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        return new ModelUserDetailsImpl(modelUser);
    }
}
