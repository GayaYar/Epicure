package com.moveo.epicure.dto;

import java.util.List;
import lombok.Data;

@Data
public class CartMealDTO {
    private Integer id;
    private String img;
    private double mealPrice;
    private List<ChoiceDTO> choices;
    private int amount;
    private double finalPrice;
}
