package com.moveo.epicure.service;

import com.moveo.epicure.dto.CartDTO;
import com.moveo.epicure.dto.CartMealDTO;
import com.moveo.epicure.dto.CustomerDetail;
import com.moveo.epicure.dto.LoginInfo;
import com.moveo.epicure.dto.LoginResponse;
import com.moveo.epicure.entity.Cart;
import com.moveo.epicure.repo.CartRepo;
import com.moveo.epicure.repo.CustomerRepo;
import com.moveo.epicure.util.DtoMapper;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    @Autowired
    private CustomerDetail detail;
    @Autowired
    private CustomerRepo customerRepo;
    @Autowired
    private CartRepo cartRepo;

    public CartDTO getCart() {
        Optional<Cart> optionalCart = cartRepo.findByCustomerIdAndCurrentTrue(detail.getId());
        if(optionalCart.isPresent()) {
            return DtoMapper.cartToDto(optionalCart.get());
        }
        //add empty cart with customer to db and return cart with the id
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
