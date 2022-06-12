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
    private List<Restaurant> allRestaurants;
    private List<RestaurantBriefDTO> allBriefs;
    private String imgUrl;

    public MockRestaurant() {
        allRestaurants = allRestaurantsInitialise();
        allBriefs = allBriefInitialise();
        imgUrl = "https://camo.githubusercontent.com/56ea24702a43a27f55794275849e38c16cd393e244a59297a71266b9b34e3e53/68747470733a2f2f617368616c6c656e64657369676e2e636f2e756b2f696d616765732f637573746f6d2f73686f72742d75726c2d6c6f676f2e706e67";
    }

    private List<Restaurant> allRestaurantsInitialise() {
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

    public List<Restaurant> noMealsRestaurants(Chef mockChef) {
        List<Restaurant> restaurants = new ArrayList<>(10);
        restaurants.add(new Restaurant(null, "name0", mockChef, 1, imgUrl, true, 10
                , 1.5, 80.3, 2, new Date(), new ArrayList<>()));
        restaurants.add(new Restaurant(null, "name1", mockChef, 2, imgUrl, false, 3
                , 1.5, 80.3, 2, new Date(), new ArrayList<>()));
        restaurants.add(new Restaurant(null, "name2", mockChef, 2, imgUrl, true, 4
                , 1.5, 80.3, 2, new Date(), new ArrayList<>()));
        restaurants.add(new Restaurant(null, "name3", mockChef, 2, imgUrl, false, 5
                , 1.5, 80.3, 4, new Date(), new ArrayList<>()));
        restaurants.add(new Restaurant(null, "name4", mockChef, 5, imgUrl, false, 6
                , 1.5, 80.3, 1, new Date(), new ArrayList<>()));
        restaurants.add(new Restaurant(null, "name5", mockChef, 3, imgUrl, true, 2
                , 1.5, 80.3, 3, new Date(), new ArrayList<>()));
        restaurants.add(new Restaurant(null, "name6", mockChef, 4, imgUrl, true, 25
                , 1.5, 80.3, 1, new Date(), new ArrayList<>()));
        restaurants.add(new Restaurant(null, "name7", mockChef, 2, imgUrl, false, 12
                , 20.5, 83.3, 2, new Date(), new ArrayList<>()));
        restaurants.add(new Restaurant(null, "name8", mockChef, 2, imgUrl, true, 17
                , 21.5, 83.3, 2, new Date(), new ArrayList<>()));
        restaurants.add(new Restaurant(null, "name9", mockChef, 2, imgUrl, true, 18
                , 21.5, 83.3, 2, new Date(), new ArrayList<>()));
        return restaurants;
    }

    private List<RestaurantBriefDTO> allBriefInitialise() {
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

    public RestaurantDTO restaurant5AsDto() {
        return new RestaurantDTO(5, "name", "yossi", 3, "img", true, new ArrayList<>());
    }

    public RestaurantDTO restaurant9asDtoWithMeals() {
        List<RestaurantMeals> restaurantMeals = new ArrayList<>(1);
        List<MealBriefDTO> briefMeals = new ArrayList<>(2);
        briefMeals.add(new MealBriefDTO(1, "meal1", "des", 15.5, "img"));
        briefMeals.add(new MealBriefDTO(2, "meal2", "des", 15.5, "img"));
        restaurantMeals.add(new RestaurantMeals("breakfast", briefMeals));
        return new RestaurantDTO(9, "name", "yossi", 2, "img", true, restaurantMeals);
    }

    public MealDTO meal1asDto() {
        List<ChoiceDTO> choiceDTOS = new ArrayList<>(1);
        List<OptionDTO> optionDTOS = new ArrayList<>(1);
        optionDTOS.add(new OptionDTO(1, "avocado"));
        choiceDTOS.add(new ChoiceDTO("choice", optionDTOS, 1,3));
        List<Label> labels = new ArrayList<>(2);
        labels.add(Label.SPICY);
        labels.add(Label.VEGAN);

        return new MealDTO(1, "meal1", "des", labels, 15.5, choiceDTOS, "img", 1);
    }

    public List<Restaurant> getAllRestaurants() {
        return allRestaurants;
    }

    public List<RestaurantBriefDTO> getAllBriefs() {
        return allBriefs;
    }

    public List<Restaurant> getRestaurantIndexes(int... indexes) {
        ArrayList<Restaurant> l = new ArrayList<>(indexes.length);
        for (int i:indexes
        ) {
            l.add(allRestaurants.get(i));
        }
        return l;
    }

    public List<RestaurantBriefDTO> getBriefIndexes(int... indexes) {
        List<RestaurantBriefDTO> chosen = new ArrayList<>(indexes.length);
        for (int i: indexes) {
            chosen.add(allBriefs.get(i));
        }
        return chosen;
    }

    public Restaurant getRestaurant(int index) {
        return allRestaurants.get(index);
    }
}
