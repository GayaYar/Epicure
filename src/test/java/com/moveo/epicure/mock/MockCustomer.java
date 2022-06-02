package com.moveo.epicure.mock;

import com.moveo.epicure.dto.CartDTO;
import com.moveo.epicure.dto.CartMealDTO;
import com.moveo.epicure.dto.OptionDTO;
import com.moveo.epicure.entity.Cart;
import com.moveo.epicure.entity.Choice;
import com.moveo.epicure.entity.ChosenMeal;
import com.moveo.epicure.entity.Customer;
import com.moveo.epicure.entity.Meal;
import com.moveo.epicure.entity.Option;
import java.util.ArrayList;
import java.util.List;

public class MockCustomer {
    private Cart currentCart;
    private CartDTO cartDTO;

    public MockCustomer() {
        this.currentCart = mockCart();
        this.cartDTO = mockCartDto();
    }

    public CartDTO mockCartDto() {
        List<OptionDTO> optionDTOS = new ArrayList<>(1);
        optionDTOS.add(new OptionDTO(1, "avocado"));
        List<CartMealDTO> cartMeals = new ArrayList<>(2);
        cartMeals.add(new CartMealDTO(7, "img", 2.5, optionDTOS, 2, 5));
        cartMeals.add(new CartMealDTO(8, "img", 2.5, optionDTOS, 2, 5));

        return new CartDTO("cusName", cartMeals, "yes", 58.9);
    }

    public Cart mockCart() {
        Cart cart = new Cart(5, true, "yes", 58.9, new Customer(3, "cusName"), null);

        List<Choice> mealChoices = new ArrayList<>(1);
        List<Option> option = new ArrayList<>(1);
        option.add(new Option(1, "avocado"));
        mealChoices.add(new Choice(2, "choice", 1, 3, option));
        Meal meal = new Meal(1, "meal1", "des", true, true, false, 15.5
                , "img", "food", mealChoices);
        List<ChosenMeal> chosenMeals = new ArrayList<>(2);
        chosenMeals.add(new ChosenMeal(7, meal, option, "img", 2.5, 2, 5, cart));
        chosenMeals.add(new ChosenMeal(8, meal, option, "img", 2.5, 2, 5, cart));
        cart.setChosenMeals(chosenMeals);

        return cart;
    }

    public Cart getCurrentCart() {
        return currentCart;
    }

    public CartDTO getCartDTO() {
        return cartDTO;
    }

    public Cart differentCommentCart() {
        return new Cart(currentCart.getId(), currentCart.isCurrent(), "different", currentCart.getOverallPrice()
                , currentCart.getCustomer(), currentCart.getChosenMeals());
    }

    public CartDTO emptyCartDto() {
        return new CartDTO("cusName", new ArrayList<>(), "", 0);
    }

    public Cart emptyCart() {
        return new Cart(null, true, "", 0, new Customer(5, "cusName")
                , new ArrayList<>());
    }
}
