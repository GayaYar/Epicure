package com.moveo.epicure.util;

import com.moveo.epicure.dto.LoginResponse;
import com.moveo.epicure.entity.User;
import com.moveo.epicure.entity.UserType;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class LoginResponseMaker {
    @Value("${secret.token.key}")
    private String key;

    public LoginResponse make(Integer id, String name, UserType userType) {
        name = name == null ? "" : name;
        String jwts = Jwts.builder()
                .setIssuer("epicure")
                .setSubject("" + id)
                .claim("userType", userType)
                .claim("customerName", name)
                .setIssuedAt(new Date())
                .signWith(Keys.hmacShaKeyFor(key.getBytes()))
                .compact();
        return new LoginResponse(name, jwts);
    }

    public LoginResponse make(User user) {
        return make(user.getId(), user.getName(), user.getUserType());
    }
}
