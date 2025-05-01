package com.drsimple.jwtsecurity.product;


import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;


@Data
public class ProductRequest {
    @NotNull
    private String name;

    private String category;


    @NotNull
    private BigDecimal price;

}
