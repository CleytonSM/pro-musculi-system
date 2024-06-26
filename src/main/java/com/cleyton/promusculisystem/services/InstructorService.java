package com.cleyton.promusculisystem.services;


import com.cleyton.promusculisystem.helper.ModelAttributeSetterHelper;
import com.cleyton.promusculisystem.model.Instructor;
import com.cleyton.promusculisystem.model.dto.InstructorDTO;
import com.cleyton.promusculisystem.repository.InstructorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.cleyton.promusculisystem.helper.ModelAttributeSetterHelper.isEntityAlreadyInUse;
import static com.cleyton.promusculisystem.helper.ModelAttributeSetterHelper.verifyOptionalEntity;

@Service
public class InstructorService {

    @Autowired
    private InstructorRepository repository;
    @Autowired
    private ModelAttributeSetterHelper modelAttributeSetterHelper;

    public void save(Instructor instructor) {
        repository.save(instructor);
    }

    public void createInstructor(InstructorDTO instructorDto) {
        isEntityAlreadyInUse(repository.findByCpf(instructorDto.getCpf()));
        save(modelAttributeSetterHelper.postInstructorAttributeSetter(instructorDto));
    }

    public Instructor findInstructorByCpf(String cpf) {
        return Optional.of(verifyOptionalEntity(repository.findByCpf(cpf))).orElseThrow();
    }

    public Instructor findInstructorByName(String instructorName) {
        return Optional.of(verifyOptionalEntity(repository.findByName(instructorName))).orElseThrow();
    }

    public void updateInstructorByCpf(String cpf, InstructorDTO instructorDto) {
        Instructor instructor = verifyOptionalEntity(repository.findByCpf(cpf));
        if(!instructor.getCpf().equals(instructorDto.getCpf())) {
            isEntityAlreadyInUse(repository.findByCpf(instructorDto.getCpf()));
        }

        save(modelAttributeSetterHelper.updateInstructorAttributeSetter(instructor, instructorDto));
    }

    public void patchInstructorByCpf(String cpf, InstructorDTO instructorDto) {
        Instructor instructor = verifyOptionalEntity(repository.findByCpf(cpf));
        if(!instructor.getCpf().equals(instructorDto.getCpf())) {
            isEntityAlreadyInUse(repository.findByCpf(instructorDto.getCpf()));
        }

        save(modelAttributeSetterHelper.patchInstructorAttributeSetter(instructor, instructorDto));
    }

    public void deleteInstructorByCpf(String cpf) {
        Instructor instructor = verifyOptionalEntity(repository.findByCpf(cpf));

        save(modelAttributeSetterHelper.deleteInstructorAttributeSetter(instructor));
    }

    public void reactivateInstructByCpf(String cpf) {
        Instructor instructor = verifyOptionalEntity(repository.findByCpf(cpf));

        save(modelAttributeSetterHelper.reactivateInstructorAttributeSetter(instructor));
    }

}
