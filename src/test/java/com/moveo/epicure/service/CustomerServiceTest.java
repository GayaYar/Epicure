package com.moveo.epicure.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import com.moveo.epicure.dto.UserDetail;
import com.moveo.epicure.entity.Cart;
import com.moveo.epicure.entity.ChosenMeal;
import com.moveo.epicure.entity.User;
import com.moveo.epicure.entity.Meal;
import com.moveo.epicure.exception.NotFoundException;
import com.moveo.epicure.mock.MockCustomer;
import com.moveo.epicure.repo.CartRepo;
import com.moveo.epicure.repo.ChosenMealRepo;
import com.moveo.epicure.repo.MealRepo;
import java.util.ArrayList;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    private CustomerService service;
    @Mock
    private UserDetail detail;
    @Mock
    private CartRepo cartRepo;
    @Mock
    private MealRepo mealRepo;
    @Mock
    private ChosenMealRepo chosenMealRepo;
    private MockCustomer mockCustomer;
    @Captor
    private ArgumentCaptor<Cart> cartArgumentCaptor;
    @Captor
    private ArgumentCaptor<ChosenMeal> chosenMealArgumentCaptor;

    /**
     * instantiates the necessary fields for the tests
     */
    @BeforeEach
    void initialiseTest() {
        service = new CustomerService(detail, cartRepo, mealRepo, chosenMealRepo);
        mockCustomer = new MockCustomer();
    }

    /**
     * assures the method returns the correct cart
     */
    @Test
    void getCartFound() {
        Mockito.when(detail.getId()).thenReturn(3);
        Mockito.when(cartRepo.findCurrentWithMeals(3)).thenReturn(Optional.of(mockCustomer.getCurrentCart()));
        assertEquals(service.getCart(), mockCustomer.getCartDTO());
    }

    /**
     * assuring that if the customer doesn't have a current cart, a new empty cart will be saved for him
     */
    @Test
    void getCartNotFound() {
        Mockito.when(detail.getId()).thenReturn(5);
        Mockito.when(cartRepo.findCurrentWithMeals(5)).thenReturn(Optional.empty());
        Mockito.when(cartRepo.save(new Cart(true, new User(5)))).thenReturn(mockCustomer.emptyCart());
        service.getCart();
        Mockito.verify(cartRepo, Mockito.times(1)).save(cartArgumentCaptor.capture());
        assertEquals(cartArgumentCaptor.getValue(), mockCustomer.emptyCart());
        assertEquals(service.getCart(), mockCustomer.emptyCartDto());
    }

    /**
     * makes sure that the method changes the "comment" field appropriately and saves the cart
     */
    @Test
    void updateCartComment() {
        Cart cart = new Cart(5, true, "yes", 58.9, new User(3)
                , null);
        Cart difCart = new Cart(5, true, "different", 58.9, new User(3)
                , null);
        Mockito.when(cartRepo.findByCustomerIdAndCurrentTrue(3)).thenReturn(Optional.of(cart));
        Mockito.when(detail.getId()).thenReturn(3);
        service.updateCartComment("different");
        Mockito.verify(cartRepo, Mockito.times(1)).save(cartArgumentCaptor.capture());
        assertEquals(cartArgumentCaptor.getValue(), difCart);
    }

    /**
     * makes sure that the method converts the cart's "current" field to false and saves it
     */
    @Test
    void buyCart() {
        Cart cart = new Cart(5, true, "yes", 58.9, new User(3)
                , null);
        Cart notCurrentCart = new Cart(5, false, "yes", 58.9, new User(3)
                , null);
        Mockito.when(cartRepo.findByCustomerIdAndCurrentTrue(3)).thenReturn(Optional.of(cart));
        Mockito.when(detail.getId()).thenReturn(3);
        service.buyCart();
        Mockito.verify(cartRepo, Mockito.times(1)).save(cartArgumentCaptor.capture());
        assertEquals(cartArgumentCaptor.getValue(), notCurrentCart);
    }

    /**
     * checks that in an event where no such meal exists, a "NotFoundException" with a proper message is thrown
     */
    @Test
    void addToCartMealNotFound() {
        Mockito.when(mealRepo.findById(1)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> {
            service.addToCart(mockCustomer.mockMealDto());
        }).isInstanceOf(NotFoundException.class)
                .hasMessage("Could not find the meal you were looking for.");
    }

    /**
     * checks that the method saves a converted meal (from mealDto to chosenMeal)
     */
    @Test
    void addToCartVerifySave() {
        Meal mockMeal = mockCustomer.mockMeal();
        Cart noMealsCart = mockCustomer.noMealsCart();
        Mockito.when(mealRepo.findById(1)).thenReturn(Optional.of(mockMeal));
        Mockito.when(cartRepo.findByCustomerIdAndCurrentTrue(3)).thenReturn(Optional.of(noMealsCart));
        Mockito.when(detail.getId()).thenReturn(3);
        Mockito.when(chosenMealRepo.save(mockCustomer.convertedChosenMeal(mockMeal, noMealsCart)))
                .thenReturn(mockCustomer.mockChosenMeal(mockMeal, noMealsCart));
        service.addToCart(mockCustomer.mockMealDto());
        Mockito.verify(chosenMealRepo, Mockito.times(1)).save(chosenMealArgumentCaptor.capture());
        assertEquals(chosenMealArgumentCaptor.getValue(), mockCustomer.convertedChosenMeal(mockMeal, noMealsCart));
    }

    /**
     * checks that the returned value is converted (from mealDto to chosenMeal to cartMeal)
     */
    @Test
    void addToCartAssertReturnedType() {
        Meal mockMeal = mockCustomer.mockMeal();
        Cart noMealsCart = mockCustomer.noMealsCart();
        Mockito.when(mealRepo.findById(1)).thenReturn(Optional.of(mockMeal));
        Mockito.when(cartRepo.findByCustomerIdAndCurrentTrue(3)).thenReturn(Optional.of(noMealsCart));
        Mockito.when(detail.getId()).thenReturn(3);
        Mockito.when(chosenMealRepo.save(mockCustomer.convertedChosenMeal(mockMeal, noMealsCart)))
                .thenReturn(mockCustomer.mockChosenMeal(mockMeal, noMealsCart));
        assertEquals(service.addToCart(mockCustomer.mockMealDto()), mockCustomer.mockCartMeal());
    }

    /**
     * verifies that the method calls the repository's "deleteByIdAndCart" method
     */
    @Test
    void deleteFromCart() {
        Cart noMealsCart = mockCustomer.noMealsCart();
        Mockito.when(cartRepo.findByCustomerIdAndCurrentTrue(3)).thenReturn(Optional.of(noMealsCart));
        Mockito.when(detail.getId()).thenReturn(3);
        service.deleteFromCart(6);
        Mockito.verify(chosenMealRepo, Mockito.times(1)).deleteByIdAndCart(6, noMealsCart);
    }

    /**
     * verifies that the method deletes all the chosen meals that are linked to the cart (calls the deleteByCart
     * method)
     */
    @Test
    void clearCartDeleteMeals() {
        Cart cart = new Cart(5, true, "yes", 58.9, new User(3)
                , null);
        Mockito.when(cartRepo.findByCustomerIdAndCurrentTrue(3)).thenReturn(Optional.of(cart));
        Mockito.when(detail.getId()).thenReturn(3);
        service.clearCart();
        Mockito.verify(chosenMealRepo, Mockito.times(1)).deleteByCart(cartArgumentCaptor.capture());
        assertEquals(cartArgumentCaptor.getValue(), cart);
    }

    /**
     * verifies that the method updates the cart and saves it with default (empty) values
     */
    @Test
    void clearCart() {
        User customer = new User(3);
        Mockito.when(cartRepo.findByCustomerIdAndCurrentTrue(3)).thenReturn(Optional.of
                (new Cart(5, true, "yes", 58.9, customer, null)));
        Mockito.when(detail.getId()).thenReturn(3);
        service.clearCart();
        Mockito.verify(cartRepo, Mockito.times(1)).save(cartArgumentCaptor.capture());
        assertEquals(cartArgumentCaptor.getValue(), new Cart(5, true, "", 0, customer, new ArrayList<>()));
    }

    /**
     * assures the method returns the list returned by "cartRepo.findByCustomerIdAndCurrentFalse" in a form of dto
     */
    @Test
    void getHistory() {
        Mockito.when(cartRepo.findByCustomerIdAndCurrentFalse(3)).thenReturn(mockCustomer.mockHistory());
        Mockito.when(detail.getId()).thenReturn(3);
        assertEquals(service.getHistory(), mockCustomer.mockHistoryDto());
    }
}