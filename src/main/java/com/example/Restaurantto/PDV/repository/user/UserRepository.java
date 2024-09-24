package com.example.Restaurantto.PDV.repository.user;

import com.example.Restaurantto.PDV.model.user.ModelUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<ModelUser, UUID> {


    Optional<ModelUser> findByEmail(String email);
}
