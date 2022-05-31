package com.moveo.epicure.service;

import static org.junit.jupiter.api.Assertions.*;

import com.moveo.epicure.dto.RestaurantBriefDTO;
import com.moveo.epicure.entity.Chef;
import com.moveo.epicure.entity.Restaurant;
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
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
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

    @BeforeAll
    public void setUp() {
        service = new RestaurantService(restaurantRepo, restaurantRepoImpl, mealRepo);
        Chef mockChef = new Chef(2, "yossi", "desc", "img", null, 1, new Date());
        mockRestaurant = new Restaurant(1, "name", mockChef, 1, "img", true, 10
                , 1.5, 80.3, 2, new Date(), null);
    }

    @Test
    @DisplayName("getPopulrars call find top 3 and returns list of RestaurantBriefDto")
    void getPopularsCallsFindTop3() {
        Mockito.when(restaurantRepo.findTop3ByOrderByPopularityDesc()).thenReturn(Arrays.asList(mockRestaurant
                , mockRestaurant, mockRestaurant));
        //checking returned list size is 3
        assertTrue(service.getPopulars(null).size()==3);
        //checking the repository is being called
        Mockito.verify(restaurantRepo, Mockito.times(1)).findTop3ByOrderByPopularityDesc();
        //checking returned list size is 3
        assertTrue(service.getPopulars(3).size()==3);
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
        assertTrue(service.getPopulars(5).size()==5);
        //checking the repository is being called
        Mockito.verify(restaurantRepoImpl, Mockito.times(1)).findOrderByPopularityLimitedTo(5);
        //checking the returned list is of the correct type
        assertEquals(service.getPopulars(5).get(0).getClass(), RestaurantBriefDTO.class);
    }

    @Test
    void getAllSorted() {
    }

    @Test
    void findById() {
    }

    @Test
    void findMeal() {
    }
}