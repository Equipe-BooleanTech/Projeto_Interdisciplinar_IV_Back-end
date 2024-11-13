package com.example.Restaurantto.PDV.model.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "modeluser")
public class ModelUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(unique = true)
    private String email;
    private String password;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private ModelRole role;
    private String fullName;
    private String phone;
    private String cpf;
    private String cep;
    private String address;
    private int addressNumber;
    private String city;
    private String state;
    private String neighborhood;
    private String enterprise;
    private String cnpj;
    private String message;
    private boolean isProspecting;
    private boolean isEmployee;
    private String function;
    @CreatedDate
    @Column(updatable = false)
    private LocalDate createdAt;

public Boolean isReadyForActivation() {

    if (!isEmployee) {

        boolean isReady = isValidString(cep) && isValidString(address) && addressNumber == 0 &&
                isValidString(city) && isValidString(state) && isValidString(neighborhood) &&
                isValidString(password) &&
                (isValidString(cpf) || isValidString(cnpj)); 

        if (isReady) {
            System.out.println("Usuário pronto para ativação");
            return true;
        } else {
            System.out.println("Usuário não está pronto para ativação");
            return false;
        }
    } else {
        System.out.println("Ativação não permitida: o usuário é um funcionário.");
        return false;
    }
}

private boolean isValidString(String value) {
    return value != null && !value.trim().isEmpty();
}


}

