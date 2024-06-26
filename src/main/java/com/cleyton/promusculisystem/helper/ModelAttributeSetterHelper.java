package com.cleyton.promusculisystem.helper;

import com.cleyton.promusculisystem.exceptions.EntityAlreadyExistsException;
import com.cleyton.promusculisystem.exceptions.NotFoundException;
import com.cleyton.promusculisystem.model.Authority;
import com.cleyton.promusculisystem.model.Client;
import com.cleyton.promusculisystem.model.DanceClass;
import com.cleyton.promusculisystem.model.GymPlan;
import com.cleyton.promusculisystem.model.Instructor;
import com.cleyton.promusculisystem.model.User;
import com.cleyton.promusculisystem.model.WorkoutClass;
import com.cleyton.promusculisystem.model.dto.ClientDTO;
import com.cleyton.promusculisystem.model.dto.DanceClassDTO;
import com.cleyton.promusculisystem.model.dto.GymPlanDTO;
import com.cleyton.promusculisystem.model.dto.InstructorDTO;
import com.cleyton.promusculisystem.model.dto.PaginationDTO;
import com.cleyton.promusculisystem.model.dto.UserDTO;
import com.cleyton.promusculisystem.model.dto.WorkoutClassDTO;
import com.cleyton.promusculisystem.model.response.PageResponse;
import com.cleyton.promusculisystem.services.AuthorityService;
import com.cleyton.promusculisystem.services.ClientService;
import com.cleyton.promusculisystem.services.GymPlanService;
import com.cleyton.promusculisystem.services.InstructorService;
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
public class ModelAttributeSetterHelper {

    @Autowired
    private AuthorityService authorityService;
    @Autowired
    private GymPlanService gymPlanService;
    @Autowired
    private InstructorService instructorService;
    @Autowired
    private ClientService clientService;

    public User postUserAttributeSetter(UserDTO userDto, PasswordEncoder passwordEncoder, Authority authority) {
        User user = new User();

        authority.setUser(user);
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setActive(Boolean.TRUE);
        user.setCreatedAt(LocalDateTime.now());
        user.setAuthorities(authoritySetup(authority));

        return user;
    }

    public User updateUserAttributeSetter(User user, UserDTO userDto, PasswordEncoder passwordEncoder) {
        Authority authority = authorityService.update(user, userDto);

        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setAuthorities(authoritySetup(authority));

        return user;
    }

    public User patchUserAttributeSetter(User user, UserDTO userDto, PasswordEncoder passwordEncoder) {
      user.setEmail(Optional.ofNullable(userDto.getEmail()).orElse(user.getEmail()));
      user.setPassword((Optional.ofNullable(passwordEncoder.encode(userDto.getPassword())).orElse(user.getPassword())));
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

    public Client postClientAttributeSetter(ClientDTO clientDto) {
        Client client = new Client();
        client.setGymPlan(gymPlanService.findByName(clientDto.getGymPlanName()));
        client.setName(clientDto.getName());
        client.setEmail(clientDto.getEmail());
        client.setPhone(clientDto.getPhone());
        client.setActive(Boolean.TRUE);
        client.setCreatedAt(LocalDateTime.now());

        return client;
    }

    public Client updateClientAttributeSetter(Client client, ClientDTO clientDto) {
        client.setName(clientDto.getName());
        client.setEmail(clientDto.getEmail());
        client.setPhone(clientDto.getPhone());
        client.setGymPlan(gymPlanService.findByName(clientDto.getGymPlanName()));

        return client;
    }

    public Client patchClientAttributeSetter(Client client, ClientDTO clientDto) {
        client.setName(Optional.ofNullable(clientDto.getName()).orElse(client.getName()));
        client.setEmail(Optional.ofNullable(clientDto.getEmail()).orElse(client.getEmail()));
        client.setPhone(Optional.ofNullable(client.getPhone()).orElse(client.getPhone()));
        client.setGymPlan(clientDto.getGymPlanName() == null
                ? client.getGymPlan() : gymPlanService.findByName(clientDto.getGymPlanName()));

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

    public GymPlan postGymPlanAttributeSetter (GymPlanDTO gymPlanDto) {
        GymPlan gymPlan = new GymPlan();

        gymPlan.setName(gymPlanDto.getName());
        gymPlan.setPrice(gymPlanDto.getPrice());
        gymPlan.setDuration(gymPlanDto.getDuration());

        return gymPlan;
    }

    public GymPlan updateGymPlanAttributeSetter(GymPlan gymPlan, GymPlanDTO gymPlanDto) {
        gymPlan.setName(gymPlanDto.getName());
        gymPlan.setPrice(gymPlanDto.getPrice());
        gymPlan.setDuration(gymPlanDto.getDuration());
        return gymPlan;
    }

    public GymPlan patchGymPlanAttributeSetter(GymPlan gymPlan, GymPlanDTO gymPlanDto) {
        gymPlan.setName(Optional.ofNullable(gymPlanDto.getName()).orElse(gymPlan.getName()));
        gymPlan.setPrice(Optional.ofNullable(gymPlanDto.getPrice()).orElse(gymPlan.getPrice()));
        gymPlan.setDuration(Optional.ofNullable(gymPlanDto.getDuration()).orElse(gymPlan.getDuration()));
        return gymPlan;
    }

    public DanceClass postDanceClassAttributeSetter(DanceClassDTO danceClassDto) {
        DanceClass danceClass = new DanceClass();

        danceClass.setInstructor(instructorService.findInstructorByName(danceClassDto.getInstructorName()));
        danceClass.setClient(clientService.findClientByName(danceClassDto.getClientEmail()));
        danceClass.setName(danceClassDto.getName());
        danceClass.setStart(danceClassDto.getStart());
        danceClass.setEnd(danceClassDto.getEnd());
        danceClass.setDescription(danceClassDto.getDescription());
        danceClass.setCreatedAt(LocalDateTime.now());

        return danceClass;
    }

    public DanceClass updateDanceClassAttributeSetter(DanceClass danceClass, DanceClassDTO danceClassDto) {
        danceClass.setInstructor(instructorService.findInstructorByName(danceClassDto.getInstructorName()));
        danceClass.setClient(clientService.findClientByEmail(danceClassDto.getClientEmail()));
        danceClass.setName(danceClassDto.getName());
        danceClass.setStart(danceClassDto.getStart());
        danceClass.setEnd(danceClassDto.getEnd());
        danceClass.setDescription(danceClass.getDescription());

        return danceClass;
    }


    public DanceClass patchDanceClassAttributeSetter(DanceClass danceClass, DanceClassDTO danceClassDto) {
        danceClass.setInstructor(danceClassDto.getInstructorName() == null
                    ? danceClass.getInstructor() : instructorService.findInstructorByName(danceClassDto.getInstructorName()));

        danceClass.setClient(danceClassDto.getClientEmail() == null
                ? danceClass.getClient() : clientService.findClientByEmail(danceClassDto.getClientEmail()));
        danceClass.setName(Optional.ofNullable(danceClassDto.getName())
                .orElse(danceClass.getName()));
        danceClass.setStart(Optional.ofNullable(danceClassDto.getStart())
                .orElse(danceClass.getStart()));
        danceClass.setEnd(Optional.ofNullable(danceClassDto.getEnd())
                .orElse(danceClass.getEnd()));
        danceClass.setDescription(Optional.ofNullable(danceClassDto.getDescription())
                .orElse(danceClass.getDescription()));

        return danceClass;
    }


    public DanceClass deleteDanceClassAttributeSetter(DanceClass danceClass) {
        danceClass.setActive(Boolean.FALSE);

        return danceClass;
    }


    public DanceClass reactivateDanceClassAttributeSetter(DanceClass danceClass) {
        danceClass.setActive(Boolean.TRUE);

        return danceClass;
    }

    public Instructor postInstructorAttributeSetter(InstructorDTO instructorDto) {
        Instructor instructor = new Instructor();

        instructor.setName(instructorDto.getName());
        instructor.setSalary(instructorDto.getSalary());
        instructor.setCpf(instructorDto.getCpf());
        instructor.setActive(Boolean.TRUE);
        instructor.setPhone(instructorDto.getPhone());
        instructor.setCreatedAt(LocalDateTime.now());

        return instructor;
    }

    public Instructor updateInstructorAttributeSetter(Instructor instructor, InstructorDTO instructorDto) {
        instructor.setName(instructorDto.getName());
        instructor.setSalary(instructorDto.getSalary());
        instructor.setCpf(instructorDto.getCpf());
        instructor.setPhone(instructorDto.getPhone());

        return instructor;
    }

    public Instructor patchInstructorAttributeSetter(Instructor instructor, InstructorDTO instructorDto) {
        instructor.setName(Optional.ofNullable(instructorDto.getName()).orElse(instructor.getName()));
        instructor.setSalary(Optional.ofNullable(instructorDto.getSalary()).orElse(instructor.getSalary()));
        instructor.setCpf(Optional.ofNullable(instructorDto.getCpf()).orElse(instructor.getCpf()));
        instructor.setPhone(Optional.ofNullable(instructorDto.getPhone()).orElse(instructor.getPhone()));

        return instructor;
    }

    public Instructor deleteInstructorAttributeSetter(Instructor instructor) {
        instructor.setActive(Boolean.FALSE);

        return instructor;
    }

    public Instructor reactivateInstructorAttributeSetter(Instructor instructor) {
        instructor.setActive(Boolean.TRUE);

        return instructor;
    }


    public WorkoutClass postWorkoutClassAttributeSetter(WorkoutClassDTO workoutClassDTO) {
        WorkoutClass workoutClass = new WorkoutClass();

        workoutClass.setClient(clientService.findClientByEmail(workoutClassDTO.getClientEmail()));
        workoutClass.setInstructor(instructorService.findInstructorByName(workoutClassDTO.getInstructorName()));
        workoutClass.setName(workoutClassDTO.getName());
        workoutClass.setDateClass(workoutClassDTO.getDateClass());
        workoutClass.setActive(Boolean.TRUE);
        workoutClass.setDescription(workoutClassDTO.getDescription());
        workoutClass.setCreatedAt(LocalDateTime.now());

        return workoutClass;
    }


    public WorkoutClass updateWorkoutClassAttributeSetter(WorkoutClass workoutClass, WorkoutClassDTO workoutClassDTO) {
        workoutClass.setClient(clientService.findClientByEmail(workoutClassDTO.getClientEmail()));
        workoutClass.setInstructor(instructorService.findInstructorByName(workoutClassDTO.getInstructorName()));
        workoutClass.setName(workoutClassDTO.getName());
        workoutClass.setDateClass(workoutClassDTO.getDateClass());
        workoutClass.setDescription(workoutClassDTO.getDescription());

        return workoutClass;
    }

    public WorkoutClass patchWorkoutClassAttributeSetter(WorkoutClass workoutClass, WorkoutClassDTO workoutClassDTO) {
        workoutClass.setClient(workoutClassDTO.getClientEmail() == null
                ? workoutClass.getClient() : clientService.findClientByEmail(workoutClassDTO.getClientEmail()));
        workoutClass.setInstructor(workoutClassDTO.getInstructorName() == null
                ? workoutClass.getInstructor() : instructorService.findInstructorByName(workoutClassDTO.getInstructorName()));

        workoutClass.setName(Optional.ofNullable(workoutClassDTO.getName())
                .orElse(workoutClass.getName()));
        workoutClass.setDateClass(Optional.ofNullable(workoutClassDTO.getDateClass())
                .orElse(workoutClass.getDateClass()));
        workoutClass.setDescription(Optional.ofNullable(workoutClassDTO.getDescription())
                .orElse(workoutClassDTO.getDescription()));

        return workoutClass;
    }

    public WorkoutClass deleteWorkoutClassAttributeSetter(WorkoutClass workoutClass) {
        workoutClass.setActive(Boolean.FALSE);

        return workoutClass;
    }

    public WorkoutClass reactivateWorkoutClassAttributeSetter(WorkoutClass workoutClass) {
        workoutClass.setActive(Boolean.TRUE);

        return workoutClass;
    }

    private Set<Authority> authoritySetup(Authority authority) {
        Set<Authority> authorities = new HashSet<>();
        authorities.add(authority);

        return authorities;
    }

    public static <T> T verifyOptionalEntity(Optional<T> optionalT) {
        if(optionalT.isEmpty()) {
            throw new NotFoundException("This entity doesn't exist");
        }

        return optionalT.get();
    }

    public static <T> void isEntityAlreadyInUse(Optional<T> optionalT) {
        if(optionalT.isPresent()) {
            throw new EntityAlreadyExistsException("This Entity already exists");
        }
    }

    public Pageable setupPageable(PaginationDTO paginationDto) {
        Sort sort = Sort.by(Sort.Direction.valueOf(paginationDto.getSortType()), paginationDto.getSortBy());
        return PageRequest.of(paginationDto.getPageNumber(), paginationDto.getPageSize(), sort);
    }

    public <T> PageResponse<T> setupPageResponse(Page<T> page) {
        PageResponse<T> pageResponse = new PageResponse<>();

        pageResponse.setTotal(page.getTotalElements());
        pageResponse.setRecords(page.getContent());

        return pageResponse;
    }
}
