package com.example.Restaurantto.PDV.model.groupSheet;

import com.example.Restaurantto.PDV.model.dataSheet.DataSheet;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Data
@Table(name = "groupsheet")

public class GroupSheet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private UUID id;
    private String name;

    @ManyToMany
    @JoinTable(
            name = "groupsheet_datasheet",
            joinColumns = @JoinColumn(name = "groupsheet_id"),
            inverseJoinColumns = @JoinColumn(name = "datasheet_id")
    )

    private Set<DataSheet> datasheets;
}
