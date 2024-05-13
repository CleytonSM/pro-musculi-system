package com.cleyton.promusculisystem.services;

import com.cleyton.promusculisystem.helper.ModelAttributeSetterHelper;
import com.cleyton.promusculisystem.model.WorkoutClass;
import com.cleyton.promusculisystem.model.dto.WorkoutClassDTO;
import com.cleyton.promusculisystem.repository.WorkoutClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.cleyton.promusculisystem.helper.ModelAttributeSetterHelper.isEntityAlreadyInUse;

@Service
public class WorkoutClassService {

    @Autowired
    private WorkoutClassRepository repository;
    @Autowired
    private ModelAttributeSetterHelper modelAttributeSetterHelper;

    public void save(WorkoutClass workoutClass) {
        repository.save(workoutClass);
    }

    public void createWorkoutClass(WorkoutClassDTO workoutClassDTO) {
        isEntityAlreadyInUse(repository.findByDateClass(workoutClassDTO.getDateClass()));

        save(modelAttributeSetterHelper.postWorkoutClassAttributeSetter(workoutClassDTO));
    }
}
