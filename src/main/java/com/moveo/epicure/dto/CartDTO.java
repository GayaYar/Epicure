package com.moveo.epicure.dto;

import java.util.List;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CartDTO {
    @NotNull
    private String restaurantName;
    @NotNull
    private List<CartMealDTO> meals;
    @NotNull
    private String comment;
    @NotNull
    @Min(0)
    private double overallPrice;
}
