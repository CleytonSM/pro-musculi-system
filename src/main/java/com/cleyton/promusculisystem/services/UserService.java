package com.cleyton.promusculisystem.services;

import com.cleyton.promusculisystem.config.UserAuthenticationProvider;
import com.cleyton.promusculisystem.helper.ModelAttributeSetterHelper;
import com.cleyton.promusculisystem.model.Authority;
import com.cleyton.promusculisystem.model.User;
import com.cleyton.promusculisystem.model.dto.LoginDTO;
import com.cleyton.promusculisystem.model.dto.PaginationDTO;
import com.cleyton.promusculisystem.model.dto.RoleDTO;
import com.cleyton.promusculisystem.model.dto.UserDTO;
import com.cleyton.promusculisystem.model.response.PageResponse;
import com.cleyton.promusculisystem.repository.UserRepository;
import com.cleyton.promusculisystem.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.cleyton.promusculisystem.helper.ModelAttributeSetterHelper.isEntityAlreadyInUse;
import static com.cleyton.promusculisystem.helper.ModelAttributeSetterHelper.verifyOptionalEntity;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;
    @Autowired
    private ModelAttributeSetterHelper modelAttributeSetterHelper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthorityService authorityService;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private UserAuthenticationProvider userAuthenticationProvider;

    public void save(User user) {
        repository.save(user);
    }

    public void createUser(UserDTO userDto) {
        isEntityAlreadyInUse(repository.findByEmail(userDto.getEmail()));

        Authority authority = new Authority(RoleDTO.ROLE_USER.toString());

        save(modelAttributeSetterHelper.postUserAttributeSetter(userDto, passwordEncoder, authority));
        authorityService.create(authority);
    }
    public void createAdmin(UserDTO userDto) {
        isEntityAlreadyInUse(repository.findByEmail(userDto.getEmail()));
        Authority authority = new Authority(RoleDTO.ROLE_ADMIN.toString());

        repository.save(modelAttributeSetterHelper.postUserAttributeSetter(userDto, passwordEncoder, authority));
        authorityService.create(authority);
    }

    public PageResponse<User> getUsers(PaginationDTO paginationDto) {
        Page<User> users = repository.findAllActive(modelAttributeSetterHelper.setupPageable(paginationDto));

        return modelAttributeSetterHelper.setupPageResponse(users);
    }

    public PageResponse<User> getInactiveUsers(PaginationDTO paginationDto) {
        Page<User> users = repository.findAllInactive(modelAttributeSetterHelper.setupPageable(paginationDto));

        return modelAttributeSetterHelper.setupPageResponse(users);
    }

    public String login(LoginDTO loginDto) {
        // TODO check a better way to implement this login section
        User user = verifyOptionalEntity(repository.findByEmail(loginDto.getEmail()));

        Authentication authentication = new UsernamePasswordAuthenticationToken(user,user.getPassword());

        userAuthenticationProvider.authenticate(authentication);

        return jwtTokenProvider.createToken();
    }

    public void updateUser(Integer id, UserDTO userDto) {
        User user = verifyOptionalEntity(repository.findById(id));
        if(!user.getEmail().equals(userDto.getEmail())) {
            isEntityAlreadyInUse(repository.findByEmail(userDto.getEmail()));
        }

        save(modelAttributeSetterHelper.updateUserAttributeSetter(user, userDto, passwordEncoder));
    }

    public void patchUser(Integer id, UserDTO userDto) {
        User user = verifyOptionalEntity(repository.findById(id));
        if(!user.getEmail().equals(userDto.getEmail())) {
            isEntityAlreadyInUse(repository.findByEmail(userDto.getEmail()));
        }

        save(modelAttributeSetterHelper.patchUserAttributeSetter(user, userDto, passwordEncoder));
    }

    public void deleteUser(Integer id) {
        User user = verifyOptionalEntity(repository.findById(id));

        save(modelAttributeSetterHelper.deleteUserAttributeSetter(user));
    }

    public void reactiveUser(Integer id) {
        User user = verifyOptionalEntity(repository.findById(id));

        save(modelAttributeSetterHelper.reactivateUserAttributeSetter(user));
    }

    public User findUserByEmail(String email) {
        return verifyOptionalEntity(repository.findByEmail(email));
    }
}
