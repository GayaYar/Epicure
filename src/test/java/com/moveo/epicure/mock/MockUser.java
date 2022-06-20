package com.moveo.epicure.mock;

import com.moveo.epicure.dto.LoginResponse;
import com.moveo.epicure.entity.UserType;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.Date;

public class MockUser {
    public static LoginResponse getMockResponse() {
        String jwts = Jwts.builder()
                .setIssuer("epicure")
                .setSubject("" + 5)
                .claim("userType", UserType.CUSTOMER)
                .claim("customerName", "mocky")
                .setIssuedAt(new Date())
                .signWith(Keys.hmacShaKeyFor("sdhfhsdfggusdfkuygsdufggfbgvtsdgfurfbocvajnrgaiuetjrbg".getBytes()))
                .compact();
        return new LoginResponse("mocky", jwts);
    }
}
