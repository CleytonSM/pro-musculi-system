package com.cleyton.promusculisystem.services;

import com.cleyton.promusculisystem.helper.ModelHelper;
import com.cleyton.promusculisystem.model.Authority;
import com.cleyton.promusculisystem.model.User;
import com.cleyton.promusculisystem.model.dto.LoginDto;
import com.cleyton.promusculisystem.model.dto.PaginationDto;
import com.cleyton.promusculisystem.model.dto.RoleDto;
import com.cleyton.promusculisystem.model.dto.UserDto;
import com.cleyton.promusculisystem.repository.UserRepository;
import jakarta.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.cleyton.promusculisystem.helper.ModelHelper.verifyEmptyOptionalEntity;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;
    @Autowired
    private ModelHelper modelHelper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthorityService authorityService;

    public void createUser(UserDto userDto) {
        isEmailAlreadyInUse(userDto.getEmail());

        Authority authority = new Authority(RoleDto.ROLE_USER.toString());

        repository.save(modelHelper.postUserAttributeSetter(userDto, passwordEncoder, authority));
        authorityService.create(authority);
    }
    public void createAdmin(UserDto userDto) {
        isEmailAlreadyInUse(userDto.getEmail());
        Authority authority = new Authority(RoleDto.ROLE_ADMIN.toString());

        repository.save(modelHelper.postUserAttributeSetter(userDto, passwordEncoder, authority));
        authorityService.create(authority);
    }

    public List<User> getUsers(PaginationDto paginationDto) {
        Page<User> users = repository.findAllActive(modelHelper.setupPagination(paginationDto));

        return users.getContent();
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

    public void updateUser(Integer id, UserDto userDto) {
        User user = (User) verifyEmptyOptionalEntity(repository.findById(id));

        repository.save(modelHelper.updateUserAttributeSetter(user, userDto, passwordEncoder));
    }

    public void patchUser(Integer id, UserDto userDto) {
        User user = (User) verifyEmptyOptionalEntity(repository.findById(id));

        repository.save(modelHelper.patchUserAttributeSetter(user, userDto, passwordEncoder));
    }

    public void deleteUser(Integer id) {
        User user = (User) verifyEmptyOptionalEntity(repository.findById(id));

        repository.save(modelHelper.deleteUserAttributeSetter(user));
    }

    public void reactiveUser(Integer id, UserDto userDto) {
        User user = (User) verifyEmptyOptionalEntity(repository.findById(id));

        repository.save(modelHelper.reactiveUserAttributeSetter(user, userDto));
    }



    private void isEmailAlreadyInUse(String email) {
        if(repository.findByEmail(email).isPresent()) {
            throw new EntityExistsException("This email is already in use!");
        }
    }
}
