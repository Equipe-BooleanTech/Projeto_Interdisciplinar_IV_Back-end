package com.example.Restaurantto.PDV.initializer;

import com.example.Restaurantto.PDV.enums.Role;
import com.example.Restaurantto.PDV.model.user.ModelRole;
import com.example.Restaurantto.PDV.model.user.ModelUser;
import com.example.Restaurantto.PDV.repository.user.RoleRepository;
import com.example.Restaurantto.PDV.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Order(2)
public class AdminInitializer implements CommandLineRunner {

    @Value("${admin.default.email}")
    private String adminEmail;

    @Value("${admin.default.password}")
    private String adminPassword;

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminInitializer(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        // Ensure the roles are created first
        if (roleRepository.findByName(Role.ROLE_ADMIN).isEmpty()) {
            System.out.println("Role ROLE_ADMIN is not initialized. Please check RoleInitializer.");
            return;
        }

        if (userRepository.findByEmail(adminEmail).isEmpty()) {
            ModelRole adminRole = roleRepository.findByName(Role.ROLE_ADMIN).orElseThrow();
            ModelUser admin = ModelUser.builder()
                    .email(adminEmail)
                    .password(passwordEncoder.encode(adminPassword))
                    .role(adminRole)
                    .build();

            userRepository.save(admin);
            System.out.println("Usuário admin criado: " + adminEmail);
        } else {
            System.out.println("Usuário admin já existe.");
        }
    }
}
