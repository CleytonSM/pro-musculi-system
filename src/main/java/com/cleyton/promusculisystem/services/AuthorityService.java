package com.cleyton.promusculisystem.services;

import com.cleyton.promusculisystem.model.Authority;
import com.cleyton.promusculisystem.model.User;
import com.cleyton.promusculisystem.model.dto.UserDto;
import com.cleyton.promusculisystem.repository.AuthorityRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.desktop.AboutHandler;
import java.util.Optional;

@Service
public class AuthorityService {

    @Autowired
    private AuthorityRepository repository;

    public void create(Authority authority) {
        save(authority);
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

    public Authority reactivateAuthority(User user) {
        Optional<Authority> optionalAuthority = repository.findByUser(user.getId());

        if(optionalAuthority.isEmpty()) {
            throw new EntityNotFoundException("This entity doesn't exist");
        }

        Authority authority = optionalAuthority.get();
        authority.setName("ROLE_USER");
        save(authority);

        return authority;
    }

    public Authority delete(User user) {
        Optional<Authority> optionalAuthority = repository.findByUser(user.getId());

        if(optionalAuthority.isEmpty()) {
            throw new EntityNotFoundException("This entity doesn't exist");
        }
        Authority authority = optionalAuthority.get();
        authority.setName("ROLE_DELETED");
        save(authority);

        return authority;
    }

    private void save(Authority authority) {
        repository.save(authority);
    }
}
