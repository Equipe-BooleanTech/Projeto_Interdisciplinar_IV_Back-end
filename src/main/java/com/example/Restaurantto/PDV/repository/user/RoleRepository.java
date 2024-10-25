package com.example.Restaurantto.PDV.repository.user;

import com.example.Restaurantto.PDV.model.user.ModelRole;
import com.example.Restaurantto.PDV.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<ModelRole, UUID> {

    // Método para buscar uma role pelo nome
    Optional<ModelRole> findByName(Role name);

}
