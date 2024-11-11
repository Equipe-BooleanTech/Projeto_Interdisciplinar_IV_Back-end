package com.example.Restaurantto.PDV.model.dataSheet;

import com.example.Restaurantto.PDV.model.product.Ingredient;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Data
@Table(name = "datasheet")
public class DataSheet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String name;

    @ManyToMany
    @JoinTable(
            name = "datasheet_ingredient",
            joinColumns = @JoinColumn(name = "datasheet_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id")
    )
    private Set<Ingredient> ingredients;
    @CreatedDate
    @Column(updatable = false)
    private LocalDate createdAt;
}
