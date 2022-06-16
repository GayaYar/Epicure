package com.moveo.epicure.mock;

import com.moveo.epicure.dto.CartDTO;
import com.moveo.epicure.dto.CartMealDTO;
import com.moveo.epicure.dto.ChoiceDTO;
import com.moveo.epicure.dto.LoginInfo;
import com.moveo.epicure.dto.LoginResponse;
import com.moveo.epicure.dto.MealDTO;
import com.moveo.epicure.dto.OptionDTO;
import com.moveo.epicure.dto.RegisterInfo;
import com.moveo.epicure.entity.Cart;
import com.moveo.epicure.entity.Choice;
import com.moveo.epicure.entity.ChosenMeal;
import com.moveo.epicure.entity.Customer;
import com.moveo.epicure.entity.Label;
import com.moveo.epicure.entity.Meal;
import com.moveo.epicure.entity.Option;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class MockCustomer {
    private Cart currentCart;
    private CartDTO cartDTO;

    public MockCustomer() {
        this.currentCart = mockCart();
        this.cartDTO = mockCartDto();
    }

    private CartDTO mockCartDto() {
        List<OptionDTO> optionDTOS = new ArrayList<>(1);
        optionDTOS.add(new OptionDTO(1, "avocado"));
        List<CartMealDTO> cartMeals = new ArrayList<>(2);
        cartMeals.add(new CartMealDTO(7, "img", 2.5, optionDTOS, 2, 5));
        cartMeals.add(new CartMealDTO(8, "img", 2.5, optionDTOS, 2, 5));

        return new CartDTO("cusName", cartMeals, "yes", 58.9);
    }

    public MealDTO mockMealDto() {
        List<ChoiceDTO> choiceDTOS = new ArrayList<>(1);
        List<OptionDTO> optionDTOS = new ArrayList<>(1);
        optionDTOS.add(new OptionDTO(1, "avocado"));
        choiceDTOS.add(new ChoiceDTO("choice", optionDTOS, 1,3));
        List<Label> labels = new ArrayList<>(2);
        labels.add(Label.SPICY);
        labels.add(Label.VEGAN);

        return new MealDTO(1, "meal1", "des", labels, 15.5, choiceDTOS, "img", 1);
    }

    public Meal mockMeal() {
        List<Choice> mealChoices = new ArrayList<>(1);
        List<Option> option = new ArrayList<>(1);
        option.add(new Option(1, "avocado"));
        mealChoices.add(new Choice(2, "choice", 1, 3, option));
        return new Meal(1, "meal1", "des", true, true, false, 15.5
                , "img", "food", mealChoices);
    }

    public ChosenMeal convertedChosenMeal(Meal mockMeal, Cart cart) {
        List<Option> options = new ArrayList<>(1);
        options.add(new Option(1, "avocado"));
        return new ChosenMeal(null, mockMeal, options, "img", 15.5, 1, 15.5, cart);
    }

    public ChosenMeal mockChosenMeal(Meal mockMeal, Cart cart) {
        List<Option> options = new ArrayList<>(1);
        options.add(new Option(1, "avocado"));
        return new ChosenMeal(7, mockMeal, options, "img", 15.5, 1, 15.5, cart);
    }

    public CartMealDTO mockCartMeal() {
        List<OptionDTO> optionDTOS = new ArrayList<>(1);
        optionDTOS.add(new OptionDTO(1, "avocado"));
        return new CartMealDTO(7, "img", 15.5, optionDTOS, 1, 15.5);
    }

    public Cart noMealsCart() {
        return new Cart(3, true, "yes", 58.9, new Customer(3, "cusName"), null);
    }

    private Cart mockCart() {
        Cart cart = new Cart(5, true, "yes", 58.9, new Customer(3, "cusName"), null);
        Meal meal = mockMeal();
        List<Option> option = new ArrayList<>(1);
        option.add(new Option(1, "avocado"));
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
                , currentCart.getCustomer(), null);
    }

    public CartDTO emptyCartDto() {
        return new CartDTO("cusName", new ArrayList<>(), "", 0);
    }

    public Cart emptyCart() {
        return new Cart(null, true, "", 0, new Customer(5, "cusName")
                , new ArrayList<>());
    }

    public Customer mockCustomer() {
        return new Customer(9, "mock cus", "mockCus@gmail.com", "12345678");
    }

    public LoginResponse mockResponse() {
        String jwts = Jwts.builder()
                .setIssuer("epicure")
                .setSubject("9")
                .claim("customerName", "mock cus")
                .setIssuedAt(new Date())
                .signWith(Keys.hmacShaKeyFor("sdhfhsdfggusdfkuygsdufggfbgvtsdgfurfbocvajnrgaiuetjrbg ".getBytes()))
                .compact();
        return new LoginResponse("mock cus", jwts);
    }

    public RegisterInfo mockRegisterInfo() {
        return new RegisterInfo(new LoginInfo("mockCus@gmail.com", "12345678"), "mock cus");
    }

    public List<Cart> mockHistory() {
        List<Cart> history = new ArrayList<>(2);

        Customer customer = new Customer(3, "cusName");
        Cart cart = new Cart(5, false, "yes", 58.9, customer, null);
        List<Choice> mealChoices = new ArrayList<>(1);
        List<Option> option = new ArrayList<>(1);
        option.add(new Option(1, "avocado"));
        mealChoices.add(new Choice(2, "choice", 1, 3, option));
        Meal meal =  new Meal(1, "meal1", "des", true, true, false, 15.5
                , "img", "food", mealChoices);
        List<ChosenMeal> chosenMeals = new ArrayList<>(2);
        chosenMeals.add(new ChosenMeal(7, meal, option, "img", 2.5, 2, 5, cart));
        chosenMeals.add(new ChosenMeal(8, meal, option, "img", 2.5, 2, 5, cart));
        cart.setChosenMeals(chosenMeals);
        history.add(cart);

        Cart cartB = new Cart(2, false, "message", 6.5, customer, new ArrayList<>());
        history.add(cartB);

        return history;
    }

    public List<CartDTO> mockHistoryDto() {
        List<CartDTO> history = new ArrayList<>(2);

        List<OptionDTO> optionDTOS = new ArrayList<>(1);
                optionDTOS.add(new OptionDTO(1, "avocado"));
        List<CartMealDTO> cartMealDTOS = new ArrayList<>(2);
        cartMealDTOS.add(new CartMealDTO(7, "img", 2.5, optionDTOS, 2, 5));
        cartMealDTOS.add(new CartMealDTO(8, "img", 2.5, optionDTOS, 2, 5));
        history.add(new CartDTO("cusName", cartMealDTOS, "yes", 58.9));

        history.add(new CartDTO("cusName", new ArrayList<>(), "message", 6.5));

        return history;
    }

}
