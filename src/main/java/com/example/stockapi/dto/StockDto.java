package com.example.stockapi.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class StockDto {
    @NotBlank
    @NotNull
    private String name;

    @NotNull
    private Double currentPrice;

    public StockDto(String name, Double currentPrice) {
        this.name = name;
        this.currentPrice = currentPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(Double currentPrice) {
        this.currentPrice = currentPrice;
    }
}
