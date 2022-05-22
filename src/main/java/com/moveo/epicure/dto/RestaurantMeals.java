package com.moveo.epicure.dto;

import java.util.List;
import lombok.Data;

@Data
public class RestaurantMeals {
    private String category;
    private List<MealDTO> meals;
}
