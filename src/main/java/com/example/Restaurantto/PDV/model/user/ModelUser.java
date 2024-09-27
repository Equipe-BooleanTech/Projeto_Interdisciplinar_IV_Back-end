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
    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.PERSIST)
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

    public Boolean isReadyForActivation(){
        return cep != null && address != null && addressNumber != 0 &&
                city != null && state != null && neighborhood != null &&
                cpf != null && cnpj != null && password != null;
    }



}
