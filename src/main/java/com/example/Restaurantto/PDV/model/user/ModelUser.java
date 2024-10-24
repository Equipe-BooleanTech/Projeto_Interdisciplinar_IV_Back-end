package com.example.Restaurantto.PDV.model.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
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
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<ModelRole> roles;
    private String fullName;
    private String phone;
    @Column(unique = true, nullable = true)
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

public Boolean isReadyForActivation() {

    if (!isEmployee) {

        boolean isReady = isValidString(cep) && isValidString(address) && addressNumber > 0 &&
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

