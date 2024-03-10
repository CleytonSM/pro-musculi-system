package com.cleyton.promusculisystem.helper;

import com.cleyton.promusculisystem.model.Authority;
import com.cleyton.promusculisystem.model.User;
import com.cleyton.promusculisystem.model.dto.RoleDto;
import com.cleyton.promusculisystem.model.dto.UserDto;
import com.cleyton.promusculisystem.repository.AuthorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
public class ModelHelper {

    public static User userAttributeSetter(UserDto userDto, PasswordEncoder passwordEncoder, Authority authority) {
        User user = new User();

        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        user.setAuthorities(authoritySet(authority));

        return user;
    }

    public static User updateUserAttributeSetter(User user, UserDto userDto, PasswordEncoder passwordEncoder, Authority authority) {
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
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

    public static void verifyRole (UserDto userDto) {
        if (!userDto.getRole().toString().equals(RoleDto.ROLE_ADMIN.toString())
                && !userDto.getRole().toString().equals(RoleDto.ROLE_USER.toString())) {
            throw new RuntimeException("Invalid role submission");
        }
    }
}
