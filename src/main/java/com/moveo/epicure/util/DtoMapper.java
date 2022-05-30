package com.moveo.epicure.util;

import com.moveo.epicure.dto.ChefBriefDTO;
import com.moveo.epicure.dto.ChefDTO;
import com.moveo.epicure.dto.RestaurantBriefDTO;
import com.moveo.epicure.entity.Chef;
import com.moveo.epicure.entity.Restaurant;
import java.util.stream.Collectors;

public class DtoMapper {
    public static ChefDTO chefToDto(Chef chefWithMeals) {
        return new ChefDTO(chefWithMeals.getName(), chefWithMeals.getDescription(), chefWithMeals.getRestaurants().stream()
                .map(restaurant -> restaurantToBrief(restaurant)).collect(Collectors.toList()), chefWithMeals.getImg());
    }

    public static RestaurantBriefDTO restaurantToBrief(Restaurant restaurant) {
        return null;
    }

    public static ChefBriefDTO chefToBrief(Chef chef) {
        return new ChefBriefDTO(chef.getId(), chef.getName(), chef.getImg());
    }
}
