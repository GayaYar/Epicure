package com.moveo.epicure.controller;

import com.moveo.epicure.dto.LoginResponse;
import com.moveo.epicure.exception.IncorrectLoginException;
import com.moveo.epicure.service.UserService;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public ResponseEntity<LoginResponse> login(@RequestParam @Pattern(regexp = "^(.+)@(\\S+)$", message = "invalid email address")
    String email, @RequestParam @Size(min = 4) String password) {
        Optional<LoginResponse> optionalResponse = service.login(email, password);
        if(optionalResponse.isEmpty()) {
            throw new IncorrectLoginException();
        }
        return ResponseEntity.ok(optionalResponse.get());
    }

    @PostMapping
    public ResponseEntity<LoginResponse> signup(@RequestParam @Pattern(regexp = "^(.+)@(\\S+)$", message = "invalid email address")
    String email, @RequestParam @Size(min = 4) String password, @RequestParam String name) {
        return ResponseEntity.ok(service.signup(email, password, name));
    }
}
