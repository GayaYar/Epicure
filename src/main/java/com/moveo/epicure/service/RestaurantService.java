package com.moveo.epicure.service;

import com.moveo.epicure.dto.MealDTO;
import com.moveo.epicure.dto.RestaurantDTO;
import com.moveo.epicure.dto.RestaurantBriefDTO;
import com.moveo.epicure.entity.Restaurant;
import com.moveo.epicure.util.DtoMapper;
import java.util.List;
import java.util.Optional;
import com.moveo.epicure.repo.MealRepo;
import com.moveo.epicure.repo.RestaurantRepo;
import com.moveo.epicure.repo.RestaurantRepoImpl;
import java.util.stream.Collectors;
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

    public List<RestaurantBriefDTO> getPopulars(Integer amount) {
        List<Restaurant> populars;
        if(amount == null || amount == 3) {
            populars = restaurantRepo.findTop3ByOrderByPopularityDesc();
        } else {
            populars = restaurantRepoImpl.findOrderByPopularityLimitedTo(amount);
        }
        return populars.stream()
                .map(restaurant -> {return DtoMapper.restaurantToBriefDto(restaurant);}).collect(Collectors.toList());
    }

    public List<RestaurantBriefDTO> getAllSorted(Integer minPrice, Integer maxPrice, Boolean newest, Boolean popular
            , Integer distance, Boolean open, Integer rating) {
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
