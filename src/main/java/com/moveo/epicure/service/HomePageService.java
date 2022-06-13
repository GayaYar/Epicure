package com.moveo.epicure.service;

import com.moveo.epicure.dto.ChefDTO;
import com.moveo.epicure.dto.HomeInfo;
import com.moveo.epicure.exception.NotFoundException;
import com.moveo.epicure.repo.MealRepo;
import com.moveo.epicure.util.DtoMapper;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class HomePageService {

    private RestaurantService restaurantService;
    private ChefService chefService;
    private MealRepo mealRepo;

    public HomePageService(RestaurantService restaurantService, ChefService chefService, MealRepo mealRepo) {
        this.restaurantService = restaurantService;
        this.chefService = chefService;
        this.mealRepo = mealRepo;
    }

    @Transactional(readOnly = true)
    public HomeInfo getHomeInfo(int weeklyChefId) {
        Optional<ChefDTO> optionalChefDTO = chefService.findChefById(weeklyChefId);
        if (optionalChefDTO.isPresent()) {
            return new HomeInfo(restaurantService.getPopulars(3),
                    optionalChefDTO.get(),
                    mealRepo.findBySignatureTrue().stream().map(DtoMapper::mealToBriefDto).toList());
        }
        throw new NotFoundException("chef");
    }
}
