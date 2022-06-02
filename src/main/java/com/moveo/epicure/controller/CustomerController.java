package com.moveo.epicure.controller;

import com.moveo.epicure.dto.CartDTO;
import com.moveo.epicure.dto.CartMealDTO;
import com.moveo.epicure.dto.LoginInfo;
import com.moveo.epicure.dto.LoginResponse;
import com.moveo.epicure.exception.IncorrectLoginException;
import com.moveo.epicure.dto.MealDTO;
import com.moveo.epicure.dto.RegisterInfo;
import com.moveo.epicure.service.CustomerService;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("customers")
public class CustomerController {

    @Autowired
    private CustomerService service;

    @GetMapping(value = "/cart")
    public ResponseEntity<CartDTO> getCart() {
        return ResponseEntity.ok(service.getCart());
    }

    @PutMapping(value = "/cart")
    public ResponseEntity<Void> updateCart(@Valid @RequestBody String cartComment) {
        service.updateCartComment(cartComment);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value="/order")
    public ResponseEntity<Void> buyCart() {
        service.buyCart();
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/cart/meal")
    public ResponseEntity<CartMealDTO> addToCart(@Valid @RequestBody MealDTO meal) {
        return ResponseEntity.ok(service.addToCart(meal));
    }

    @DeleteMapping(value = "/cart/meal")
    public ResponseEntity<Void> deleteMeal(@RequestParam Integer mealId) {
        service.deleteFromCart(mealId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping ResponseEntity<Void> clearCart() {
        service.clearCart();
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginInfo info) {
        Optional<LoginResponse> optionalResponse = service.login(info);
        if(optionalResponse.isEmpty()) {
            throw new IncorrectLoginException();
        }
        return ResponseEntity.ok(optionalResponse.get());
    }

    @PostMapping
    public ResponseEntity<LoginResponse> signup(@Valid @RequestBody RegisterInfo info) {
        return ResponseEntity.ok(service.signup(info));
    }

    @GetMapping("/history")
    public ResponseEntity<List<CartDTO>> getHistory() {
        return ResponseEntity.ok(service.getHistory());
    }

}
