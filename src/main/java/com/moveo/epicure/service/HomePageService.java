package com.moveo.epicure.service;

import com.moveo.epicure.dto.ChefDTO;
import com.moveo.epicure.dto.HomeInfo;
import com.moveo.epicure.exception.ActionIncompleteException;
import com.moveo.epicure.exception.NotFoundException;
import com.moveo.epicure.repo.MealRepo;
import com.moveo.epicure.util.DtoMapper;
import java.util.ArrayList;
import java.util.List;
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
        HomeInfo homeInfo = new HomeInfo();
        List<Thread> setterThreads = createSetterThreads(weeklyChefId, homeInfo);
        for (Thread t : setterThreads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                throw new ActionIncompleteException("loading home page");
            }
            t.start();
        }
        return homeInfo;
    }

    private List<Thread> createSetterThreads(int weeklyChefId, HomeInfo homeInfo) {
        List<Thread> actionThreads = new ArrayList<>(3);
        actionThreads.add(new Thread(()->homeInfo.setPopularRestaurants(restaurantService.getPopulars(3))));
        actionThreads.add(new Thread(()->{
            Optional<ChefDTO> optionalChefDTO = chefService.findChefById(weeklyChefId);
            if (optionalChefDTO.isPresent()) {
                homeInfo.setWeeklyChef(optionalChefDTO.get());
            }else {
                throw new NotFoundException("chef");
            }
        }));
        actionThreads.add(new Thread(()->homeInfo.setSignatureMeals(
                mealRepo.findBySignatureTrue().stream().map(DtoMapper::mealToBriefDto).toList())
        ));
        return actionThreads;
    }
}
