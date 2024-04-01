package com.cleyton.promusculisystem.helper;

import com.cleyton.promusculisystem.model.Authority;
import com.cleyton.promusculisystem.model.Client;
import com.cleyton.promusculisystem.model.GymPlan;
import com.cleyton.promusculisystem.model.User;
import com.cleyton.promusculisystem.model.dto.ClientDto;
import com.cleyton.promusculisystem.model.dto.GymPlanDto;
import com.cleyton.promusculisystem.model.dto.PaginationDto;
import com.cleyton.promusculisystem.model.dto.PageResponse;
import com.cleyton.promusculisystem.model.dto.UserDto;
import com.cleyton.promusculisystem.services.AuthorityService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

    public Pageable setupPageable(PaginationDto paginationDto) {
        Sort sort = Sort.by(Sort.Direction.valueOf(paginationDto.getSortType()), paginationDto.getSortBy());
        return PageRequest.of(paginationDto.getPageNumber(), paginationDto.getPageSize(), sort);
    };

    public <T> PageResponse<T> setupPageResponse(Page<T> page) {
        PageResponse<T> pageResponse = new PageResponse<>();

        pageResponse.setTotal(page.getTotalElements());
        pageResponse.setRecords(page.getContent());

        return pageResponse;
    }

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

    public Client postClientAttributeSetter(ClientDto clientDto) {
        Client client = new Client();

        client.setName(clientDto.getName());
        client.setEmail(clientDto.getEmail());
        client.setPhone(clientDto.getPhone());
        client.setActive(Boolean.TRUE);
        client.setCreatedAt(LocalDateTime.now());

        return client;
    }

    public Client updateClientAttributeSetter(Client client, ClientDto clientDto) {
        client.setName(clientDto.getName());
        client.setEmail(clientDto.getEmail());
        client.setPhone(clientDto.getPhone());

        return client;
    }

    public Client patchClientAttributeSetter(Client client, ClientDto clientDto) {
        if(clientDto.getName() != null) {
            client.setName(clientDto.getName());
        }
        if(clientDto.getEmail() != null) {
            client.setEmail(clientDto.getEmail());
        }
        if (clientDto.getPhone() != null) {
            client.setPhone(client.getPhone());
        }

        return client;
    }

    public Client deleteClientAttributeSetter(Client client) {
        client.setActive(Boolean.FALSE);

        return client;
    }

    public Client reactivateClientAttributeSetter(Client client) {
        client.setActive(Boolean.TRUE);

        return client;
    }

    public GymPlan postGymPlanAttributeSetter (GymPlanDto gymPlanDto) {
        GymPlan gymPlan = new GymPlan();

        gymPlan.setName(gymPlanDto.getName().toString());
        gymPlan.setPrice(gymPlanDto.getPrice());
        gymPlan.setDuration(gymPlanDto.getDuration());

        return gymPlan;
    }
    private Set<Authority> authoritySetup(Authority authority) {
        Set<Authority> authorities = new HashSet<>();
        authorities.add(authority);

        return authorities;
    }

    public static <T> T verifyOptionalEntity(Optional<T> optionalT) {
        if(optionalT.isEmpty()) {
            throw new EntityNotFoundException("This entity doesn't exists");
        }

        return optionalT.get();
    }

    public static <T> void isEntityAlreadyInUse(Optional<T> optionalT) {
        if(optionalT.isPresent()) {
            throw new EntityExistsException("This Entity already exists");
        }
    }
}
