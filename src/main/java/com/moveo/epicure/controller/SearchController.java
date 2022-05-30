package com.moveo.epicure.controller;

import com.moveo.epicure.dto.RestaurantBriefDTO;
import com.moveo.epicure.service.SearchService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/search")
public class SearchController {
    @Autowired
    private SearchService service;

    @GetMapping("/restaurant")
    public ResponseEntity<List<RestaurantBriefDTO>> searchForRestaurants(@RequestParam String input) {
        return ResponseEntity.ok(service.searchFoRestaurants(input));
    }
}
