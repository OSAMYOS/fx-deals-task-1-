package com.example.FXDealsTask.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Table(name="fx_deals", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"deal_unique_id","from_currency", "to_currency", "deal_timestamp", "deal_amount"})
})
public class FxDeals {

    @Id
    @Min(value = 1, message = "Id can't be negative")
    @Column(name = "deal_unique_id")
    private String dealUniqueId;

    @NotNull(message = "Date cannot be null")
    @Column(name = "from_currency", nullable = false, length = 3)
    private String fromCurrency;

    @NotNull(message = "Date cannot be null")
    @Column(name = "to_currency", nullable = false, length = 3)
    private String toCurrency;

    @NotNull(message = "Date cannot be null")
    @Column(name = "deal_timestamp", nullable = false)
    private Timestamp dealTimestamp;

    @NotNull(message = "Date cannot be null")
    @Column(name = "deal_amount", nullable = false)
    private BigDecimal dealAmount;


    public FxDeals(String dealUniqueId, String fromCurrency, String toCurrency, Timestamp dealTimestamp, BigDecimal dealAmount) {
        this.dealUniqueId = dealUniqueId;
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
        this.dealTimestamp = dealTimestamp;
        this.dealAmount = dealAmount;
    }
    public FxDeals() {}

    @Override
    public String toString() {
        return "FxDeals{" +
                "dealUniqueId='" + dealUniqueId + '\'' +
                ", fromCurrency='" + fromCurrency + '\'' +
                ", toCurrency='" + toCurrency + '\'' +
                ", dealTimestamp=" + dealTimestamp +
                ", dealAmount=" + dealAmount +
                '}';
    }
}
