package com.moveo.epicure.controller;

import com.moveo.epicure.dto.FullMealDTO;
import com.moveo.epicure.dto.FullRestaurantDTO;
import com.moveo.epicure.dto.RestaurantDTO;
import com.moveo.epicure.exception.NotFoundException;
import com.moveo.epicure.service.RestaurantService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("restaurants")
public class RestaurantController {
    @Autowired
    private RestaurantService service;

    @GetMapping(value = "/popular/{amount}")
    public ResponseEntity<List<RestaurantDTO>> getPopularRestaurants(@PathVariable int amount) {
        return ResponseEntity.ok(service.getPopulars());
    }

    @GetMapping()
    public ResponseEntity<List<RestaurantDTO>> getRestaurants(@RequestParam(required = false) Integer minPrice,
            @RequestParam(required = false) Integer maxPrice, @RequestParam(required = false) Boolean newest,
            @RequestParam(required = false) Boolean popular, @RequestParam(required = false) Integer distance,
            @RequestParam(required = false) Boolean open, @RequestParam(required = false) Integer rating) {
        return ResponseEntity.ok(service.getAllSorted(minPrice, maxPrice, newest, popular, distance, open, rating));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<FullRestaurantDTO> findById(@PathVariable Integer id) {
        Optional<FullRestaurantDTO> optionalRestaurant = service.findById(id);
        if(optionalRestaurant.isEmpty()) {
            throw new NotFoundException();
        }
        return ResponseEntity.ok(optionalRestaurant.get());
    }

    @GetMapping(value = "/meals/{id}")
    public ResponseEntity<FullMealDTO> getMeal(@PathVariable Integer id) {
        Optional<FullMealDTO> optionalMeal = service.findMeal(id);
        if(optionalMeal.isEmpty()) {
            throw new NotFoundException();
        }
        return ResponseEntity.ok(optionalMeal.get());
    }

}
