package com.cleyton.promusculisystem.services;

import com.cleyton.promusculisystem.helper.ModelHelper;
import com.cleyton.promusculisystem.model.Authority;
import com.cleyton.promusculisystem.model.User;
import com.cleyton.promusculisystem.model.dto.LoginDto;
import com.cleyton.promusculisystem.model.dto.PaginationDto;
import com.cleyton.promusculisystem.model.dto.RoleDto;
import com.cleyton.promusculisystem.model.dto.UserDto;
import com.cleyton.promusculisystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Stream;

import static com.cleyton.promusculisystem.helper.ModelHelper.userAttributeSetter;
import static com.cleyton.promusculisystem.helper.ModelHelper.verifyEmptyOptionalEntity;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;
    @Autowired
    private ModelHelper modelHelper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User createAdmin(User user) {
        // TODO create email validation filter
        isEmailAlreadyInUse(user.getEmail());

        Authority authority = new Authority(RoleDto.ROLE_ADMIN.toString(), user);

        return repository.save(userAttributeSetter(user, passwordEncoder, authority));
    }

    public User createUser(User user) {
        // TODO create email validation filter
        isEmailAlreadyInUse(user.getEmail());

        Authority authority = new Authority(RoleDto.ROLE_USER.toString(), user);

        return repository.save(userAttributeSetter(user, passwordEncoder, authority));
    }

    public Stream<User> getUsers(PaginationDto paginationDto) {
       Pageable pageable = Pageable.ofSize(paginationDto.getPageSize()).withPage(paginationDto.getPageNumber());
       return repository.findAll(pageable).stream();
    }

    public HttpStatus login(LoginDto loginDto) {
        // TODO check a better way to implement this login section
        Optional<User> optionalUser = repository.findByEmail(loginDto.getEmail());
        if(optionalUser.isEmpty()) {
            return HttpStatus.UNAUTHORIZED;
        }
        if(!passwordEncoder.matches(loginDto.getPassword(), optionalUser.get().getPassword())) {
            return HttpStatus.FORBIDDEN;
        }

        return HttpStatus.OK;
    }

    public User updateUser(Integer id, UserDto dto) {
        User user = (User) verifyEmptyOptionalEntity(repository.findById(id));

        Authority authority = new Authority(dto.getRole().toString(), user);

        return repository.save(userAttributeSetter(user, passwordEncoder, authority));
    }

    private void isEmailAlreadyInUse(String email) {
        if(repository.findByEmail(email).isPresent()) {
            throw new RuntimeException("This email is already in use!");
        }
    }

}
