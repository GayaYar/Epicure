package com.moveo.epicure.util;

import com.moveo.epicure.dto.CartDTO;
import com.moveo.epicure.dto.CartMealDTO;
import com.moveo.epicure.dto.OptionDTO;
import com.moveo.epicure.entity.Cart;
import com.moveo.epicure.entity.ChosenMeal;
import com.moveo.epicure.entity.Option;
import java.util.stream.Collectors;

public class DtoMapper {
    public static CartDTO cartToDto(Cart cart) {
        return new CartDTO(cart.getRestaurant().getName()
                , cart.getChosenMeals().stream().map(chosenMeal -> {return chosenMealToCartMeal(chosenMeal);}).collect(
                Collectors.toList()), cart.getComment(), cart.getOverallPrice());
    }

    public static CartMealDTO chosenMealToCartMeal(ChosenMeal chosen) {
        return new CartMealDTO(chosen.getId(), chosen.getImg(), chosen.getMealPrice(),
                chosen.getChosenOptions().stream().map(option -> {return optionToDto(option);}).collect(Collectors.toList())
                , chosen.getAmount(), chosen.getFinalPrice());
    }

    public static OptionDTO optionToDto(Option option) {
        //to do
        return null;
    }
}
