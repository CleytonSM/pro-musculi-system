package com.cleyton.promusculisystem.services;

import com.cleyton.promusculisystem.exceptions.EntityAlreadyExistsException;
import com.cleyton.promusculisystem.helper.ModelAttributeSetterHelper;
import com.cleyton.promusculisystem.model.WorkoutClass;
import com.cleyton.promusculisystem.model.dto.WorkoutClassDTO;
import com.cleyton.promusculisystem.repository.WorkoutClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.cleyton.promusculisystem.helper.ModelAttributeSetterHelper.isEntityAlreadyInUse;
import static com.cleyton.promusculisystem.helper.ModelAttributeSetterHelper.verifyOptionalEntity;

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

    public WorkoutClass findWorkoutClassById(Integer id) {
        return verifyOptionalEntity(repository.findActiveById(id));
    }

    public WorkoutClass findInactiveWorkoutClassById(Integer id) {
        return verifyOptionalEntity(repository.findInactiveById(id));
    }

    public void updateWorkoutClassById(Integer id, WorkoutClassDTO workoutClassDTO) {
        WorkoutClass workoutClass = dateAlreadyInUse(workoutClassDTO, findWorkoutClassById(id));

        save(modelAttributeSetterHelper.updateWorkoutClassAttributeSetter(workoutClass, workoutClassDTO));
    }

    private WorkoutClass dateAlreadyInUse(WorkoutClassDTO workoutClassDTO, WorkoutClass workoutClass) {

        if(workoutClassDTO.getDateClass() != workoutClass.getDateClass()) {
            Optional<WorkoutClass> workoutClassConflict = repository.findByDateClass(workoutClassDTO.getDateClass());

            if(workoutClassConflict.isPresent()) {
                throw new EntityAlreadyExistsException("There is already a workout class scheduled at this time");
            }
        }

        return workoutClass;
    }
}
