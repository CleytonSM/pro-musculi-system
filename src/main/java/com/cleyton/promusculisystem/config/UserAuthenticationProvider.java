package com.cleyton.promusculisystem.config;

import com.cleyton.promusculisystem.model.Authority;
import com.cleyton.promusculisystem.model.User;
import com.cleyton.promusculisystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

    @Component
    public class UserAuthenticationProvider implements AuthenticationProvider {

        @Autowired
        private UserRepository repository;

        @Autowired
        private PasswordEncoder passwordEncoder;

        @Override
        public Authentication authenticate(Authentication authentication) throws AuthenticationException {
            String userName = authentication.getName();
            String password = authentication.getCredentials().toString();

            Optional<User> optionalUser = repository.findByEmail(userName);

            if(optionalUser.isPresent()) {
                User user = optionalUser.get();

                if(passwordEncoder.matches(password, user.getPassword())) {
                    return new UsernamePasswordAuthenticationToken(user, password, grantedAuthority(user.getAuthorities()));
                } else {
                    throw new RuntimeException("Wrong Email or Password");
                }
            } else {
                throw new RuntimeException("User not found");
            }
        }

        private List<GrantedAuthority> grantedAuthority(Set<Authority> authorities) {
            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

            for (Authority authority : authorities) {
                grantedAuthorities.add(new SimpleGrantedAuthority(authority.getName()));
            }

            return grantedAuthorities;
        }

        @Override
        public boolean supports(Class<?> authentication) {
            return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
        }
    }
