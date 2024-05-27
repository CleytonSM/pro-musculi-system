package com.cleyton.promusculisystem.security;

import com.cleyton.promusculisystem.model.User;
import com.cleyton.promusculisystem.model.dto.UserDTO;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JWTTokenProvider {

    @Value("${security.jwt.token.secret-key}")
    private String secretKey;

    @Value("${security.jwt.token.expire-length}")
    private long validityInMilliseconds;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(String email, UserDTO userDTO, User user) {
        Date now = new Date();
        Date validity = validitySetUp(now);

        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
                .issuer("ProMusculiSystem")
                .subject(email)
                .issuedAt(now)
                .expiration(validity)
                .claims(claimsSetUp(userDTO, user))
                .signWith(key)
                .compact();
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public Map<String, Object> claimsSetUp(UserDTO userDTO, User user) {
        Map<String, Object> claims = new HashMap<>();

        claims.put("authorities", user.getAuthorities().stream()
                .map(s -> new SimpleGrantedAuthority(s.getName())).map(SimpleGrantedAuthority::getAuthority));
        claims.put("user", userDTO);

        return claims;
    }

    public Date validitySetUp(Date now) {
        return new Date(now.getTime() + validityInMilliseconds);
    }
}
