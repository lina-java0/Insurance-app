package org.export.travel.insurance.core.validations.person;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.export.travel.insurance.core.api.dto.AgreementDTO;
import org.export.travel.insurance.core.api.dto.PersonDTO;
import org.export.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.export.travel.insurance.core.blacklist.BlackListPersonCheckService;
import org.export.travel.insurance.core.util.Placeholder;
import org.export.travel.insurance.core.validations.ValidationErrorFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class PersonBlacklistedCheckValidation extends TravelPersonFieldValidationImpl {

    private final BlackListPersonCheckService blackListPersonCheckService;
    private final ValidationErrorFactory errorFactory;

    @Override
    public Optional<ValidationErrorDTO> validate(AgreementDTO agreementDTO, PersonDTO personDTO) {
        return !personFirstNameIsNullOrBlank(personDTO) &&
                !personLastNameIsNullOrBlank(personDTO) &&
                !personCodeIsNullOrBlank(personDTO) ?
                personBlackListedCheck(personDTO) : Optional.empty();
    }

    private boolean personFirstNameIsNullOrBlank(PersonDTO personDTO) {
        return personDTO.getPersonFirstName() == null || personDTO.getPersonFirstName().isBlank();
    }

    private boolean personLastNameIsNullOrBlank(PersonDTO personDTO) {
        return personDTO.getPersonLastName() == null || personDTO.getPersonLastName().isBlank();
    }

    private boolean personCodeIsNullOrBlank(PersonDTO personDTO) {
        return personDTO.getPersonCode() == null || personDTO.getPersonCode().isBlank();
    }

    private Optional<ValidationErrorDTO> personBlackListedCheck(PersonDTO personDTO) {
        try {
            if (blackListPersonCheckService.isPersonBlackListed(personDTO)) {
                Placeholder placeholder = new Placeholder("PERSON_CODE", personDTO.getPersonCode());
                return Optional.of(errorFactory.buildError("ERROR_CODE_24", List.of(placeholder)));
            }
        } catch (Throwable e) {
            log.error("BlackList call failed!", e);
            return Optional.of(errorFactory.buildError("ERROR_CODE_25"));
        }
        return Optional.empty();
    }
}
