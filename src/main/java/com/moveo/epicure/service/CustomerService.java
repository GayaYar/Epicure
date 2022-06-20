package com.moveo.epicure.service;

import com.moveo.epicure.dto.CartDTO;
import com.moveo.epicure.dto.CartMealDTO;
import com.moveo.epicure.dto.UserDetail;
import com.moveo.epicure.dto.MealDTO;
import com.moveo.epicure.entity.Cart;
import com.moveo.epicure.entity.ChosenMeal;
import com.moveo.epicure.entity.User;
import com.moveo.epicure.entity.Meal;
import com.moveo.epicure.exception.NotFoundException;
import com.moveo.epicure.repo.CartRepo;
import com.moveo.epicure.repo.ChosenMealRepo;
import com.moveo.epicure.repo.MealRepo;
import com.moveo.epicure.repo.UserRepo;
import com.moveo.epicure.util.DtoMapper;
import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
@NoArgsConstructor
public class CustomerService {
    private UserDetail detail;
    private CartRepo cartRepo;
    private MealRepo mealRepo;
    private ChosenMealRepo chosenMealRepo;

    /**
     * Gets the customer's current cart.
     * If the customer doesn't have one, saves an empty cart as his current and returns it.
     * @return the customer's current cart.
     */
    private Cart getCurrentCart(boolean withMeals) {
        Integer customerId = detail.getId();
        Optional<Cart> optionalCart = withMeals ? cartRepo.findCurrentWithMeals(customerId) :
                cartRepo.findByCustomerIdAndCurrentTrue(customerId);
        if(optionalCart.isPresent()) {
            return optionalCart.get();
        }
        return cartRepo.save(new Cart(true, new User(customerId)));
    }

    public CartDTO getCart() {
        return DtoMapper.cartToDto(getCurrentCart(true));
    }

    public String updateCartComment(String comment) {
        Cart currentCart = getCurrentCart(false);
        currentCart.setComment(comment);
        cartRepo.save(currentCart);
        return comment;
    }

    public void buyCart() {
        Cart currentCart = getCurrentCart(false);
        currentCart.setCurrent(false);
        cartRepo.save(currentCart);
    }

    /**
     * Saves the mealDto as chosenMeal, turns the new chosenMeal to cartMeal and returns it.
     * @param mealDTO - meal to add to cart
     * @return the added meal as CartMealDto
     */
    public CartMealDTO addToCart(MealDTO mealDTO) {
        Optional<Meal> optionalMeal = mealRepo.findById(mealDTO.getId());
        if(optionalMeal.isEmpty()) {
            throw new NotFoundException("meal");
        }
        Meal meal = optionalMeal.get();
        Cart currentCart = getCurrentCart(false);

        ChosenMeal chosenMeal = chosenMealRepo.save(
                new ChosenMeal(DtoMapper.choiceDtosToOptions(mealDTO.getChoices()), meal.getImg(), mealDTO.getPrice(),
                        mealDTO.getQuantity(), mealDTO.getPrice() * mealDTO.getQuantity(), meal, currentCart));

        return DtoMapper.chosenMealToCartMeal(chosenMeal);
    }

    public void deleteFromCart(Integer mealId) {
        chosenMealRepo.deleteByIdAndCart(mealId, getCurrentCart(false));
    }

    public void clearCart() {
        Cart currentCart = getCurrentCart(false);
        chosenMealRepo.deleteByCart(currentCart);
        currentCart.defaultValues();
        cartRepo.save(currentCart);
    }

    public List<CartDTO> getHistory() {
        return cartRepo.findByCustomerIdAndCurrentFalse(detail.getId()).stream().map(DtoMapper::cartToDto).collect(
                Collectors.toList());
    }
}
