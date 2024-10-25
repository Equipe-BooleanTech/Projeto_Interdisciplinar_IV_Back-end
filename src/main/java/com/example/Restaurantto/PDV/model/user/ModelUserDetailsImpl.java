package com.example.Restaurantto.PDV.model.user;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ModelUserDetailsImpl implements UserDetails {

    private ModelUser modelUser;

    public ModelUserDetailsImpl(ModelUser modelUser) {
        this.modelUser = modelUser;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Retorna uma única autoridade baseada no role do usuário
        return List.of(new SimpleGrantedAuthority(modelUser.getRole().getName().name()));
    }


    @Override
    public String getPassword() {
        return modelUser.getPassword();
    }

    @Override
    public String getUsername() {
        return modelUser.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
