package com.moveo.epicure.service;

import com.moveo.epicure.repo.MealRepo;
import com.moveo.epicure.repo.RestaurantRepo;
import com.moveo.epicure.repo.RestaurantRepoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RestaurantService {
    @Autowired
    private RestaurantRepo restaurantRepo;
    @Autowired
    private RestaurantRepoImpl restaurantRepoImpl;
    @Autowired
    private MealRepo mealRepo;


}
