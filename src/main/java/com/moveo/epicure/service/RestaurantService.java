package com.moveo.epicure.service;

import com.moveo.epicure.dto.FullMealDTO;
import com.moveo.epicure.dto.FullRestaurantDTO;
import com.moveo.epicure.dto.RestaurantDTO;
import java.util.List;
import java.util.Optional;
import javax.swing.text.html.Option;
import org.springframework.stereotype.Service;

@Service
public class RestaurantService {

    public List<RestaurantDTO> getPopulars(Integer amount) {
        //to do
        amount = (amount==null) ? 3 : amount;
        return null;
    }

    public List<RestaurantDTO> getAllSorted(Integer minPrice, Integer maxPrice, Boolean newest, Boolean popular, Integer distance, Boolean open, Integer rating) {
        //to do
        return null;
    }

    public Optional<FullRestaurantDTO> findById(Integer id) {
        //to do
        return null;
    }

    public Optional<FullMealDTO> findMeal(Integer id) {
        //to do
        return null;
    }
}
