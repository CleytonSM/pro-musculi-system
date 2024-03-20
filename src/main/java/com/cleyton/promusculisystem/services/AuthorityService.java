package com.cleyton.promusculisystem.services;

import com.cleyton.promusculisystem.model.Authority;
import com.cleyton.promusculisystem.model.User;
import com.cleyton.promusculisystem.model.dto.UserDto;
import com.cleyton.promusculisystem.repository.AuthorityRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthorityService {

    @Autowired
    private AuthorityRepository repository;

    public Authority create(String role, User user) {
        Authority authority = new Authority(role, user);
        save(authority);

        return authority;
    }

    public Authority update(User user, UserDto userDto) {
        Optional<Authority> optionalAuthority = repository.findByUser(user.getId());

        if(optionalAuthority.isEmpty()) {
            throw new EntityNotFoundException("This entity doesn't exist");
        }
        Authority authority = optionalAuthority.get();
        authority.setName(userDto.getRole().toString());
        save(authority);

        return authority;
    }

    private void save(Authority authority) {
        repository.save(authority);
    }
}
