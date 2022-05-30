package com.moveo.epicure.dto;

import java.util.List;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class CartDTO {
    @NotNull
    private List<CartMealDTO> meals;
    @NotNull
    private String comment;
    @NotNull
    @Min(0)
    private double overallPrice;
}
