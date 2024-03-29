package com.moveo.epicure.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HomeInfo {
    private List<RestaurantBriefDTO> popularRestaurants;
    private ChefDTO weeklyChef;
    private List<MealBriefDTO> signatureMeals;
}
