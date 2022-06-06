package com.moveo.epicure.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import com.moveo.epicure.exception.LocationNotFoundException;
import com.moveo.epicure.mock.MockRestaurant;
import com.moveo.epicure.repo.MealRepo;
import com.moveo.epicure.repo.RestaurantRepo;
import com.moveo.epicure.repo.RestaurantRepoImpl;
import java.util.Optional;
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
    private MockRestaurant mockRestaurant;

    /**
     * initialisation of required fields for the test
     */
    @BeforeAll
    void setUpTest() {
        service = new RestaurantService(restaurantRepo, restaurantRepoImpl, mealRepo);
        mockRestaurant = new MockRestaurant();
    }

    @Test
    void getPopularsCallsFindTop3() {
        Mockito.when(restaurantRepo.findTop3ByOrderByPopularityDesc()).thenReturn(mockRestaurant.getRestaurantIndexes(6,9,8));
        assertEquals(service.getPopulars(null), mockRestaurant.getBriefIndexes(6,9,8));
    }

    @Test
    void getPopularsCallsFindTopLimit() {
        Mockito.when(restaurantRepoImpl.findOrderByPopularityLimitedTo(5)).thenReturn(mockRestaurant.getRestaurantIndexes(6,9,8,7,0));
        assertEquals(service.getPopulars(5), mockRestaurant.getBriefIndexes(6,9,8,7,0));
    }

    /**
     * restaurants filtered by min and max price
     */
    @Test
    void getAllSortedPrice() {
        Mockito.when(restaurantRepo.findByParams(3, 4, 0, 0, Integer.MAX_VALUE
                , false, 1)).thenReturn(mockRestaurant.getRestaurantIndexes(3,5));
        assertEquals(service.getAllSorted(3,4, null, null, null, null
                , null, null), mockRestaurant.getBriefIndexes(3,5));
    }

    /**
     * get restaurants by distance when a valid location is given
     */
    @Test
    void getAllSortedDistanceValid() {
        Mockito.when(restaurantRepo.findByParams(0, Integer.MAX_VALUE, 15.0, 15.0, 4
                , false, 1)).thenReturn(mockRestaurant.getRestaurantIndexes(7,8,9));

        assertEquals(service.getAllSorted(null, null, null, 15.0, 15.0
                , 4, null, null), mockRestaurant.getBriefIndexes(9,8,7));
    }

    /**
     * assuring that if the "distance" parameter is not null (restaurants should be filtered by distance)
     * and no valid location is given with it- a LocationNotFoundException is thrown
     */
    @Test
    void getAllSortedDistanceInvalid() {
        assertThatThrownBy(()->{service.getAllSorted(null,null, null, 3.5, null
                , 4, null, null);}).isInstanceOf(LocationNotFoundException.class);
    }

    /**
     * getting only the open restaurants
     */
    @Test
    void getAllSortedOpen() {
        Mockito.when(restaurantRepo.findByParams(0, Integer.MAX_VALUE, 0, 0, Integer.MAX_VALUE
                , true, 1)).thenReturn(mockRestaurant.getRestaurantIndexes(0,2,5,6,8,9));

        assertEquals(service.getAllSorted(null, null, null, null, null
                , null, true, null), mockRestaurant.getBriefIndexes(6,9,8,0,2,5));
    }

    /**
     * assures returning of all restaurants when all inserted parameters are null
     */
    @Test
    void getAllSortedAll() {
        Mockito.when(restaurantRepo.findByParams(0, Integer.MAX_VALUE, 0, 0, Integer.MAX_VALUE
                , false, 1)).thenReturn(mockRestaurant.getAllRestaurants());

        assertEquals(service.getAllSorted(null, null, null, null, null
                , null, null, null), mockRestaurant.getBriefIndexes(6,9,8,7,0,4,3,2,1,5));
    }

    /**
     * assures the method knows to convert from restaurant to dto when the restaurant has no meals
     */
    @Test
    void findByIdIsFoundWithoutMeals() {
        Mockito.when(restaurantRepo.findRestaurantWithMeals(5)).thenReturn(Optional.of(mockRestaurant.getRestaurant(5)));
        assertTrue(service.findById(5).get().equals(mockRestaurant.restaurant5AsDto()));
    }

    /**
     * assures the method returns the correct restaurant dto (converts from restaurant) when the restaurant has meals
     */
    @Test
    void findByIdIsFoundWithMeals() {
        Mockito.when(restaurantRepo.findRestaurantWithMeals(9)).thenReturn(Optional.of(mockRestaurant.getRestaurant(9)));
        assertTrue(service.findById(9).get().equals(mockRestaurant.restaurant9asDtoWithMeals()));
    }

    /**
     * assures that if the meal is not found, an empty optional is returned
     */
    @Test
    void findByIdNotFound() {
        Mockito.when(restaurantRepo.findRestaurantWithMeals(-2)).thenReturn(Optional.empty());
        assertTrue(service.findById(-2).isEmpty());
    }

    /**
     * assures the method transforms the meal to dto and returns it
     */
    @Test
    void findMeal() {
        Mockito.when(mealRepo.findMealWithChoices(1)).thenReturn(Optional.of(mockRestaurant.getRestaurant(9).getMeals().get(0)));
        assertEquals(service.findMeal(1).get(), mockRestaurant.meal1asDto());
    }
}