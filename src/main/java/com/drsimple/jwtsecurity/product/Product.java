package com.drsimple.jwtsecurity.product;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;


@Entity
@Table(name = "tbl_products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private String name;

    @Column(name = "category", nullable = true)
    private String category;

    @NotNull
    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private Long createdBy;


    public Product() {
    }

    public Product(String name, String category, BigDecimal price, Long createdBy) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.createdBy = createdBy;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
