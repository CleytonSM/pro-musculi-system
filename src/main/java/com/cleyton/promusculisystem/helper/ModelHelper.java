package com.cleyton.promusculisystem.helper;

import com.cleyton.promusculisystem.model.Authority;
import com.cleyton.promusculisystem.model.User;
import com.cleyton.promusculisystem.model.dto.UserDto;
import com.cleyton.promusculisystem.services.AuthorityService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
public class ModelHelper {

    @Autowired
    private AuthorityService authorityService;

    public User postUserAttributeSetter(UserDto userDto, PasswordEncoder passwordEncoder, String role) {
        User user = new User();

        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        Authority authority = authorityService.create(role, user);
        user.setAuthorities(authoritySetup(authority));

        return user;
    }

    public User updateUserAttributeSetter(User user, UserDto userDto, PasswordEncoder passwordEncoder) {
        Authority authority = authorityService.update(user, userDto);

        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        user.setAuthorities(authoritySetup(authority));

        return user;
    }

    public User patchUserAttributeSetter(User user, UserDto userDto, PasswordEncoder passwordEncoder) {
      if(userDto.getEmail() != null) {
          user.setEmail(userDto.getEmail());
      }
      if(userDto.getPassword() != null) {
          user.setPassword(passwordEncoder.encode(userDto.getPassword()));
      }
      if(userDto.getRole() != null) {
          Authority authority = authorityService.update(user, userDto);

          user.setAuthorities(authoritySetup(authority));
      }

      return user;
    }

    public User deleteUserAttributeSetter(User user) {
        Authority authority = authorityService.delete(user);

        user.setActive(Boolean.FALSE);
        user.setAuthorities(authoritySetup(authority));

        return user;
    }

    public User reactiveUserAttributeSetter(User user, UserDto userDto) {
        Authority authority = authorityService.update(user, userDto);

        user.setActive(Boolean.TRUE);
        user.setAuthorities(authoritySetup(authority));

        return user;
    }

    private Set<Authority> authoritySetup(Authority authority) {
        Set<Authority> authorities = new HashSet<>();
        authorities.add(authority);

        return authorities;
    }


    public static Object verifyEmptyOptionalEntity (Optional<?> optionalObject) {
        if(optionalObject.isEmpty()) {
            throw new EntityNotFoundException("This entity doesn't exists");
        }

        return optionalObject.get();
    }
}
