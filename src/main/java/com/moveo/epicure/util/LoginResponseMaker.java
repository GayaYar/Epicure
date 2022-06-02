package com.moveo.epicure.util;

import com.moveo.epicure.swagger.dto.LoginResponse;
import com.moveo.epicure.entity.Customer;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.Date;

public class LoginResponseMaker {

    public static LoginResponse make(Integer id, String name) {
        NullUtil.validate(id);
        name = name == null ? "" : name;
        String jwts = Jwts.builder()
                .setIssuer("epicure")
                .setSubject("" + id)
                .setIssuedAt(new Date())
                .signWith(Keys.hmacShaKeyFor("sdhfhsdfggusdfkuygsdufggfbgvtsdgfurfbocvajnrgaiuetjrbg ".getBytes()))
                .compact();
        return new LoginResponse(name, jwts);
    }

    public static LoginResponse make(Customer customer) {
        return make(customer.getId(), customer.getName());
    }
}
