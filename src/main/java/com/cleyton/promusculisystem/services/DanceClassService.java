package com.cleyton.promusculisystem.services;

import com.cleyton.promusculisystem.exceptions.DanceClassAlreadyUsingThisDate;
import com.cleyton.promusculisystem.helper.ModelAttributeSetterHelper;
import com.cleyton.promusculisystem.model.DanceClass;
import com.cleyton.promusculisystem.model.dto.DanceClassDto;
import com.cleyton.promusculisystem.model.dto.PaginationDto;
import com.cleyton.promusculisystem.model.response.PageResponse;
import com.cleyton.promusculisystem.repository.DanceClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.cleyton.promusculisystem.helper.ModelAttributeSetterHelper.verifyOptionalEntity;

@Service
public class DanceClassService {

    @Autowired
    private DanceClassRepository repository;
    @Autowired
    private ModelAttributeSetterHelper modelAttributeSetterHelper;

    private void save(DanceClass danceClass) {
        repository.save(danceClass);
    }

    public void createDanceClass(DanceClassDto danceClassDto) {
        Optional<DanceClass> optionalDanceClass = repository
                .findByStartAndEnd(danceClassDto.getStart(), danceClassDto.getEnd());

        if(optionalDanceClass.isPresent()) {
            throw new DanceClassAlreadyUsingThisDate("There is already a dance class scheduled for this time");
        }

        save(modelAttributeSetterHelper.postDanceClassAttributeSetter(danceClassDto));
    }

    public DanceClass findDanceClassByName(String name) {
        return verifyOptionalEntity(repository.findByName(name));
    }

    public PageResponse<?> findAllDanceClassesByInstructor(String instructorName, PaginationDto paginationDto) {
        Page<DanceClass> danceClasses = repository.findAllByInstructorName(instructorName,
                modelAttributeSetterHelper.setupPageable(paginationDto));

        return modelAttributeSetterHelper.setupPageResponse(danceClasses);
    }

    public void updateDanceClassById(Integer id, DanceClassDto danceClassDto) {
        DanceClass danceClass = findDanceClassById(id);
        dateAlreadyInUse(danceClassDto, danceClass);

        save(modelAttributeSetterHelper.updateDanceClassAttributeSetter(danceClass, danceClassDto));
    }

    public void patchDanceClassById(Integer id, DanceClassDto danceClassDto) {
        DanceClass danceClass = findDanceClassById(id);
        dateAlreadyInUse(danceClassDto, danceClass);

        save(modelAttributeSetterHelper.patchDanceClassAttributeSetter(danceClass, danceClassDto));
    }

    public DanceClass findDanceClassById(Integer id) {
        return verifyOptionalEntity(repository.findById(id));
    }

    public void dateAlreadyInUse(DanceClassDto danceClassDto, DanceClass danceClass) {

        if(danceClassDto.getStart() != danceClass.getStart() && danceClassDto.getEnd() != danceClass.getEnd()) {
            Optional<DanceClass> optionalDanceClass = repository
                    .findByStartAndEnd(danceClassDto.getStart(), danceClassDto.getEnd());

            if(optionalDanceClass.isPresent()) {
                throw new DanceClassAlreadyUsingThisDate("There is already a dance class scheduled for this time");
            }
        }
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
