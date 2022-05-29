package com.moveo.epicure.service;

import com.moveo.epicure.dto.CartDTO;
import com.moveo.epicure.dto.CartMealDTO;
import com.moveo.epicure.dto.CustomerDetail;
import com.moveo.epicure.dto.LoginInfo;
import com.moveo.epicure.dto.LoginResponse;
import com.moveo.epicure.entity.Cart;
import com.moveo.epicure.entity.Customer;
import com.moveo.epicure.repo.CartRepo;
import com.moveo.epicure.repo.CustomerRepo;
import com.moveo.epicure.util.DtoMapper;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CustomerService {
    @Autowired
    private CustomerDetail detail;
    @Autowired
    private CustomerRepo customerRepo;
    @Autowired
    private CartRepo cartRepo;
    
    /**
     * Gets the customer's current cart.
     * If the customer doesn't have one, saves an empty cart as his current and returns it.
     * @return the customer's current cart.
     */
    public CartDTO getCart() {
        Optional<Cart> optionalCart = cartRepo.findByCustomerIdAndCurrentTrue(detail.getId());
        if(optionalCart.isPresent()) {
            return DtoMapper.cartToDto(optionalCart.get());
        }
        return DtoMapper.cartToDto(cartRepo.save(new Cart(true, new Customer(detail.getId()))));
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
