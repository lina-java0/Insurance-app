package org.blacklist.core.services;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.blacklist.core.api.command.BlackListedPersonCoreCommand;
import org.blacklist.core.api.command.BlackListedPersonCoreResult;
import org.blacklist.core.api.dto.BlackListedPersonDTO;
import org.blacklist.core.api.dto.ValidationErrorDTO;
import org.blacklist.core.repositories.BlackListedPersonEntityRepository;
import org.blacklist.core.validations.BlackListedPersonValidator;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class BlackListedPersonServiceImpl implements BlackListedPersonService {

    private final BlackListedPersonValidator validator;
    private final BlackListedPersonEntityRepository repository;

    @Override
    public BlackListedPersonCoreResult checkPerson(BlackListedPersonCoreCommand command) {
        List<ValidationErrorDTO> errors = validator.validate(command.getPersonDTO());

        if (errors.isEmpty()) {
            BlackListedPersonDTO personDTO = command.getPersonDTO();
            boolean isBlackListed = repository.findByFirstNameAndLastNameAndPersonCode(
                    personDTO.getPersonFirstName(),
                    personDTO.getPersonLastName(),
                    personDTO.getPersonCode()
            ).isPresent();

            personDTO.setBlackListed(isBlackListed);

            return new BlackListedPersonCoreResult(personDTO);
        }
        return new BlackListedPersonCoreResult(errors);
    }
}
