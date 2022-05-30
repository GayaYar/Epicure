package com.moveo.epicure.dto;

import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class RestaurantMeals {
    @NotNull
    private String category;
    @NotNull
    @Size(min = 1)
    private List<MealBriefDTO> meals;
}
