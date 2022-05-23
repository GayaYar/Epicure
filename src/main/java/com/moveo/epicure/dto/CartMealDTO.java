package com.moveo.epicure.dto;

import java.util.List;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CartMealDTO {
    @NotNull
    private Integer id;
    @NotNull
    private String img;
    @NotNull
    @Min(0)
    private double mealPrice;
    private List<ChoiceDTO> choices;
    @NotNull
    @Min(1)
    private int amount;
    @NotNull
    @Min(0)
    private double finalPrice;
}
