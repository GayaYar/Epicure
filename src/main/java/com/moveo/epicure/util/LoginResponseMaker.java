package com.moveo.epicure.util;

import com.moveo.epicure.dto.LoginResponse;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.Date;

public class LoginResponseMaker {
    public static LoginResponse make(Integer id, String name) {
        NullUtil.validate(id);
        name = name==null ? "" : name;
        String jwts = Jwts.builder()
                .setIssuer("epicure")
                .setSubject(""+id)
                .setIssuedAt(new Date())
                .signWith(Keys.hmacShaKeyFor("sdhfhsdfggusdfkuygsdufggfbgvtsdgfurfbocvajnrgaiuetjrbg ".getBytes()))
                .compact();
        return new LoginResponse(name, jwts);
    }
}
