package com.moveo.epicure.service;

import com.moveo.epicure.repo.RestaurantRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SearchService {
    @Autowired
    private RestaurantRepo restaurantRepo;


}
