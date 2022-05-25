package com.moveo.epicure.service;

import com.moveo.epicure.dto.MealDTO;
import com.moveo.epicure.dto.RestaurantDTO;
import com.moveo.epicure.dto.RestaurantBriefDTO;
import com.moveo.epicure.entity.Meal;
import com.moveo.epicure.entity.Restaurant;
import com.moveo.epicure.exception.LocationNotFoundException;
import com.moveo.epicure.repo.ChoiceRepo;
import com.moveo.epicure.util.DtoMapper;
import java.util.Comparator;
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
    @Autowired
    private ChoiceRepo choiceRepo;

    public List<RestaurantBriefDTO> getPopulars(Integer amount) {
        List<Restaurant> populars;
        if(amount == null || amount == 3) {
            populars = restaurantRepo.findTop3ByOrderByPopularityDesc();
        } else {
            populars = restaurantRepoImpl.findOrderByPopularityLimitedTo(amount);
        }
        return populars.stream().map(DtoMapper::restaurantToBriefDto).collect(Collectors.toList());
    }

    /**
     * get all restaurants withing the restrictions specified by the variables
     * sorts the list by newest (if newest == true) and by popularity
     * turn the restaurant list to a list of RestaurantBriefDTO
     * @param minPrice
     * @param maxPrice
     * @param newest
     * @param longitude
     * @param latitude
     * @param distance
     * @param open
     * @param rating
     * @return the modified list
     */
    public List<RestaurantBriefDTO> getAllSorted(Integer minPrice, Integer maxPrice, Boolean newest, Double longitude
            , Double latitude, Integer distance, Boolean open, Integer rating) {
        if(distance != null && (longitude==null || latitude==null)){
            throw new LocationNotFoundException();
        }

        return restaurantRepo.findByParams(minPrice==null?0:minPrice, maxPrice==null?Integer.MAX_VALUE:maxPrice
                        , longitude==null?0:longitude, latitude==null?0:latitude, distance==null?Integer.MAX_VALUE:distance
                        , open, rating == null ? 1 : rating)
                .stream().sorted(restaurantComparator(newest)).map(DtoMapper::restaurantToBriefDto).collect(Collectors.toList());

    }

    private Comparator<Restaurant> restaurantComparator(Boolean newest) {
        Comparator<Restaurant> restaurantComparator;
        Comparator<Restaurant> compareByPopularity = (Restaurant r1, Restaurant r2) -> {
            return -(r1.getPopularity()-r2.getPopularity());};
        if(newest!=null && newest==true) {
            Comparator<Restaurant> compareByNewest = (Restaurant r1, Restaurant r2) -> {
                return -r1.getCreation().compareTo(r2.getCreation());};
            restaurantComparator = compareByNewest.thenComparing(compareByPopularity);
        }else {
            restaurantComparator = compareByPopularity;
        }
        return restaurantComparator;
    }

    public Optional<RestaurantDTO> findById(Integer id) {
        Optional<Restaurant> optionalRest = restaurantRepo.findById(id);
        if(optionalRest.isEmpty()) {
            return Optional.empty();
        }
        Restaurant restaurant = optionalRest.get();
        return Optional.of(DtoMapper.restaurantToDto(restaurant, mealRepo.findByRestaurant(restaurant)));
    }

    public Optional<MealDTO> findMeal(Integer id) {
        Optional<Meal> optionalMeal = mealRepo.findById(id);
        if(optionalMeal.isEmpty()){
            return Optional.empty();
        }
        Meal meal = optionalMeal.get();
        return Optional.of(DtoMapper.mealToDto(meal, choiceRepo.findByMeal(meal)));
    }
}
