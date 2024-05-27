package com.cleyton.promusculisystem.security;

import com.cleyton.promusculisystem.helper.SecretKeyHelper;
import com.cleyton.promusculisystem.model.Authority;
import com.cleyton.promusculisystem.model.User;
import com.cleyton.promusculisystem.model.dto.UserDTO;
import com.cleyton.promusculisystem.repository.UserRepository;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.JwtParserBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.SecretKeyFactorySpi;
import java.nio.charset.StandardCharsets;
import java.security.Provider;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.cleyton.promusculisystem.helper.ModelAttributeSetterHelper.verifyOptionalEntity;

@Component
public class JWTTokenProvider {

    @Value("${security.jwt.token.secret-key}")
    private String secretKey;

    @Value("${security.jwt.token.expire-length}")
    private long validityInMilliseconds;

    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(String email, UserDTO userDTO, User user) {
        Date now = new Date();
        SecretKeyHelper secretKeyHelper = new SecretKeyHelper();

        Date validity = validitySetUp(now);
        SecretKey key = secretKeyHelper.secretKeyBuilder(secretKey);

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

    public boolean validateToken(String token) {

    }

    public String getUsername(String token) {
        SecretKeyHelper secretKeyHelper = new SecretKeyHelper();

        SecretKey key = secretKeyHelper.secretKeyBuilder(secretKey);

        return Jwts.parser().verifyWith(key)
    }

    public Authentication getAuthentication(String token) {
        User user = verifyOptionalEntity(userRepository.findByEmail(getUsername(token)));

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        for (Authority authority : user.getAuthorities()) {
            grantedAuthorities.add(new SimpleGrantedAuthority(authority.getName()));
        }
        return new UsernamePasswordAuthenticationToken(user, "", grantedAuthorities);
    }
}
