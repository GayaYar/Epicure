package com.moveo.epicure.dto;

import java.util.List;
import lombok.Data;

@Data
public class CartDTO {
    private String restaurantName;
    private List<CartMealDTO> meals;
    private String comment;
    private double overallPrice;
}
