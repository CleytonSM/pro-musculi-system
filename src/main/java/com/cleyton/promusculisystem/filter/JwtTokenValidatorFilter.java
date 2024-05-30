package com.cleyton.promusculisystem.filter;

import com.cleyton.promusculisystem.exceptions.InvalidJWTToken;
import com.cleyton.promusculisystem.helper.SecretKeyHelper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;

public class JwtTokenValidatorFilter extends OncePerRequestFilter {

    @Value("${security.jwt.token.secret-key}")
    private String secretKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String jwt = request.getHeader("Authorization");
        // TODO resolve token here?
        if(jwt != null) {
            try {
                SecretKeyHelper secretKeyHelper = new SecretKeyHelper();
                SecretKey key = secretKeyHelper.secretKeyBuilder(secretKey);

                Claims claims = Jwts.parser()
                        .decryptWith(key)
                        .build()
                        .parseUnsecuredClaims(jwt)
                        .getPayload();

                String username = String.valueOf(claims.get("email"));
                String authorities = (String) claims.get("authorities");

                Authentication authentication = new UsernamePasswordAuthenticationToken(username,
                        null, AuthorityUtils.commaSeparatedStringToAuthorityList(authorities));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e) {
                throw new InvalidJWTToken("Invalid Token received!");
            }
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return request.getServletPath().equals("/user/auth");
    }
}
