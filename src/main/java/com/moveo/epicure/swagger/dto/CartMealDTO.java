package com.moveo.epicure.swagger.dto;

import java.util.List;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CartMealDTO {
    @NotNull
    private Integer id;
    @NotNull
    private String img;
    @NotNull
    @Min(0)
    private double mealPrice;
    private List<OptionDTO> chosenOptions;
    @NotNull
    @Min(1)
    private int amount;
    @NotNull
    @Min(0)
    private double finalPrice;
}
