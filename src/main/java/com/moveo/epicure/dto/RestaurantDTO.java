package com.moveo.epicure.dto;

import java.util.List;
import lombok.Data;

@Data
public class RestaurantDTO {
    private Integer id;
    private String name;
    private String chefName;
    private int rating;
    private String img;
    private boolean open;
    private List<RestaurantMeals> meals;
}
