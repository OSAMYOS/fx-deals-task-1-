package com.example.FXDealsTask.model;

import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="fx_deals", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"deal_unique_id","from_currency", "to_currency", "deal_timestamp", "deal_amount"})
})
public class FxDeal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "deal_unique_id")
    private int id;

    @NotNull(message = "From currency cannot be null")
    @Column(name = "from_currency", nullable = false, length = 3)
    private String fromCurrency;

    @NotNull(message = "To currency cannot be null")
    @Column(name = "to_currency", nullable = false, length = 3)
    private String toCurrency;

    @NotNull(message = "Timestamp cannot be null")
    @Column(name = "deal_timestamp", nullable = false)
    private Timestamp dealTimestamp;

    @NotNull(message = "Amount cannot be null")
    @Column(name = "deal_amount", nullable = false)
    private BigDecimal dealAmount;

    public FxDeal(String fromCurrency, String toCurrency, Timestamp timestamp, BigDecimal bigDecimal) {
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
        this.dealTimestamp = timestamp;
        this.dealAmount = bigDecimal;
    }
}