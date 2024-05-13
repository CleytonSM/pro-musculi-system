package com.cleyton.promusculisystem.services;

import com.cleyton.promusculisystem.model.Authority;
import com.cleyton.promusculisystem.model.User;
import com.cleyton.promusculisystem.model.dto.UserDTO;
import com.cleyton.promusculisystem.repository.AuthorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.cleyton.promusculisystem.helper.ModelAttributeSetterHelper.verifyOptionalEntity;

@Service
public class AuthorityService {

    @Autowired
    private AuthorityRepository repository;

    public void create(Authority authority) {
        save(authority);
    }

    public Authority update(User user, UserDTO userDto) {
        Authority authority = verifyOptionalEntity(repository.findByUser(user.getId()));

        authority.setName(userDto.getRole().toString());
        save(authority);

        return authority;
    }

    public Authority reactivateAuthority(User user) {
        Authority authority = verifyOptionalEntity(repository.findByUser(user.getId()));

        authority.setName("ROLE_USER");
        save(authority);

        return authority;
    }

    public Authority delete(User user) {
        Authority authority = verifyOptionalEntity(repository.findByUser(user.getId()));

        authority.setName("ROLE_DELETED");
        save(authority);

        return authority;
    }

    public void save(Authority authority) {
        repository.save(authority);
    }
}
