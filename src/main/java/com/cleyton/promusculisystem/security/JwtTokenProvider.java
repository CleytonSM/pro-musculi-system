package com.cleyton.promusculisystem.security;

import com.cleyton.promusculisystem.exceptions.InvalidJWTToken;
import com.cleyton.promusculisystem.exceptions.JwtTokenCreationException;
import com.cleyton.promusculisystem.helper.SecretKeyHelper;
import com.cleyton.promusculisystem.model.User;
import com.cleyton.promusculisystem.repository.UserRepository;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
public class JwtTokenProvider {

    @Value("${security.jwt.token.secret-key}")
    private String secretKey;

    @Value("${security.jwt.token.expire-length}")
    private long validityInMilliseconds;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SecretKeyHelper secretKeyHelper;

    public String createToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new JwtTokenCreationException("Unauthorized User");
        }

        Date now = new Date();
        SecretKey key = secretKeyHelper.secretKeyBuilder(secretKey);

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
        claims.put("authorities", populateAuthorities(authentication.getAuthorities()));

        return claims;
    }

    private String populateAuthorities(Collection<? extends GrantedAuthority> collection) {
        Set<String> authoritiesSet = new HashSet<>();
        for (GrantedAuthority authority : collection) {
            authoritiesSet.add(authority.getAuthority());
        }
        return String.join(",", authoritiesSet);
    }

    public boolean validateToken(String token) {
        SecretKey key = secretKeyHelper.secretKeyBuilder(secretKey);
        try {
            Jwts.parser().verifyWith(key).build().parseUnsecuredClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            throw new InvalidJWTToken("Expired or invalid JWT token");
        }
    }
//
//    public String getUsername(String token) {
//        SecretKey key = secretKeyHelper.secretKeyBuilder(secretKey);
//
//        return Jwts.parser().verifyWith(key).build().parseUnsecuredClaims(token)
//                .getPayload().getSubject();
//    }
//
//    public Authentication getAuthentication(String token) {
//        User user = verifyOptionalEntity(userRepository.findByEmail(getUsername(token)));
//
//        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
//
//        for (Authority authority : user.getAuthorities()) {
//            grantedAuthorities.add(new SimpleGrantedAuthority(authority.getName()));
//        }
//        return new UsernamePasswordAuthenticationToken(user, "", grantedAuthorities);
//    }
}
