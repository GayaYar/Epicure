package com.moveo.epicure.service;

import com.moveo.epicure.dto.RestaurantBriefDTO;
import com.moveo.epicure.repo.RestaurantRepo;
import com.moveo.epicure.util.DtoMapper;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SearchService {
    @Autowired
    private RestaurantRepo restaurantRepo;

    @Transactional(readOnly = true)
    public List<RestaurantBriefDTO> searchFoRestaurants(String input) {
        return restaurantRepo.searchRestaurants(input).stream().map(DtoMapper::restaurantToBriefDto)
                .collect(Collectors.toList());
    }
}
