package com.moveo.epicure.controller;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import com.moveo.epicure.entity.Restaurant;
import com.moveo.epicure.exception.NotFoundException;
import com.moveo.epicure.mock.MockRestaurant;
import com.moveo.epicure.service.RestaurantService;
import com.moveo.epicure.swagger.dto.MealDTO;
import com.moveo.epicure.swagger.dto.RestaurantBriefDTO;
import com.moveo.epicure.swagger.dto.RestaurantDTO;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

public class RestaurantControllerTest {
    private RestaurantController controller;
    @Mock
    private RestaurantService service;
    private MockRestaurant mockRestaurant;

    /**
     * initialisation of required fields for the test
     */
    @BeforeAll
    void setUpTest() {
        controller = new RestaurantController(service);
        mockRestaurant = new MockRestaurant();
    }

    @Test
    void getPopularRestaurants() {
        List<RestaurantBriefDTO> mockBriefs = mockRestaurant.getBriefIndexes(6, 9, 8);
        Mockito.when(service.getPopulars(3)).thenReturn(mockBriefs);
        assertEquals(controller.getPopularRestaurants(3), ResponseEntity.ok(mockBriefs));
    }

    @Test
    void getRestaurants() {
        List<RestaurantBriefDTO> mockBriefs = mockRestaurant.getBriefIndexes(9, 8, 7);
        Mockito.when(service.getAllSorted(null, null, null, 15.0, 15.0
                , 4, null, null)).thenReturn(mockBriefs);
        assertEquals(controller.getRestaurants(null, null, null, 15.0, 15.0
                , 4, null, null), ResponseEntity.ok(mockBriefs));
    }

    /**
     * assures the method returns a RestaurantDto if such is found by id
     */
    @Test
    void findByIdFound() {
        Optional<RestaurantDTO> mockOptional = Optional.of(mockRestaurant.restaurant9asDtoWithMeals());
        Mockito.when(service.findById(9)).thenReturn(mockOptional);
        assertEquals(controller.findById(9), mockOptional.get());
    }

    /**
     * assures that if no such restaurant is found, a NotFoundException with a proper message is thrown
     */
    @Test
    void findByIdNotFound() {
        Mockito.when(service.findById(50)).thenReturn(Optional.empty());
        assertThatThrownBy(()->{controller.findById(50);}).isInstanceOf(NotFoundException.class)
                .hasMessage("Could not find the restaurant you were looking for.");
    }

    /**
     * assures the method returns a MealDto if such is found by id
     */
    @Test
    void getMealFound() {
        Optional<MealDTO> mockOptional = Optional.of(mockRestaurant.meal1asDto());
        Mockito.when(service.findMeal(1)).thenReturn(mockOptional);
        assertEquals(controller.findById(1), mockOptional.get());
    }

    /**
     * assures that if no such meal is found, a NotFoundException with a proper message is thrown
     */
    @Test
    void getMealNotFound() {
        Mockito.when(service.findMeal(50)).thenReturn(Optional.empty());
        assertThatThrownBy(()->{controller.getMeal(50);}).isInstanceOf(NotFoundException.class)
                .hasMessage("Could not find the meal you were looking for.");
    }
}