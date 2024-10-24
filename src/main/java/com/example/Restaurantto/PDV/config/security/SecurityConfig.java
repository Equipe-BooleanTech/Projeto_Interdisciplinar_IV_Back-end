package com.example.Restaurantto.PDV.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {
    @Autowired
    private UserAuthenticationFilter userAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())  // Desabilita CSRF para APIs REST
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(
                                "/api/users/login",
                                "/api/users/prospects",
                                "/api/users/activate/**",
                                "/swagger-ui",
                                "/swagger-ui/**",
                                "/v3/api-docs/**"
                        ).permitAll()
                        .requestMatchers(
                                "/api/users/update/**",
                                "/api/users/delete/**",
                                "/api/users/update-password").authenticated()
                        .requestMatchers(
                                "/api/users/get-users",
                                "/api/users/create-complete",
                                "/api/products/create-supplier",
                                "/api/products/create-ingredient",
                                "/api/products/update-supplier/{id}",
                                "/api/products/update-ingredient/{id}",
                                "/api/products/delete-supplier/{id}",
                                "/api/products/delete-ingredient/{id}",
                                "/api/products/get-supplier",
                                "/api/products/get-ingredients"
                        ).hasAnyRole(
                                "GERENTE", "ADMIN"
                        )
                        .requestMatchers(
                                "/api/datasheets/create-datasheet",
                                "/api/datasheets/update-datasheet/{id}",
                                "/api/datasheets/delete-datasheet/{id}",
                                "/api/datasheets/get-datasheets"
                        ).hasAnyRole(
                                "CHEF", "ADMIN"
                        )
                        .requestMatchers(
                                "/api/user/roles/**"
                        ).hasRole("ADMIN")
                        .anyRequest().denyAll()
                )
                .addFilterBefore(userAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
