package com.example.Restaurantto.PDV.repository.user;

import com.example.Restaurantto.PDV.model.user.ModelUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<ModelUser, Long> {


    Optional<ModelUser> findByEmail(String email);
}
