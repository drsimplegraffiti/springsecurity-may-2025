package com.drsimple.jwtsecurity.featuredflag;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseDtoRequest {
    private String name;
    private Double price;
}