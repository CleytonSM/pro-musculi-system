//package com.cleyton.promusculisystem.filter;
//
//import com.cleyton.promusculisystem.helper.SecretKeyHelper;
//import io.jsonwebtoken.Jwts;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import javax.crypto.SecretKey;
//import java.io.IOException;
//import java.util.Collection;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.Map;
//import java.util.Set;
//
//public class JwtTokenGeneratorFilter extends OncePerRequestFilter {
//
//    @Value("${security.jwt.token.secret-key}")
//    private String secretKey;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//            throws ServletException, IOException {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        if (authentication != null && authentication.isAuthenticated()) {
//            SecretKeyHelper secretKeyHelper = new SecretKeyHelper();
//            Date now = new Date();
//
//            SecretKey key = secretKeyHelper.secretKeyBuilder(secretKey);
//            String jwt = Jwts.builder().issuer("ProMusculiSystem")
//                    .claims(claimsSetUp(authentication))
//                    .subject("JWT token")
//                    .issuedAt(new Date(now.getTime()))
//                    .expiration(new Date(now.getTime() + 3600000))
//                    .signWith(key)
//                    .compact();
//            response.setHeader("Authorization", "Bearer " + jwt);
//        }
//
//        filterChain.doFilter(request, response);
//    }
//
//    public Map<String, Object> claimsSetUp(Authentication authentication) {
//        Map<String, Object> claims = new HashMap<>();
//
//        claims.put("email", authentication.getName());
//        claims.put("authorities", populateAuthorities(authentication.getAuthorities()));
//
//        return claims;
//    }
//
//    private String populateAuthorities(Collection<? extends GrantedAuthority> collection) {
//        Set<String> authoritiesSet = new HashSet<>();
//        for (GrantedAuthority authority : collection) {
//            authoritiesSet.add(authority.getAuthority());
//        }
//        return String.join(",", authoritiesSet);
//    }
//
//    @Override
//    protected boolean shouldNotFilter(HttpServletRequest request) {
//        return !request.getServletPath().equals("/user/auth");
//    }
//}
