package com.moveo.epicure.service;

import com.moveo.epicure.dto.LoginResponse;
import com.moveo.epicure.dto.RegisterInfo;
import com.moveo.epicure.entity.LoginAttempt;
import com.moveo.epicure.entity.User;
import com.moveo.epicure.entity.UserType;
import com.moveo.epicure.exception.AccountBlockedException;
import com.moveo.epicure.exception.AlreadyExistsException;
import com.moveo.epicure.repo.AttemptRepo;
import com.moveo.epicure.repo.UserRepo;
import com.moveo.epicure.util.LoginResponseMaker;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserRepo repo;
    private PasswordEncoder passwordEncoder;
    private AttemptRepo attemptRepo;
    private static final long ALLOWED_ATTEMPTS = 10;
    private static final int ATTEMPT_MINUTES = 30;

    public UserService(UserRepo repo, PasswordEncoder passwordEncoder, AttemptRepo attemptRepo) {
        this.repo = repo;
        this.passwordEncoder = passwordEncoder;
        this.attemptRepo = attemptRepo;
    }

    public Optional<LoginResponse> login(String email, String password, LocalDateTime now) {
        return loginLogic(email, password, now);
    }

    private Optional<LoginResponse> loginLogic(String email, String password, LocalDateTime now) {
        Optional<User> validUser = getValidUser(email, now);
        if (validUser.isPresent()) {
            User user = validUser.get();
            if (passwordEncoder.matches(password, user.getPassword())) {
                return Optional.of(LoginResponseMaker.make(user));
            }else {
                attemptRepo.save(new LoginAttempt(email, now));
            }
        }
        return Optional.empty();
    }

    private Optional<User> getValidUser(String email, LocalDateTime now) {
        Optional<User> optionalUser = repo.findByEmail(email);
        if (optionalUser.isPresent()) {
            long attemptCount = attemptRepo.countByMailInTime(email, now.minusMinutes(ATTEMPT_MINUTES), now);
            if (attemptCount >= ALLOWED_ATTEMPTS) {
                throw new AccountBlockedException();
            } else {
                return optionalUser;
            }
        }
        return Optional.empty();
    }
    public LoginResponse signup(RegisterInfo info) {
        String email = info.getEmail();
        if (repo.existsByEmail(email)) {
            throw new AlreadyExistsException("email");
        }
        User user = repo.save(new User(info.getName(), email, passwordEncoder.encode(info.getPassword())));
        return LoginResponseMaker.make(user);
    }

    public void saveAdmin(String email, String password, String name) {
        if (repo.existsByEmail(email)) {
            throw new AlreadyExistsException("email");
        }
        repo.save(new User(name, email, passwordEncoder.encode(password), UserType.ADMIN));
    }
}
