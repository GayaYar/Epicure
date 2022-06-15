package com.moveo.epicure.controller;

import com.moveo.epicure.annotation.PermissionNeeded;
import com.moveo.epicure.dto.AdminRestaurantDto;
import com.moveo.epicure.dto.MealDTO;
import com.moveo.epicure.dto.RestaurantDTO;
import com.moveo.epicure.dto.RestaurantBriefDTO;
import com.moveo.epicure.exception.NotFoundException;
import com.moveo.epicure.service.RestaurantService;
import java.util.List;
import java.util.Optional;
import javax.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("restaurants")
@Validated
public class RestaurantController {
    private RestaurantService service;

    public RestaurantController(RestaurantService service) {
        this.service = service;
    }

    @GetMapping(value = "/popular")
    public ResponseEntity<List<RestaurantBriefDTO>> getPopularRestaurants(@RequestParam(required = false) @Min(0) Integer amount) {
        return ResponseEntity.ok(service.getPopulars(amount));
    }

    @GetMapping()
    public ResponseEntity<List<RestaurantBriefDTO>> getRestaurants(@RequestParam(required = false) @Min(0) Integer minPrice,
            @RequestParam(required = false) @Min(0) Integer maxPrice, @RequestParam(required = false) Boolean newest
            , @RequestParam(required = false) Double longitude, @RequestParam(required = false) Double latitude
            , @RequestParam(required = false) @Min(0) Integer distance, @RequestParam(required = false) Boolean open
            , @RequestParam(required = false) Integer rating) {
        return ResponseEntity.ok(service.getAllSorted(minPrice, maxPrice, newest, longitude, latitude, distance
                , open, rating));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<RestaurantDTO> findById(@PathVariable @Min(0) Integer id) {
        Optional<RestaurantDTO> optionalRestaurant = service.findById(id);
        if(optionalRestaurant.isEmpty()) {
            throw new NotFoundException("restaurant");
        }
        return ResponseEntity.ok(optionalRestaurant.get());
    }

    @GetMapping(value = "/meals/{id}")
    public ResponseEntity<MealDTO> getMeal(@PathVariable @Min(0) Integer id) {
        Optional<MealDTO> optionalMeal = service.findMeal(id);
        if(optionalMeal.isEmpty()) {
            throw new NotFoundException("meal");
        }
        return ResponseEntity.ok(optionalMeal.get());
    }

    @PermissionNeeded
    @PutMapping
    public ResponseEntity<AdminRestaurantDto> addRestaurant(@RequestBody AdminRestaurantDto restaurant) {
        return ResponseEntity.ok(service.addRestaurant(restaurant));
    }

}
