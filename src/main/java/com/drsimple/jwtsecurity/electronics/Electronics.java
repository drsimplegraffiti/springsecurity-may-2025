package com.drsimple.jwtsecurity.electronics;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "electronics")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Electronics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Double price;
    private String brand;

    @Version
    private Integer version; // For optimistic locking
}