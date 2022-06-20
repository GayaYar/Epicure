package com.moveo.epicure.controller;

import com.moveo.epicure.annotation.PermissionNeeded;
import com.moveo.epicure.dto.AdminRestaurantDto;
import com.moveo.epicure.dto.MealDTO;
import com.moveo.epicure.dto.RestaurantDTO;
import com.moveo.epicure.dto.RestaurantBriefDTO;
import com.moveo.epicure.exception.NotFoundException;
import com.moveo.epicure.service.RestaurantService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
@Slf4j
@Validated
public class RestaurantController {
    private RestaurantService service;
    private String fileName;
    public RestaurantController(RestaurantService service) {
        this.service = service;
        fileName = "RestaurantController:";
    }

    @GetMapping(value = "/popular")
    public ResponseEntity<List<RestaurantBriefDTO>> getPopularRestaurants(@RequestParam(required = false) @Min(0) Integer amount) {
        log.debug(fileName+"getPopularRestaurants] is called with amount="+amount);
        return ResponseEntity.ok(service.getPopulars(amount));
    }

    @GetMapping()
    public ResponseEntity<List<RestaurantBriefDTO>> getRestaurants(@RequestParam(required = false) @Min(0) Integer minPrice,
            @RequestParam(required = false) @Min(0) Integer maxPrice, @RequestParam(required = false) Boolean newest
            , @RequestParam(required = false) Double longitude, @RequestParam(required = false) Double latitude
            , @RequestParam(required = false) @Min(0) Integer distance, @RequestParam(required = false) Boolean open
            , @RequestParam(required = false) Integer rating) {
        log.debug(fileName+"getRestaurants] is called with minPrice="+minPrice+", maxPrice="+maxPrice+", newest="+newest
        +", longitude="+longitude+", latitude="+latitude+", distance="+distance+", open="+open+", rating="+rating);
        return ResponseEntity.ok(service.getAllSorted(minPrice, maxPrice, newest, longitude, latitude, distance
                , open, rating));
    }

    @GetMapping(value = "/pageable")
    public ResponseEntity<List<RestaurantBriefDTO>> getPageableRestaurants(@RequestParam(required = false) @Min(0) Integer minPrice,
            @RequestParam(required = false) @Min(0) Integer maxPrice, @RequestParam(required = false) Boolean newest
            , @RequestParam(required = false) Double longitude, @RequestParam(required = false) Double latitude
            , @RequestParam(required = false) @Min(0) Integer distance, @RequestParam(required = false) Boolean open
            , @RequestParam(required = false) Integer rating, @RequestParam(required = false) @Min(0) Integer page
            , @RequestParam(required = false) @Min(0) @Max(100) Integer size) {
        Pageable pageRequest = PageRequest.of(page==null?0:page, size==null?20:size);
        return ResponseEntity.ok(service.getAllSortedPageable(minPrice, maxPrice, newest, longitude, latitude, distance
                , open, rating, pageRequest));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<RestaurantDTO> findById(@PathVariable @Min(0) Integer id) {
        log.debug(fileName+"findById] is called with id="+id);
        Optional<RestaurantDTO> optionalRestaurant = service.findById(id);
        if(optionalRestaurant.isEmpty()) {
            log.error(fileName+"findById] restaurant with id: "+id+" was not found");
            throw new NotFoundException("restaurant");
        }
        return ResponseEntity.ok(optionalRestaurant.get());
    }

    @GetMapping(value = "/meals/{id}")
    public ResponseEntity<MealDTO> getMeal(@PathVariable @Min(0) Integer id) {
        log.debug(fileName+"getMeal] is called with id="+id);
        Optional<MealDTO> optionalMeal = service.findMeal(id);
        if(optionalMeal.isEmpty()) {
            log.error(fileName+"getMeal] meal with id: "+id+" was not found");
            throw new NotFoundException("meal");
        }
        return ResponseEntity.ok(optionalMeal.get());
    }

    @PermissionNeeded
    @PutMapping(value = "/add")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "Authorization", value = "Authorization token",
//                    required = true, dataType = "string", paramType = "header") })
    public ResponseEntity<AdminRestaurantDto> addRestaurant(@Valid @RequestBody AdminRestaurantDto restaurant) {
        return ResponseEntity.ok(service.addRestaurant(restaurant));
    }

}
