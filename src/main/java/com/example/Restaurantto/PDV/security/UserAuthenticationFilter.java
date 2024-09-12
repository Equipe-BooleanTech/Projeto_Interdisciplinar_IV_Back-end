package com.example.Restaurantto.PDV.security;

import com.example.Restaurantto.PDV.model.ModelUser;
import com.example.Restaurantto.PDV.model.ModelUserDetailsImpl;
import com.example.Restaurantto.PDV.repository.UserRepsitory;
import com.example.Restaurantto.PDV.service.JwtTokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.grammars.hql.HqlParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.method.annotation.AuthenticationPrincipalArgumentResolver;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Component
public class UserAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtTokenService jwtTokenService;
    @Autowired
    private UserRepsitory userRepsitory;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(verificaEndpointsPublicos(request)){
            String token = recuperaToken(request);
            if(token!=null){
                String subject = jwtTokenService.pegarToken(token);
                ModelUser modelUser = userRepsitory.findByEmail(subject).get();
                ModelUserDetailsImpl modelUserDetails = new ModelUserDetailsImpl(modelUser);
                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        modelUserDetails.getUsername(),
                        null,
                        modelUserDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }else{
                throw new RuntimeException("TOKEN INEXISTENTE");
            }
        }
        filterChain.doFilter(request,response);
    }

    private boolean verificaEndpointsPublicos(HttpServletRequest request){
        String requestURI = request.getRequestURI();
        return !Arrays.asList("/api/users/login","/api/users")
                .contains(requestURI);
    }

    private String recuperaToken(HttpServletRequest request){
        String authHeader = request.getHeader("Authorization");
        if(authHeader != null){
            return authHeader.replace("Bearer ","");
        }
        return null;
    }
}
