package com.cleyton.promusculisystem.helper;

import com.cleyton.promusculisystem.model.Authority;
import com.cleyton.promusculisystem.model.User;
import com.cleyton.promusculisystem.model.dto.PaginationDto;
import com.cleyton.promusculisystem.model.dto.UserDto;
import com.cleyton.promusculisystem.services.AuthorityService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    public Pageable setupPagination(PaginationDto paginationDto) {
        Sort sort = Sort.by(Sort.Direction.valueOf(paginationDto.getSortType()), paginationDto.getSortBy());
        return PageRequest.of(paginationDto.getPageNumber(), paginationDto.getPageSize(), sort);
    };

    public User postUserAttributeSetter(UserDto userDto, PasswordEncoder passwordEncoder, Authority authority) {
        User user = new User();

        authority.setUser(user);
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setActive(Boolean.TRUE);
        user.setCreatedAt(LocalDateTime.now());
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

    public User reactivateUserAttributeSetter(User user) {
        Authority authority = authorityService.reactivateAuthority(user);

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
