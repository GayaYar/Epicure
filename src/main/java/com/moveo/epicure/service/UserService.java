package com.moveo.epicure.service;

import com.moveo.epicure.dto.LoginResponse;
import com.moveo.epicure.entity.User;
import com.moveo.epicure.entity.UserType;
import com.moveo.epicure.exception.AlreadyExistsException;
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
        Optional<User> optionalUser = repo.findByEmail(email);
        if (optionalUser.isPresent() && passwordEncoder.matches(password, optionalUser.get().getPassword())) {
            return Optional.of(LoginResponseMaker.make(optionalUser.get()));
        }
        return Optional.empty();
    }

    public LoginResponse signup(String email, String password, String name) {
        if (repo.existsByEmail(email)) {
            throw new AlreadyExistsException("email");
        }
        User user = repo.save(new User(name, email, passwordEncoder.encode(password)));
        return LoginResponseMaker.make(user);
    }

    public void saveAdmin(String email, String password, String name) {
        if (repo.existsByEmail(email)) {
            throw new AlreadyExistsException("email");
        }
        repo.save(new User(name, email, passwordEncoder.encode(password), UserType.ADMIN));
    }
}
