package com.moveo.epicure.mock;

import com.moveo.epicure.dto.LoginResponse;
import com.moveo.epicure.entity.LoginAttempt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MockCustomer {

    //necessary for limit login test
    public List<LoginAttempt> getBlockedAttempt() {
        List<LoginAttempt> attempts = new ArrayList<>(9);
        for(int i=1; i<=9; i++) {
            attempts.add(new LoginAttempt("blocked@mail.com", LocalDateTime.now().minusMinutes((long) i)));
        }
        return attempts;
    }

    //necessary for limit login test
    public List<LoginAttempt> getNotBlockedAttempt() {
        List<LoginAttempt> attempts = new ArrayList<>(9);
        for(int i=1; i<=9; i++) {
            attempts.add(new LoginAttempt("mockCus@gmail.com", LocalDateTime.now().minusMinutes((long) i*10)));
        }
        return attempts;
    }

    public LoginResponse mockResponse() {
        String jwts = Jwts.builder()
                .setIssuer("epicure")
                .setSubject("9")
                .claim("customerName", "mock cus")
                .setIssuedAt(new Date())
                .signWith(Keys.hmacShaKeyFor("sdhfhsdfggusdfkuygsdufggfbgvtsdgfurfbocvajnrgaiuetjrbg ".getBytes()))
                .compact();
        return new LoginResponse("mock cus", jwts);
    }
}
