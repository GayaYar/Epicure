package com.moveo.epicure.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RestaurantDTO {
    private Integer id;
    private String name;
    private String chefName;
    private int rating;
    private String img;
}
