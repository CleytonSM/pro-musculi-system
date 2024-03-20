package com.cleyton.promusculisystem.services;

import com.cleyton.promusculisystem.helper.ModelHelper;
import com.cleyton.promusculisystem.model.User;
import com.cleyton.promusculisystem.model.dto.LoginDto;
import com.cleyton.promusculisystem.model.dto.PaginationDto;
import com.cleyton.promusculisystem.model.dto.RoleDto;
import com.cleyton.promusculisystem.model.dto.UserDto;
import com.cleyton.promusculisystem.repository.UserRepository;
import jakarta.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Stream;

import static com.cleyton.promusculisystem.helper.ModelHelper.verifyEmptyOptionalEntity;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;
    @Autowired
    private ModelHelper modelHelper;
    @Autowired
    private PasswordEncoder passwordEncoder;


    public void createUser(UserDto userDto) {
        // TODO create email validation filter
        isEmailAlreadyInUse(userDto.getEmail());

        repository.save(modelHelper.postUserAttributeSetter(userDto, passwordEncoder, RoleDto.ROLE_USER.toString()));
    }
    public void createAdmin(UserDto userDto) {
        // TODO create email validation filter
        isEmailAlreadyInUse(userDto.getEmail());

        repository.save(modelHelper.postUserAttributeSetter(userDto, passwordEncoder, RoleDto.ROLE_ADMIN.toString()));
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

    public User updateUser(Integer id, UserDto userDto) {
        User user = (User) verifyEmptyOptionalEntity(repository.findById(id));

        return repository.save(modelHelper.updateUserAttributeSetter(user, userDto, passwordEncoder));
    }

    public User patchUser(Integer id, UserDto userDto) {
        User user = (User) verifyEmptyOptionalEntity(repository.findById(id));

        return repository.save(modelHelper.patchUserAttributeSetter(user, userDto, passwordEncoder));
    }

    private void isEmailAlreadyInUse(String email) {
        if(repository.findByEmail(email).isPresent()) {
            throw new EntityExistsException("This email is already in use!");
        }
    }
}
