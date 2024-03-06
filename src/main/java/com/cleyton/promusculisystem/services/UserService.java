package com.cleyton.promusculisystem.services;

import com.cleyton.promusculisystem.helper.ModelMapperHelper;
import com.cleyton.promusculisystem.model.Authority;
import com.cleyton.promusculisystem.model.User;
import com.cleyton.promusculisystem.model.dto.PaginationDto;
import com.cleyton.promusculisystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;
    @Autowired
    private ModelMapperHelper modelMapperHelper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User createAdmin(User user) {
        // TODO create email validation filter
        isEmailAlreadyInUse(user.getEmail());

        Authority authority = new Authority("ROLE_ADMIN", user);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        user.setAuthorities(authoritySet(authority));

        return repository.save(user);
    }

    public User createUser(User user) {
        isEmailAlreadyInUse(user.getEmail());
        // TODO create email validation filter

        Authority authority = new Authority("ROLE_USER", user);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        user.setAuthorities(authoritySet(authority));

        return repository.save(user);
    }

    public Stream<User> getUsers(PaginationDto paginationDto) {
       Pageable pageable = Pageable.ofSize(paginationDto.getPageSize()).withPage(paginationDto.getPageNumber());
       return repository.findAll(pageable).stream();
    }

    private void isEmailAlreadyInUse(String email) {
        if(repository.findByEmail(email).isPresent()) {
            throw new RuntimeException("This email is already in use!");
        }
    }

    private Set<Authority> authoritySet(Authority authority) {
        Set<Authority> authorities = new HashSet<>();
        authorities.add(authority);
        return authorities;
    }
}
