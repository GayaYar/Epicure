package com.moveo.epicure.controller;

import com.moveo.epicure.dto.MealDTO;
import com.moveo.epicure.dto.RestaurantDTO;
import com.moveo.epicure.dto.RestaurantBriefDTO;
import com.moveo.epicure.exception.NotFoundException;
import com.moveo.epicure.service.RestaurantService;
import java.util.List;
import java.util.Optional;
import javax.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping(value = "/pageable")
    public ResponseEntity<List<RestaurantBriefDTO>> getPageableRestaurants(@RequestParam(required = false) @Min(0) Integer minPrice,
            @RequestParam(required = false) @Min(0) Integer maxPrice, @RequestParam(required = false) Boolean newest
            , @RequestParam(required = false) Double longitude, @RequestParam(required = false) Double latitude
            , @RequestParam(required = false) @Min(0) Integer distance, @RequestParam(required = false) Boolean open
            , @RequestParam(required = false) Integer rating, @RequestParam @Min(0) int page, @RequestParam @Min(0) int size) {
        Pageable pageRequest = PageRequest.of(page, size);
        return ResponseEntity.ok(service.getAllSortedPageable(minPrice, maxPrice, newest, longitude, latitude, distance
                , open, rating, pageRequest));
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

}
