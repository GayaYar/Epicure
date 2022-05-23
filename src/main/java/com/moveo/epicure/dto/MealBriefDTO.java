package com.moveo.epicure.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MealBriefDTO {
    private Integer id;
    private String name;
    private String description;
    private double price;
    private String img;
}
