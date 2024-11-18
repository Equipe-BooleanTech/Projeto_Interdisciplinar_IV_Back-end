package com.example.Restaurantto.PDV.model.financial;

import com.example.Restaurantto.PDV.enums.PaymentMethod;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "receitas")
public class Revenue {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String name;

    private BigDecimal amount;

    private LocalDate saleDate;

    private String description;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    private String category; // Qual tipo de pagamento, interessante para implementação do ifood posteriormente pois pode ser via restaurante, via ifood, etc

    private Boolean paymentStatus; // true = pago, false = pendente

    private String employee; // colocar o nome do garçom ou caixa para resgistar numero de vendas por pessoa, pensando em uma feature futura

    private String orderNumber; // para poder integrar com sistema de pedidos posteriormente
}