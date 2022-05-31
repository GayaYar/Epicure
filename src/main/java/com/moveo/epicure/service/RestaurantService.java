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
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RestaurantService {
    private RestaurantRepo restaurantRepo;
    private RestaurantRepoImpl restaurantRepoImpl;
    private MealRepo mealRepo;

    public RestaurantService(RestaurantRepo restaurantRepo, RestaurantRepoImpl restaurantRepoImpl, MealRepo mealRepo) {
        this.restaurantRepo = restaurantRepo;
        this.restaurantRepoImpl = restaurantRepoImpl;
        this.mealRepo = mealRepo;
    }

    @Transactional(readOnly = true)
    public List<RestaurantBriefDTO> getPopulars(Integer amount) {
        List<Restaurant> populars;
        if(amount == null || amount == 3) {
            populars = restaurantRepo.findTop3ByOrderByPopularityDesc();
        } else {
            populars = restaurantRepoImpl.findOrderByPopularityLimitedTo(amount);
        }
        return populars.stream().map(DtoMapper::restaurantToBriefDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
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

    @Transactional(readOnly = true)
    public Optional<RestaurantDTO> findById(Integer id) {
        Optional<Restaurant> optionalRest = restaurantRepo.findRestaurantWithMeals(id);
        return optionalRest.isEmpty() ? Optional.empty() : Optional.of(DtoMapper.restaurantToDto(optionalRest.get()));
    }

    @Transactional(readOnly = true)
    public Optional<MealDTO> findMeal(Integer id) {
        Optional<Meal> optionalMeal = mealRepo.findMealWithChoices(id);
        return optionalMeal.isEmpty() ? Optional.empty() : Optional.of(DtoMapper.mealToDto(optionalMeal.get()));
    }
}
