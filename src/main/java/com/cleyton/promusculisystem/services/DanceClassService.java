package com.cleyton.promusculisystem.services;

import com.cleyton.promusculisystem.exceptions.EntityAlreadyExistsException;
import com.cleyton.promusculisystem.helper.ModelAttributeSetterHelper;
import com.cleyton.promusculisystem.model.DanceClass;
import com.cleyton.promusculisystem.model.dto.DanceClassDTO;
import com.cleyton.promusculisystem.model.dto.PaginationDTO;
import com.cleyton.promusculisystem.model.response.PageResponse;
import com.cleyton.promusculisystem.repository.DanceClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.cleyton.promusculisystem.helper.ModelAttributeSetterHelper.isEntityAlreadyInUse;
import static com.cleyton.promusculisystem.helper.ModelAttributeSetterHelper.verifyOptionalEntity;

@Service
public class DanceClassService {

    @Autowired
    private DanceClassRepository repository;
    @Autowired
    private ModelAttributeSetterHelper modelAttributeSetterHelper;

    public void save(DanceClass danceClass) {
        repository.save(danceClass);
    }

    public void createDanceClass(DanceClassDTO danceClassDTO) {
        isEntityAlreadyInUse(repository
                .findByStartAndEnd(danceClassDTO.getStart(), danceClassDTO.getEnd()));

        save(modelAttributeSetterHelper.postDanceClassAttributeSetter(danceClassDTO));
    }

    public PageResponse<?> findAllDanceClassesByInstructor(String instructorName, PaginationDTO paginationDTO) {
        Page<DanceClass> danceClasses = repository.findAllByInstructorName(instructorName,
                modelAttributeSetterHelper.setupPageable(paginationDTO));

        return modelAttributeSetterHelper.setupPageResponse(danceClasses);
    }

    public PageResponse<?> findAllInactiveDanceClassesByInstructor(String instructorName, PaginationDTO paginationDTO) {
        Page<DanceClass> danceClasses = repository.findAllInactiveByInstructor(instructorName, modelAttributeSetterHelper.setupPageable(paginationDTO));

        return modelAttributeSetterHelper.setupPageResponse(danceClasses);
    }

    public void updateDanceClassById(Integer id, DanceClassDTO danceClassDTO) {
        DanceClass danceClass = dateAlreadyInUse(danceClassDTO, findDanceClassById(id));

        save(modelAttributeSetterHelper.updateDanceClassAttributeSetter(danceClass, danceClassDTO));
    }

    public void patchDanceClassById(Integer id, DanceClassDTO danceClassDTO) {
        DanceClass danceClass = dateAlreadyInUse(danceClassDTO, findDanceClassById(id));

        save(modelAttributeSetterHelper.patchDanceClassAttributeSetter(danceClass, danceClassDTO));
    }

    public DanceClass findDanceClassById(Integer id) {
        return verifyOptionalEntity(repository.findById(id));
    }

    public DanceClass findInactiveDanceClassById(Integer id) {
        return verifyOptionalEntity(repository.findInactiveById(id));
    }

    public DanceClass dateAlreadyInUse(DanceClassDTO danceClassDTO, DanceClass danceClass) {

        if(danceClassDTO.getStart() != danceClass.getStart() && danceClassDTO.getEnd() != danceClass.getEnd()) {
            Optional<DanceClass> danceClassConflict = repository
                    .findByStartAndEnd(danceClassDTO.getStart(), danceClassDTO.getEnd());

            if(danceClassConflict.isPresent()) {
                throw new EntityAlreadyExistsException("There is already a dance class scheduled at this time");
            }
        }
        return danceClass;
    }

    public void deleteDanceClassById(Integer id) {
        DanceClass danceClass = verifyOptionalEntity(repository.findById(id));

        save(modelAttributeSetterHelper.deleteDanceClassAttributeSetter(danceClass));
    }

    public void reactivateDanceClassById(Integer id) {
        DanceClass danceClass = verifyOptionalEntity(repository.findById(id));

        save(modelAttributeSetterHelper.reactivateDanceClassAttributeSetter(danceClass));
    }
}
