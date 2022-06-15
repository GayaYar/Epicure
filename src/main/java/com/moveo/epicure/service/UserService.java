package com.moveo.epicure.service;

import com.moveo.epicure.dto.LoginInfo;
import com.moveo.epicure.dto.LoginResponse;
import com.moveo.epicure.dto.RegisterInfo;
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

    public Optional<LoginResponse> login(LoginInfo info) {
        Optional<User> optionalCustomer = repo.findByEmailAndPassword(info.getEmail()
                , passwordEncoder.encode(info.getPassword()));
        if(optionalCustomer.isPresent()) {
            return Optional.of(LoginResponseMaker.make(optionalCustomer.get()));
        }
        return Optional.empty();
    }

    public LoginResponse signup(RegisterInfo info) {
        LoginInfo loginInfo = info.getLoginInfo();
        User customer = repo.save(new User(info.getName(), loginInfo.getEmail()
                , passwordEncoder.encode(loginInfo.getPassword())));
        return LoginResponseMaker.make(customer);
    }
}
