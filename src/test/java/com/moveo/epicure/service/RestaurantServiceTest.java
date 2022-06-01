package com.moveo.epicure.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import com.moveo.epicure.dto.RestaurantBriefDTO;
import com.moveo.epicure.entity.Chef;
import com.moveo.epicure.entity.Restaurant;
import com.moveo.epicure.exception.LocationNotFoundException;
import com.moveo.epicure.repo.MealRepo;
import com.moveo.epicure.repo.RestaurantRepo;
import com.moveo.epicure.repo.RestaurantRepoImpl;
import com.moveo.epicure.util.DtoMapper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
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
    private Restaurant mockRestaurant;
    private Chef mockChef;
    private List<Restaurant> restaurants;

    @BeforeAll
    public void setUp() {
        service = new RestaurantService(restaurantRepo, restaurantRepoImpl, mealRepo);
        mockChef = new Chef(2, "yossi", "desc", "img", null, 1, new Date());
        restaurants = new ArrayList<>(8);
        restaurants.add(new Restaurant(0, "name", mockChef, 1, "img", true, 10
                , 1.5, 80.3, 2, new Date(), null));
        restaurants.add(new Restaurant(1, "name", mockChef, 2, "img", false, 3
                , 1.5, 80.3, 2, new Date(), null));
        restaurants.add(new Restaurant(2, "name", mockChef, 2, "img", true, 3
                , 1.5, 80.3, 2, new Date(), null));
        restaurants.add(new Restaurant(3, "name", mockChef, 2, "img", false, 5
                , 1.5, 80.3, 4, new Date(), null));
        restaurants.add(new Restaurant(4, "name", mockChef, 5, "img", false, 3
                , 1.5, 80.3, 1, new Date(), null));
        restaurants.add(new Restaurant(5, "name", mockChef, 3, "img", true, 3
                , 1.5, 80.3, 3, new Date(), null));
        restaurants.add(new Restaurant(6, "name", mockChef, 4, "img", true, 25
                , 1.5, 80.3, 1, new Date(), null));
        restaurants.add(new Restaurant(7, "name", mockChef, 2, "img", false, 3
                , 20.5, 83.3, 2, new Date(), null));
        restaurants.add(new Restaurant(8, "name", mockChef, 2, "img", true, 1
                , 21.5, 83.3, 2, new Date(), null));
        mockRestaurant = restaurants.get(0);
    }

    @Test
    @DisplayName("getPopulrars call find top 3 and returns list of RestaurantBriefDto")
    void getPopularsCallsFindTop3() {
        Mockito.when(restaurantRepo.findTop3ByOrderByPopularityDesc()).thenReturn(Arrays.asList(mockRestaurant
                , mockRestaurant, mockRestaurant));
        //checking returned list size is 3
        assertTrue(service.getPopulars(null).size() == 3);
        //checking the repository is being called
        Mockito.verify(restaurantRepo, Mockito.times(1)).findTop3ByOrderByPopularityDesc();
        //checking returned list size is 3
        assertTrue(service.getPopulars(3).size() == 3);
        //checking the repository is being called
        Mockito.verify(restaurantRepo, Mockito.times(1)).findTop3ByOrderByPopularityDesc();
        //checking the returned list is of the correct type
        assertEquals(service.getPopulars(null).get(0).getClass(), RestaurantBriefDTO.class);
        //checking the returned list is of the correct type
        assertEquals(service.getPopulars(3).get(0).getClass(), RestaurantBriefDTO.class);
    }

    @Test
    @DisplayName("getPopulrars call find top limit and returns list of RestaurantBriefDto")
    void getPopularsCallsFindTopLimit() {
        Mockito.when(restaurantRepoImpl.findOrderByPopularityLimitedTo(5)).thenReturn(Arrays.asList(mockRestaurant
                , mockRestaurant, mockRestaurant, mockRestaurant, mockRestaurant));
        //checking returned list size is 5
        assertTrue(service.getPopulars(5).size() == 5);
        //checking the repository is being called
        Mockito.verify(restaurantRepoImpl, Mockito.times(1)).findOrderByPopularityLimitedTo(5);
        //checking the returned list is of the correct type
        assertEquals(service.getPopulars(5).get(0).getClass(), RestaurantBriefDTO.class);
    }

    @Test
    @DisplayName("getAllSorted when the only values are min and max price")
    void getByMinAndMaxPrice() {
        Mockito.when(restaurantRepo.findByParams(3, 4, 0, 0, Integer.MAX_VALUE
                , false, 1)).thenReturn(Arrays.asList(restaurants.get(3), restaurants.get(5)));
        assertEquals(service.getAllSorted(3,4, null, null, null, null
                , null, null), Arrays.asList(DtoMapper.restaurantToBriefDto(restaurants.get(3))
                , DtoMapper.restaurantToBriefDto(restaurants.get(5))));
    }

    @Test
    @DisplayName("getAllSorted when the only values are distance (valid and invalid)")
    void getByDistance() {
        Mockito.when(restaurantRepo.findByParams(0, Integer.MAX_VALUE, 3.5, 6.7, 4
                , false, 1)).thenReturn(Arrays.asList(restaurants.get(7), restaurants.get(8)));
        assertEquals(service.getAllSorted(null,null, null, 3.5, 6.7, 4
                , null, null), Arrays.asList(DtoMapper.restaurantToBriefDto(restaurants.get(7))
                , DtoMapper.restaurantToBriefDto(restaurants.get(8))));
        assertThatThrownBy(()->{service.getAllSorted(null,null, null, 3.5, null, 4
                , null, null);}).isInstanceOf(LocationNotFoundException.class);
    }

    @Test
    void findById() {
    }

    @Test
    void findMeal() {
    }
}