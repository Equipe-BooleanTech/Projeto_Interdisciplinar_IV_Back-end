package com.example.Restaurantto.PDV.initializer;

import com.example.Restaurantto.PDV.enums.Role;
import com.example.Restaurantto.PDV.model.user.ModelRole;
import com.example.Restaurantto.PDV.repository.user.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class RoleInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;

    public RoleInitializer(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Arrays.stream(Role.values()).forEach(roleName -> {
            if (roleRepository.findByName(roleName).isEmpty()) {
                ModelRole role = ModelRole.builder().name(roleName).build();
                roleRepository.save(role);
                System.out.println(STR."Role \{roleName} created.");
            }
        });
    }
}
