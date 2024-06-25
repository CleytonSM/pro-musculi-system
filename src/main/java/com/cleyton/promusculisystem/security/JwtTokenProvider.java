package com.cleyton.promusculisystem.security;

import com.cleyton.promusculisystem.exceptions.InvalidJWTToken;
import com.cleyton.promusculisystem.helper.SecretKeyHelper;
import com.cleyton.promusculisystem.model.Authority;
import com.cleyton.promusculisystem.model.User;
import com.cleyton.promusculisystem.repository.UserRepository;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.repository.cdi.Eager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.cleyton.promusculisystem.helper.ModelAttributeSetterHelper.verifyOptionalEntity;

@Component
public class JwtTokenProvider {

    private SecretKeyHelper secretKeyHelper = new SecretKeyHelper();

    @Autowired
    private UserRepository repository;

    public String createToken(Authentication authentication) {
        Date now = new Date();
        SecretKey key = secretKeyHelper.secretKeyBuilder();

        return Jwts.builder().issuer("ProMusculiSystem")
                .claims(claimsSetUp(authentication))
                .subject("JWT token")
                .issuedAt(new Date(now.getTime()))
                .expiration(new Date(now.getTime() + 3600000))
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

    public Map<String, Object> claimsSetUp(Authentication authentication) {
        Map<String, Object> claims = new HashMap<>();
        User user = (User) authentication.getPrincipal();

        claims.put("email", user.getEmail());
        //claims.put("authorities", l.getAuthorities()));

        return claims;
    }

    public boolean validateToken(String token) {
        SecretKey key = secretKeyHelper.secretKeyBuilder();

        try {
            Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            throw new InvalidJWTToken(e.getMessage());
        }
    }

    public String getUsername(String token) {
        SecretKey key = secretKeyHelper.secretKeyBuilder();

        return Jwts.parser().verifyWith(key).build().parseSignedClaims(token)
                .getPayload().get("email", String.class);
    }

    public Authentication getAuthentication(String token) {
        User user = verifyOptionalEntity(repository.findByEmail(getUsername(token)));

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        for (Authority authority : user.getAuthorities()) {
            grantedAuthorities.add(new SimpleGrantedAuthority(authority.getName()));
        }
        return new UsernamePasswordAuthenticationToken(user, "", grantedAuthorities);
    }
}
