package com.moveo.epicure.service;

import com.moveo.epicure.dto.LoginResponse;
import com.moveo.epicure.entity.User;
import com.moveo.epicure.repo.UserRepo;
import com.moveo.epicure.util.LoginResponseMaker;
import java.util.Optional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserRepo repo;
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepo repo, PasswordEncoder passwordEncoder) {
        this.repo = repo;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<LoginResponse> login(String email, String password) {
        Optional<User> optionalCustomer = repo.findByEmailAndPassword(email, passwordEncoder.encode(password));
        if(optionalCustomer.isPresent()) {
            return Optional.of(LoginResponseMaker.make(optionalCustomer.get()));
        }
        return Optional.empty();
    }

    public LoginResponse signup(String email, String password, String name) {
        User customer = repo.save(new User(name, email, passwordEncoder.encode(password)));
        return LoginResponseMaker.make(customer);
    }
}
