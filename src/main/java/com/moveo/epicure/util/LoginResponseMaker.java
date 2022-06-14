package com.moveo.epicure.util;

import com.moveo.epicure.dto.LoginResponse;
import com.moveo.epicure.entity.Customer;
import com.moveo.epicure.entity.PermittedType;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.Date;

public class LoginResponseMaker {

    public static LoginResponse make(Integer id, String name, PermittedType customerType) {
        NullUtil.validate(id);
        name = name == null ? "" : name;
        String jwts = Jwts.builder()
                .setIssuer("epicure")
                .setSubject("" + id)
                .claim("type", customerType==null ? "CUSTOMER" : customerType.toString())
                .setIssuedAt(new Date())
                .signWith(Keys.hmacShaKeyFor("sdhfhsdfggusdfkuygsdufggfbgvtsdgfurfbocvajnrgaiuetjrbg ".getBytes()))
                .compact();
        return new LoginResponse(name, jwts);
    }

    public static LoginResponse make(Customer customer) {
        return make(customer.getId(), customer.getName(), customer.getType());
    }
}
