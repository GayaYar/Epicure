package com.moveo.epicure.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import com.moveo.epicure.dto.RestaurantBriefDTO;
import com.moveo.epicure.entity.Chef;
import com.moveo.epicure.entity.Choice;
import com.moveo.epicure.entity.Meal;
import com.moveo.epicure.entity.Option;
import com.moveo.epicure.entity.Restaurant;
import com.moveo.epicure.exception.LocationNotFoundException;
import com.moveo.epicure.exception.NullException;
import com.moveo.epicure.mock.MockRestaurant;
import com.moveo.epicure.repo.MealRepo;
import com.moveo.epicure.repo.RestaurantRepo;
import com.moveo.epicure.repo.RestaurantRepoImpl;
import com.moveo.epicure.util.DtoMapper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

public class RestaurantServiceTest {

    private RestaurantService service;
    @Mock
    private RestaurantRepo restaurantRepo;
    @Mock
    private RestaurantRepoImpl restaurantRepoImpl;
    @Mock
    private MealRepo mealRepo;
    private List<Restaurant> restaurants;
    private List<RestaurantBriefDTO> briefDTOS;

    @BeforeAll
    public void setUp() {
        service = new RestaurantService(restaurantRepo, restaurantRepoImpl, mealRepo);
        restaurants = MockRestaurant.all();
        briefDTOS = MockRestaurant.allBrief();
    }

    @Test
    @DisplayName("getPopulrars call find top 3 and returns list of RestaurantBriefDto")
    void getPopularsCallsFindTop3() {
        Mockito.when(restaurantRepo.findTop3ByOrderByPopularityDesc()).thenReturn(getRestaurantIndexes(6,9,8));
        assertEquals(service.getPopulars(3), getBriefIndexes(6,9,8));
        assertEquals(service.getPopulars(null), getBriefIndexes(6,9,8));
        assertEquals(service.getPopulars(-2), getBriefIndexes(6,9,8));
    }

    @Test
    @DisplayName("getPopulrars call find top limit and returns list of RestaurantBriefDto")
    void getPopularsCallsFindTopLimit() {
        Mockito.when(restaurantRepoImpl.findOrderByPopularityLimitedTo(5)).thenReturn(getRestaurantIndexes(6,9,8,7,0));
        assertEquals(service.getPopulars(5), getBriefIndexes(6,9,8,7,0));
    }

    private List<Restaurant> getRestaurantIndexes(int... indexes) {
        ArrayList<Restaurant> l = new ArrayList<>(indexes.length);
        for (int i:indexes
        ) {
            l.add(restaurants.get(i));
        }
        return l;
    }

    private List<RestaurantBriefDTO> getBriefIndexes(int... indexes) {
        List<RestaurantBriefDTO> chosen = new ArrayList<>(indexes.length);
        for (int i: indexes) {
            chosen.add(briefDTOS.get(i));
        }
        return chosen;
    }

    @Test
    void getAllSortedPrice() {
        Mockito.when(restaurantRepo.findByParams(3, 4, 0, 0, Integer.MAX_VALUE
                , false, 1)).thenReturn(getRestaurantIndexes(3,5));
        assertEquals(service.getAllSorted(3,4, null, null, null, null
                , null, null), getBriefIndexes(3,5));
    }

    @Test
    void getAllSortedDistanceValid() {
        Mockito.when(restaurantRepo.findByParams(0, Integer.MAX_VALUE, 15.0, 15.0, 4
                , false, 1)).thenReturn(getRestaurantIndexes(7,8,9));

        assertEquals(service.getAllSorted(null, null, null, 15.0, 15.0
                , 4, null, null), getBriefIndexes(9,8,7));
    }

    @Test
    void getAllSortedDistanceInvalid() {
        assertThatThrownBy(()->{service.getAllSorted(null,null, null, 3.5, null
                , 4, null, null);}).isInstanceOf(LocationNotFoundException.class);
    }

    @Test
    void getAllSortedOpen() {
        Mockito.when(restaurantRepo.findByParams(0, Integer.MAX_VALUE, 0, 0, Integer.MAX_VALUE
                , true, 1)).thenReturn(getRestaurantIndexes(0,2,5,6,8,9));

        assertEquals(service.getAllSorted(null, null, null, null, null
                , null, true, null), getBriefIndexes(6,9,8,0,2,5));
    }

    @Test
    @DisplayName("getAllSorted receiving null as parameters")
    void getAllSortedAll() {
        Mockito.when(restaurantRepo.findByParams(0, Integer.MAX_VALUE, 0, 0, Integer.MAX_VALUE
                , false, 1)).thenReturn(restaurants);

        assertEquals(service.getAllSorted(null, null, null, null, null
                , null, null, null), getBriefIndexes(6,9,8,7,0,4,3,2,1,5));
    }

    @Test
    void findByIdIsFoundWithoutMeals() {
        Mockito.when(restaurantRepo.findRestaurantWithMeals(5)).thenReturn(Optional.of(restaurants.get(5)));
        assertTrue(service.findById(5).get().equals(MockRestaurant.id5AsDto()));
    }

    @Test
    void findByIdIsFoundWithMeals() {
        Mockito.when(restaurantRepo.findRestaurantWithMeals(9)).thenReturn(Optional.of(restaurants.get(9)));
        assertTrue(service.findById(9).get().equals(MockRestaurant.id9asDtoWithMeals()));
    }

    @Test
    void findByIdNotFound() {
        Mockito.when(restaurantRepo.findRestaurantWithMeals(-2)).thenReturn(Optional.empty());
        assertTrue(service.findById(-2).isEmpty());
    }

    @Test
    void findMeal() {
        Mockito.when(mealRepo.findMealWithChoices(1)).thenReturn(Optional.of(restaurants.get(9).getMeals().get(0)));
        assertEquals(service.findMeal(1).get(), MockRestaurant.meal1Dto());
    }
}