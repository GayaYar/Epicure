package com.moveo.epicure.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MealDTO {
    private Integer id;
    private String name;
    private String description;
    private double price;
    private String img;
}
