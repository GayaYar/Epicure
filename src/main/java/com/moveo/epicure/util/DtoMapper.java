package com.moveo.epicure.util;

import com.moveo.epicure.dto.CartDTO;
import com.moveo.epicure.dto.CartMealDTO;
import com.moveo.epicure.dto.ChoiceDTO;
import com.moveo.epicure.dto.OptionDTO;
import com.moveo.epicure.entity.Cart;
import com.moveo.epicure.entity.ChosenMeal;
import com.moveo.epicure.entity.Option;
import java.util.List;
import java.util.stream.Collectors;

public class DtoMapper {
    public static CartDTO cartToDto(Cart cart) {
        //replace with mapper from dto branch
        return null;
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

    public static CartMealDTO chosenMealToCartMeal(ChosenMeal chosenMeal) {
        return new CartMealDTO(chosenMeal.getId(), chosenMeal.getImg(), chosenMeal.getMealPrice()
                , DtoMapper.optionsToDtos(chosenMeal.getChosenOptions()), chosenMeal.getAmount(),
                chosenMeal.getFinalPrice());
    }
}
