package org.export.travel.insurance.core.services;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.export.travel.insurance.core.api.dto.PersonDTO;
import org.export.travel.insurance.core.domain.entities.PersonEntity;
import org.export.travel.insurance.core.repositories.entities.PersonEntityRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class PersonEntityFactory {

    private final PersonEntityRepository personEntityRepository;

    PersonEntity createPersonEntity(PersonDTO personDTO) {
        Optional<PersonEntity> personOpt = personEntityRepository.findBy(
                personDTO.getPersonFirstName(),
                personDTO.getPersonLastName(),
                personDTO.getPersonCode()
        );
        if (personOpt.isPresent()) {
            return personOpt.get();
        } else {
            PersonEntity personEntity = new PersonEntity();
            personEntity.setFirstName(personDTO.getPersonFirstName());
            personEntity.setLastName(personDTO.getPersonLastName());
            personEntity.setPersonCode(personDTO.getPersonCode());
            personEntity.setBirthDate(personDTO.getPersonBirthDate());
            return personEntityRepository.save(personEntity);
        }
    }
}
