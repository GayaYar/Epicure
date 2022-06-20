package com.moveo.epicure.util;

import com.moveo.epicure.dto.LoginResponse;
import com.moveo.epicure.entity.User;
import com.moveo.epicure.entity.UserType;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.Date;

public class LoginResponseMaker {

    public static LoginResponse make(Integer id, String name, UserType userType) {
        NullUtil.validate(id);
        name = name == null ? "" : name;
        String jwts = Jwts.builder()
                .setIssuer("epicure")
                .setSubject("" + id)
                .claim("userType", userType)
                .setIssuedAt(new Date())
                .signWith(Keys.hmacShaKeyFor("sdhfhsdfggusdfkuygsdufggfbgvtsdgfurfbocvajnrgaiuetjrbg".getBytes()))
                .compact();
        return new LoginResponse(name, jwts);
    }

    public static LoginResponse make(User user) {
        return make(user.getId(), user.getName(), user.getUserType());
    }
}
