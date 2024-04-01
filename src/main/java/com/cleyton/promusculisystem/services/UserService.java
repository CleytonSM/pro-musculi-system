package com.cleyton.promusculisystem.services;

import com.cleyton.promusculisystem.helper.ModelHelper;
import com.cleyton.promusculisystem.model.Authority;
import com.cleyton.promusculisystem.model.User;
import com.cleyton.promusculisystem.model.dto.LoginDto;
import com.cleyton.promusculisystem.model.dto.PageResponse;
import com.cleyton.promusculisystem.model.dto.PaginationDto;
import com.cleyton.promusculisystem.model.dto.RoleDto;
import com.cleyton.promusculisystem.model.dto.UserDto;
import com.cleyton.promusculisystem.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.cleyton.promusculisystem.helper.ModelHelper.isEntityAlreadyInUse;
import static com.cleyton.promusculisystem.helper.ModelHelper.verifyOptionalEntity;

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

    public void save(User user) {
        repository.save(user);
    }

    public void createUser(UserDto userDto) {
        isEntityAlreadyInUse(repository.findByEmail(userDto.getEmail()));

        Authority authority = new Authority(RoleDto.ROLE_USER.toString());

        save(modelHelper.postUserAttributeSetter(userDto, passwordEncoder, authority));
        authorityService.create(authority);
    }
    public void createAdmin(UserDto userDto) {
        isEntityAlreadyInUse(repository.findByEmail(userDto.getEmail()));
        Authority authority = new Authority(RoleDto.ROLE_ADMIN.toString());

        repository.save(modelHelper.postUserAttributeSetter(userDto, passwordEncoder, authority));
        authorityService.create(authority);
    }

    public PageResponse<User> getUsers(PaginationDto paginationDto) {
        Page<User> users = repository.findAllActive(modelHelper.setupPageable(paginationDto));

        return modelHelper.setupPageResponse(users);
    }

    public PageResponse<User> getInactiveUsers(PaginationDto paginationDto) {
        Page<User> users = repository.findAllInactive(modelHelper.setupPageable(paginationDto));

        return modelHelper.setupPageResponse(users);
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
        //TODO verify isEntityAlreadyInUse logistic here
        User user = verifyOptionalEntity(repository.findById(id));

        save(modelHelper.updateUserAttributeSetter(user, userDto, passwordEncoder));
    }

    public void patchUser(Integer id, UserDto userDto) {
        //TODO verify isEntityAlreadyInUse logistic here
        User user = verifyOptionalEntity(repository.findById(id));

        save(modelHelper.patchUserAttributeSetter(user, userDto, passwordEncoder));
    }

    public void deleteUser(Integer id) {
        //TODO verify isEntityAlreadyInUse logistic here
        User user = verifyOptionalEntity(repository.findById(id));

        save(modelHelper.deleteUserAttributeSetter(user));
    }

    public void reactiveUser(Integer id) {
        User user = verifyOptionalEntity(repository.findById(id));

        save(modelHelper.reactivateUserAttributeSetter(user));
    }

    public User findUserByEmail(String email) {
        Optional<User> optionalUser = repository.findByEmail(email);

        if(optionalUser.isEmpty()) {
            throw new EntityNotFoundException("This user doesn't exists");
        }

        return optionalUser.get();
    }
}
