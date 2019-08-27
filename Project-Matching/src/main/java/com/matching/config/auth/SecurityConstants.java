package com.matching.config.auth;

import java.util.UUID;

public class SecurityConstants {

    public static final String AUTH_LOGIN_URL = "/api/login";

    public static final String JWT_SECRET = UUID.randomUUID().toString();

    public static final String TOKEN_HEADER = "Authorization";

    public static final String TOKEN_PREFIX = "Bearer ";

    public static final String TOKEN_TYPE = "JwtToken";

    public static final String TOKEN_ISSUER = "secure-api";

    public static final String TOKEN_AUDIENCE = "secure-app";

    private SecurityConstants() {
        throw new IllegalStateException("Cannot create instance of static util class");
    }
}
