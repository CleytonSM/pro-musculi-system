package com.cleyton.promusculisystem.services;


import com.cleyton.promusculisystem.helper.ModelAttributeSetterHelper;
import com.cleyton.promusculisystem.model.Instructor;
import com.cleyton.promusculisystem.model.dto.InstructorDto;
import com.cleyton.promusculisystem.repository.InstructorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.cleyton.promusculisystem.helper.ModelAttributeSetterHelper.isEntityAlreadyInUse;

@Service
public class InstructorService {

    @Autowired
    private InstructorRepository repository;
    @Autowired
    private ModelAttributeSetterHelper modelAttributeSetterHelper;

    public void save(Instructor instructor) {
        repository.save(instructor);
    }

    public void createInstructor(InstructorDto instructorDto) {
        isEntityAlreadyInUse(repository.findByCpf(instructorDto.getCpf()));
        save(modelAttributeSetterHelper.postInstructorAttributeSetter(instructorDto));
    }
}
