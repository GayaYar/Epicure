package com.moveo.epicure.service;

import com.moveo.epicure.dto.MealDTO;
import com.moveo.epicure.dto.RestaurantDTO;
import com.moveo.epicure.dto.RestaurantBriefDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class RestaurantService {

    public List<RestaurantBriefDTO> getPopulars(Integer amount) {
        //to do
        amount = (amount==null) ? 3 : amount;
        return null;
    }

    public List<RestaurantBriefDTO> getAllSorted(Integer minPrice, Integer maxPrice, Boolean newest, Boolean popular, Integer distance, Boolean open, Integer rating) {
        //to do
        return null;
    }

    public Optional<RestaurantDTO> findById(Integer id) {
        //to do
        return null;
    }

    public Optional<MealDTO> findMeal(Integer id) {
        //to do
        return null;
    }
}
