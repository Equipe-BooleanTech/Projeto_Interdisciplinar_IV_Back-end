package com.example.Restaurantto.PDV.model.dataSheet;

import com.example.Restaurantto.PDV.enums.UnitType;
import com.example.Restaurantto.PDV.model.product.Ingredient;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;
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

    @Enumerated(EnumType.STRING)
    private UnitType unit; // Unidade do produto (g, ml, unidade)

    private Double cost; // Custo total da ficha técnica

    private Double salePrice; // Preço de venda (30% acima do custo)

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
