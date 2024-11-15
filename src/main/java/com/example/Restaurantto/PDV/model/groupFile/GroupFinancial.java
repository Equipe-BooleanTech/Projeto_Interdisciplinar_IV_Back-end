package com.example.Restaurantto.PDV.model.groupFile;

import com.example.Restaurantto.PDV.model.financial.Expense;
import com.example.Restaurantto.PDV.model.financial.Revenue;
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
@Data
@Entity
@Builder
@Table(name = "groupfinancial")
public class GroupFinancial {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String name;

    @ManyToMany
    @JoinTable(name = "group_financial_revenue",
            joinColumns = @JoinColumn(name = "groupfinancial_id"),
            inverseJoinColumns = @JoinColumn(name = "revenue_id"))
    private Set<Revenue> revenues;

    @ManyToMany
    @JoinTable(name = "group_financial_expense",
            joinColumns = @JoinColumn(name = "groupfinancial_id"),
            inverseJoinColumns = @JoinColumn(name = "expense_id"))
    private Set<Expense> expenses;

    @CreatedDate
    @Column(updatable = false)
    private LocalDate createdAt;



}
