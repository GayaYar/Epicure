package com.moveo.epicure.util;

import static java.util.stream.Collectors.groupingBy;

import com.moveo.epicure.swagger.dto.CartDTO;
import com.moveo.epicure.swagger.dto.CartMealDTO;
import com.moveo.epicure.swagger.dto.ChefBriefDTO;
import com.moveo.epicure.swagger.dto.ChefDTO;
import com.moveo.epicure.swagger.dto.ChoiceDTO;
import com.moveo.epicure.swagger.dto.MealBriefDTO;
import com.moveo.epicure.swagger.dto.MealDTO;
import com.moveo.epicure.swagger.dto.OptionDTO;
import com.moveo.epicure.swagger.dto.RestaurantBriefDTO;
import com.moveo.epicure.swagger.dto.RestaurantDTO;
import com.moveo.epicure.swagger.dto.RestaurantMeals;
import com.moveo.epicure.entity.Cart;
import com.moveo.epicure.entity.Chef;
import com.moveo.epicure.entity.Choice;
import com.moveo.epicure.entity.ChosenMeal;
import com.moveo.epicure.entity.Meal;
import com.moveo.epicure.entity.Option;
import com.moveo.epicure.entity.Restaurant;
import com.moveo.epicure.entity.Label;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DtoMapper {
    public static CartDTO cartToDto(Cart cart) {
        return new CartDTO(cart.getChosenMeals().stream().map(chosenMeal -> {return chosenMealToCartMeal(chosenMeal);}).collect(
                Collectors.toList()), cart.getComment(), cart.getOverallPrice());
    }

    public static CartMealDTO chosenMealToCartMeal(ChosenMeal chosenMeal) {
        return new CartMealDTO(chosenMeal.getId(), chosenMeal.getImg(), chosenMeal.getMealPrice()
                , DtoMapper.optionsToDtos(chosenMeal.getChosenOptions()), chosenMeal.getAmount(),
                chosenMeal.getFinalPrice());
    }

    public static OptionDTO optionToDto(Option option) {
        return new OptionDTO(option.getId(), option.getOption());
    }

    public static ChoiceDTO choiceToDto(Choice choice) {
        return new ChoiceDTO(choice.getTitle(), choice.getOptions().stream().map(option -> {return optionToDto(option);})
                .collect(Collectors.toList()), choice.getMinChoices(), choice.getMaxChoices());
    }

    public static MealBriefDTO mealToBriefDto(Meal meal) {
        return new MealBriefDTO(meal.getId(), meal.getName(), meal.getDescription(), meal.getPrice(), meal.getImg());
    }

    public static MealDTO mealToDto(Meal mealWithChoices) {
        return new MealDTO(mealWithChoices.getId(), mealWithChoices.getName(), mealWithChoices.getDescription()
                , turnLabelsToList(mealWithChoices.isSpicy(), mealWithChoices.isVegan(), mealWithChoices.isGlutenFree())
                , mealWithChoices.getPrice(), mealWithChoices.getChoices().stream()
                .map(choice -> {return choiceToDto(choice);}).collect(Collectors.toList()), mealWithChoices.getImg(), 1);
    }

    public static RestaurantBriefDTO restaurantToBriefDto(Restaurant restaurant) {
        return new RestaurantBriefDTO(restaurant.getId(), restaurant.getName(), restaurant.getChef().getName(),
                restaurant.getRating(), restaurant.getImg());
    }

    public static RestaurantDTO restaurantToDto(Restaurant restaurantWithMeals) {
        return new RestaurantDTO(restaurantWithMeals.getId(), restaurantWithMeals.getName()
                , restaurantWithMeals.getChef().getName(), restaurantWithMeals.getRating(), restaurantWithMeals.getImg()
                , restaurantWithMeals.isOpen(), mealsToRestaurantMeals(restaurantWithMeals.getMeals()));
    }

    /**
     * turns the least to a map of key=category, value=list of meals in that category
     * then, turns the map to a list of restaurant meals
     * @param meals - all the meals of restaurant
     * @return a list of restaurant meals (dto)
     */
    public static List<RestaurantMeals> mealsToRestaurantMeals(List<Meal> meals) {
        List<RestaurantMeals> restMeals = new ArrayList<>();
        Map<String, List<Meal>> restMealMap = meals.stream().collect(groupingBy(Meal::getCategory));
        for (Map.Entry<String, List<Meal>> entry : restMealMap.entrySet()) {
            restMeals.add(new RestaurantMeals(entry.getKey(),
                    entry.getValue().stream().map(meal -> {return mealToBriefDto(meal);}).collect(Collectors.toList())));
        }
        return restMeals;
    }

    private static List<Label> turnLabelsToList(boolean spicy, boolean vegan, boolean glutenFree) {
        List<Label> labels = new ArrayList<>(3);
        if (spicy) {
            labels.add(Label.SPICY);
        }
        if (vegan) {
            labels.add(Label.VEGAN);
        }
        if (glutenFree) {
            labels.add(Label.GLUTEN_FREE);
        }
        return labels;
    }

    public static List<Option> choiceDtosToOptions(List<ChoiceDTO> choiceDTOS) {
        return choiceDTOS.stream().flatMap(choiceDTO -> choiceDTO.getOptions().stream())
                .map(optionDTO -> new Option(optionDTO.getId(), optionDTO.getOption()))
                .collect(Collectors.toList());
    }

    public static List<OptionDTO> optionsToDtos(List<Option> options) {
        return options.stream().map(option -> new OptionDTO(option.getId(), option.getOption()))
                .collect(Collectors.toList());
    }



    public static ChefDTO chefToDto(Chef chefWithMeals) {
        return new ChefDTO(chefWithMeals.getName(), chefWithMeals.getDescription(), chefWithMeals.getRestaurants().stream()
                .map(restaurant -> restaurantToBriefDto(restaurant)).collect(Collectors.toList()), chefWithMeals.getImg());
    }

    public static ChefBriefDTO chefToBrief(Chef chef) {
        return new ChefBriefDTO(chef.getId(), chef.getName(), chef.getImg());
    }
}
