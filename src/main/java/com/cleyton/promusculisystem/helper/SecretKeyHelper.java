package com.cleyton.promusculisystem.helper;

import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Component
public class SecretKeyHelper {

    public SecretKey secretKeyBuilder() {
        String secretKey = "MDocZiuc5sJExb0XvMKSOeFj_2_spFjZYRCJpwRB69Y";
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }
}
