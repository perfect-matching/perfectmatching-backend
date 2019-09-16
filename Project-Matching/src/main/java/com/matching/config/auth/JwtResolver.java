package com.matching.config.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

import javax.servlet.http.HttpServletRequest;

public class JwtResolver {
    private HttpServletRequest request;

    public JwtResolver(HttpServletRequest request) {
        this.request = request;
    }

    public String getUserByToken() {
        String token = request.getHeader(SecurityConstants.TOKEN_HEADER);
        Jws<Claims> parsedToken = Jwts.parser()
                .setSigningKey(SecurityConstants.JWT_SECRET.getBytes())
                .parseClaimsJws(token.replace("Bearer ", ""));

        return (String) parsedToken.getBody().get("email");
    }
}
