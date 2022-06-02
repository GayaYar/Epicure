package com.moveo.epicure.service;

import static org.junit.jupiter.api.Assertions.*;

import com.moveo.epicure.dto.CustomerDetail;
import com.moveo.epicure.entity.Cart;
import com.moveo.epicure.entity.Customer;
import com.moveo.epicure.mock.MockCustomer;
import com.moveo.epicure.repo.CartRepo;
import com.moveo.epicure.repo.ChosenMealRepo;
import com.moveo.epicure.repo.CustomerRepo;
import com.moveo.epicure.repo.MealRepo;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;

class CustomerServiceTest {
    private CustomerService service;
    @Mock
    private CustomerDetail detail;
    @Mock
    private CustomerRepo customerRepo;
    @Mock
    private CartRepo cartRepo;
    @Mock
    private MealRepo mealRepo;
    @Mock
    private ChosenMealRepo chosenMealRepo;
    @Mock
    private PasswordEncoder passwordEncoder;
    private MockCustomer mockCustomer;
    @Captor
    private ArgumentCaptor<Cart> cartArgumentCaptor;

    @BeforeAll
    void initialiseTest() {
        service = new CustomerService(detail, customerRepo, cartRepo, mealRepo, chosenMealRepo, passwordEncoder);
        mockCustomer = new MockCustomer();
    }

    @Test
    void getCartFound() {
        Mockito.when(detail.getId()).thenReturn(3);
        Mockito.when(cartRepo.findCurrentWithMeals(3)).thenReturn(Optional.of(mockCustomer.getCurrentCart()));
        assertEquals(service.getCart(), mockCustomer.getCartDTO());
    }

    @Test
    @DisplayName("assuring that if the customer doesn't have a current cart, a new empty cart will be saved for him")
    void getCartNotFound() {
        Mockito.when(detail.getId()).thenReturn(5);
        Mockito.when(detail.getName()).thenReturn("cusName");
        Mockito.when(cartRepo.findCurrentWithMeals(5)).thenReturn(Optional.empty());
        service.getCart();
        Mockito.verify(cartRepo, Mockito.times(1)).save(cartArgumentCaptor.capture());
        assertEquals(cartArgumentCaptor.getValue(), mockCustomer.emptyCart());
        assertEquals(service.getCart(), mockCustomer.emptyCartDto());
    }

    @Test
    void updateCartComment() {
        Cart cart = new Cart(5, true, "yes", 58.9, new Customer(3, "cusName")
                , null);
        Cart difCart = new Cart(5, true, "different", 58.9, new Customer(3, "cusName")
                , null);
        Mockito.when(cartRepo.findByCustomerIdAndCurrentTrue(3)).thenReturn(Optional.of(cart));
        Mockito.when(detail.getId()).thenReturn(3);
        service.updateCartComment("different");
        Mockito.verify(cartRepo, Mockito.times(1)).save(cartArgumentCaptor.capture());
        assertEquals(cartArgumentCaptor.getValue(), difCart);
    }

    @Test
    void buyCart() {
        Cart cart = new Cart(5, true, "yes", 58.9, new Customer(3, "cusName")
                , null);
        Cart notCurrentCart = new Cart(5, false, "yes", 58.9, new Customer(3, "cusName")
                , null);
        Mockito.when(cartRepo.findByCustomerIdAndCurrentTrue(3)).thenReturn(Optional.of(cart));
        Mockito.when(detail.getId()).thenReturn(3);
        service.buyCart();
        Mockito.verify(cartRepo, Mockito.times(1)).save(cartArgumentCaptor.capture());
        assertEquals(cartArgumentCaptor.getValue(), notCurrentCart);
    }

    @Test
    void addToCart() {
    }

    @Test
    void deleteFromCart() {
    }

    @Test
    void clearCart() {
    }

    @Test
    void login() {
    }

    @Test
    void signup() {
    }
}