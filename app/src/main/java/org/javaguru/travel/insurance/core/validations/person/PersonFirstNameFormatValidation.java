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
public class PersonFirstNameFormatValidation extends TravelPersonFieldValidationImpl {

    private final ValidationErrorFactory errorFactory;

    @Override
    public Optional<ValidationErrorDTO> validate(AgreementDTO agreementDTO, PersonDTO personDTO) {
        if (!isPersonFirstNameNullOrBlank(personDTO) && !isValidFormat(personDTO)) {
            Placeholder placeholder = new Placeholder("PERSON_FIRST_NAME", personDTO.getPersonFirstName());
            return Optional.of(errorFactory.buildError("ERROR_CODE_21", List.of(placeholder)));
        } else {
            return Optional.empty();
        }
    }

    private boolean isPersonFirstNameNullOrBlank(PersonDTO personDTO) {
        return personDTO.getPersonFirstName() == null || personDTO.getPersonFirstName().isBlank();
    }

    private boolean isValidFormat(PersonDTO personDTO) {
        String regex = "^[A-Za-z]+([ -][A-Za-z]+)*$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(personDTO.getPersonFirstName());
        return matcher.matches();
    }
}
