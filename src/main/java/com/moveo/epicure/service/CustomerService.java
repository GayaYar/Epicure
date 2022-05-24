package com.moveo.epicure.service;

import com.moveo.epicure.dto.CartDTO;
import com.moveo.epicure.dto.CartMealDTO;
import com.moveo.epicure.dto.LoginInfo;
import com.moveo.epicure.dto.LoginResponse;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    public CartDTO getCart() {
        //to do
        return null;
    }

    public CartDTO updateCart(CartDTO cart) {
        //to do
        return null;
    }

    public void buyCart(CartDTO cart) {
        //to do
    }

    public void addToCart(CartMealDTO meal) {
        //to do
    }

    public void deleteFromCart(Integer mealId) {
        //to do
    }

    public void clearCart() {
        //to do
    }

    public Optional<LoginResponse> login(LoginInfo info) {
        //to do
        return null;
    }

    public LoginResponse signup(LoginInfo info) {
        //to do
        return null;
    }
}
