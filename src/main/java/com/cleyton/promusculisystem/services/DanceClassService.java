package com.cleyton.promusculisystem.services;

import com.cleyton.promusculisystem.exceptions.DanceClassAlreadyUsingThisDate;
import com.cleyton.promusculisystem.helper.ModelAttributeSetterHelper;
import com.cleyton.promusculisystem.model.DanceClass;
import com.cleyton.promusculisystem.model.dto.DanceClassDto;
import com.cleyton.promusculisystem.repository.DanceClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DanceClassService {

    @Autowired
    private DanceClassRepository danceClassRepository;
    @Autowired
    private ModelAttributeSetterHelper modelAttributeSetterHelper;

    private void save(DanceClass danceClass) {
        danceClassRepository.save(danceClass);
    }

    public void createDanceClass(DanceClassDto danceClassDto) {
        Optional<DanceClass> optionalDanceClass = danceClassRepository
                .findByStartAndEnd(danceClassDto.getStart(), danceClassDto.getEnd());

        if(optionalDanceClass.isPresent()) {
            throw new DanceClassAlreadyUsingThisDate("There is already a dance class scheduled for this time");
        }

        save(modelAttributeSetterHelper.postDanceClassAttributeSetter(danceClassDto));
    }
}
