package org.javaguru.travel.insurance.core.validations.person;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.core.util.Placeholder;
import org.javaguru.travel.insurance.core.validations.ValidationErrorFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class PersonLastNameFormatValidation extends TravelPersonFieldValidationImpl {

    private final ValidationErrorFactory errorFactory;

    @Override
    public Optional<ValidationErrorDTO> validate(AgreementDTO agreementDTO, PersonDTO personDTO) {
        if (!isPersonLastNameNullOrBlank(personDTO) && !isValidFormat(personDTO)) {
            Placeholder placeholder = new Placeholder("PERSON_LAST_NAME", personDTO.getPersonLastName());
            return Optional.of(errorFactory.buildError("ERROR_CODE_22", List.of(placeholder)));
        } else {
            return Optional.empty();
        }
    }

    private boolean isPersonLastNameNullOrBlank(PersonDTO personDTO) {
        return personDTO.getPersonLastName() == null || personDTO.getPersonLastName().isBlank();
    }

    private boolean isValidFormat(PersonDTO personDTO) {
        String regex = "^[A-Za-z]+([ -][A-Za-z]+)*$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(personDTO.getPersonLastName());
        return matcher.matches();
    }
}
