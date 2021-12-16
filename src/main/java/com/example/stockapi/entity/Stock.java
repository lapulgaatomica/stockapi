package com.example.stockapi.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
public class Stock {
    @Id
    @GeneratedValue()
    private Long id;

    private String name;

    @Column(name = "current_price")
    private Double currentPrice;

    @CreationTimestamp
    @Column(name = "create_date")
    private Timestamp createDate;

    @UpdateTimestamp
    @Column(name = "last_update")
    private Timestamp lastUpdate;

    public Stock() {
    }

    public Stock(String name, Double currentPrice) {
        this.name = name;
        this.currentPrice = currentPrice;
    }

    public Stock(Long id, String name, Double currentPrice) {
        this.id = id;
        this.name = name;
        this.currentPrice = currentPrice;
    }

    public Stock(Long id, String name, Double currentPrice, Timestamp createDate, Timestamp lastUpdate) {
        this.id = id;
        this.name = name;
        this.currentPrice = currentPrice;
        this.createDate = createDate;
        this.lastUpdate = lastUpdate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
