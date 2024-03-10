package com.cleyton.promusculisystem.helper;

import com.cleyton.promusculisystem.model.Authority;
import com.cleyton.promusculisystem.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
public class ModelHelper {


    public static User userAttributeSetter(User user, PasswordEncoder passwordEncoder, Authority authority) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        user.setAuthorities(authoritySet(authority));

        return user;
    }


    private static Set<Authority> authoritySet(Authority authority) {
        Set<Authority> authorities = new HashSet<>();
        authorities.add(authority);
        return authorities;
    }


    public static Object verifyEmptyOptionalEntity (Optional<?> optionalObject) {
        if(optionalObject.isEmpty()) {
            throw new RuntimeException("This entity doesn't exists");
        }

        return optionalObject.get();
    }
}
