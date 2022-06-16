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
