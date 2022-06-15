package com.moveo.epicure.controller;

import com.moveo.epicure.dto.LoginInfo;
import com.moveo.epicure.dto.LoginResponse;
import com.moveo.epicure.dto.RegisterInfo;
import com.moveo.epicure.exception.IncorrectLoginException;
import com.moveo.epicure.service.UserService;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
@Validated
public class UserController {
    private UserService service;

    public UserController(UserService service) {
        this.service = service;
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
}
