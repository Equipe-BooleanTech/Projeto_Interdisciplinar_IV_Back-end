package com.example.Restaurantto.PDV.initializer;

import com.example.Restaurantto.PDV.enums.Role;
import com.example.Restaurantto.PDV.model.user.ModelRole;
import com.example.Restaurantto.PDV.model.user.ModelUser;
import com.example.Restaurantto.PDV.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
    public class AdminInitializer implements CommandLineRunner {

        @Value("${admin.default.email}")
        private String adminEmail;

        @Value("${admin.default.password}")
        private String adminPassword;

        private final UserRepository userRepository;
        private final PasswordEncoder passwordEncoder;

        public AdminInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
            this.userRepository = userRepository;
            this.passwordEncoder = passwordEncoder;
        }

        @Override
        public void run(String... args) throws Exception {
            if (userRepository.findByEmail(adminEmail).isEmpty()) {
                ModelUser admin = ModelUser.builder()
                        .email(adminEmail)
                        .password(passwordEncoder.encode(adminPassword))
                        .role(ModelRole.builder().name(Role.ROLE_ADMIN).build())
                        .build();

                userRepository.save(admin);
                System.out.println("Usuário admin criado: " + adminEmail);
            } else {
                System.out.println("Usuário admin já existe.");
            }
        }
    }


