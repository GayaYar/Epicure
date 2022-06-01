package com.moveo.epicure.mock;

import com.moveo.epicure.dto.ChoiceDTO;
import com.moveo.epicure.dto.MealBriefDTO;
import com.moveo.epicure.dto.MealDTO;
import com.moveo.epicure.dto.OptionDTO;
import com.moveo.epicure.dto.RestaurantBriefDTO;
import com.moveo.epicure.dto.RestaurantDTO;
import com.moveo.epicure.dto.RestaurantMeals;
import com.moveo.epicure.entity.Chef;
import com.moveo.epicure.entity.Choice;
import com.moveo.epicure.entity.Label;
import com.moveo.epicure.entity.Meal;
import com.moveo.epicure.entity.Option;
import com.moveo.epicure.entity.Restaurant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MockRestaurant {

    public static List<Restaurant> all() {
        Chef mockChef = new Chef(2, "yossi", "desc", "img", new ArrayList<>(), 1
                , new Date());
        List<Choice> mealChoices = new ArrayList<>(1);
        List<Option> option = new ArrayList<>(1);
        option.add(new Option(1, "avocado"));
        mealChoices.add(new Choice(2, "choice", 1, 3, option));
        List<Meal> meals = new ArrayList<>(2);
        meals.add(new Meal(1, "meal1", "des", true, true, false, 15.5
                , "img", "food", mealChoices));
        meals.add(new Meal(2, "meal2", "des", true, true, false, 15.5
                , "img", "food", mealChoices));

        List<Restaurant> restaurants = new ArrayList<>(10);
        restaurants.add(new Restaurant(0, "name", mockChef, 1, "img", true, 10
                , 1.5, 80.3, 2, new Date(), new ArrayList<>()));
        restaurants.add(new Restaurant(1, "name", mockChef, 2, "img", false, 3
                , 1.5, 80.3, 2, new Date(), new ArrayList<>()));
        restaurants.add(new Restaurant(2, "name", mockChef, 2, "img", true, 4
                , 1.5, 80.3, 2, new Date(), new ArrayList<>()));
        restaurants.add(new Restaurant(3, "name", mockChef, 2, "img", false, 5
                , 1.5, 80.3, 4, new Date(), new ArrayList<>()));
        restaurants.add(new Restaurant(4, "name", mockChef, 5, "img", false, 6
                , 1.5, 80.3, 1, new Date(), new ArrayList<>()));
        restaurants.add(new Restaurant(5, "name", mockChef, 3, "img", true, 2
                , 1.5, 80.3, 3, new Date(), new ArrayList<>()));
        restaurants.add(new Restaurant(6, "name", mockChef, 4, "img", true, 25
                , 1.5, 80.3, 1, new Date(), new ArrayList<>()));
        restaurants.add(new Restaurant(7, "name", mockChef, 2, "img", false, 12
                , 20.5, 83.3, 2, new Date(), new ArrayList<>()));
        restaurants.add(new Restaurant(8, "name", mockChef, 2, "img", true, 17
                , 21.5, 83.3, 2, new Date(), new ArrayList<>()));
        restaurants.add(new Restaurant(9, "name", mockChef, 2, "img", true, 18
                , 21.5, 83.3, 2, new Date(), meals));
        return restaurants;
    }

    public static List<RestaurantBriefDTO> allBrief() {
        List<RestaurantBriefDTO> briefs = new ArrayList<>(10);
        briefs.add(new RestaurantBriefDTO(0, "name", "yossi", 1, "img"));
        briefs.add(new RestaurantBriefDTO(1, "name", "yossi", 2, "img"));
        briefs.add(new RestaurantBriefDTO(2, "name", "yossi", 2, "img"));
        briefs.add(new RestaurantBriefDTO(3, "name", "yossi", 2, "img"));
        briefs.add(new RestaurantBriefDTO(4, "name", "yossi", 5, "img"));
        briefs.add(new RestaurantBriefDTO(5, "name", "yossi", 3, "img"));
        briefs.add(new RestaurantBriefDTO(6, "name", "yossi", 4, "img"));
        briefs.add(new RestaurantBriefDTO(7, "name", "yossi", 2, "img"));
        briefs.add(new RestaurantBriefDTO(8, "name", "yossi", 2, "img"));
        briefs.add(new RestaurantBriefDTO(9, "name", "yossi", 2, "img"));
        return briefs;
    }

    public static RestaurantDTO id5AsDto() {
        return new RestaurantDTO(5, "name", "yossi", 3, "img", true, new ArrayList<>());
    }

    public static RestaurantDTO id9asDtoWithMeals() {
        List<RestaurantMeals> restaurantMeals = new ArrayList<>(1);
        List<MealBriefDTO> briefMeals = new ArrayList<>(2);
        briefMeals.add(new MealBriefDTO(1, "meal1", "des", 15.5, "img"));
        briefMeals.add(new MealBriefDTO(2, "meal2", "des", 15.5, "img"));
        restaurantMeals.add(new RestaurantMeals("breakfast", briefMeals));
        return new RestaurantDTO(9, "name", "yossi", 2, "img", true, restaurantMeals);
    }

    public static MealDTO meal1Dto() {
        List<ChoiceDTO> choiceDTOS = new ArrayList<>(1);
        List<OptionDTO> optionDTOS = new ArrayList<>(1);
        optionDTOS.add(new OptionDTO(1, "avocado"));
        choiceDTOS.add(new ChoiceDTO("choice", optionDTOS, 1,3));
        List<Label> labels = new ArrayList<>(2);
        labels.add(Label.SPICY);
        labels.add(Label.VEGAN);

        return new MealDTO(1, "meal1", "des", labels, 15.5, choiceDTOS, "img", 1);
    }

}
