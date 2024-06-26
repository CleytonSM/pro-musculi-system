package com.cleyton.promusculisystem.helper;

import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Component
public class SecretKeyHelper {

    @Value("${security.jwt.token.secret-key}")
    private String secretKey;

    public SecretKey secretKeyBuilder() {
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }
}
