package com.moveo.epicure.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TokenUtil {
    @Value("${secret.token.key}")
    private String key;

    /**
     * tries to validate and get the claims for token
     * @param httpRequest
     * @return the valid token claims
     * @throws Exception if one is thrown during the process
     */
    public Claims validateAndGetClaims(HttpServletRequest httpRequest) throws Exception{
        String token = httpRequest.getHeader("Authorization");
        if(token.startsWith("bearer")) {
            token = token.substring(7);
        }
        return Jwts.parserBuilder()
                .setSigningKey(key.getBytes())
                .build().parseClaimsJws(token).getBody();
    }

}
