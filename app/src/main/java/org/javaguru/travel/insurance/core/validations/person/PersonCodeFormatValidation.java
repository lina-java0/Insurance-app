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
public class PersonCodeFormatValidation extends TravelPersonFieldValidationImpl {

    private final ValidationErrorFactory errorFactory;

    @Override
    public Optional<ValidationErrorDTO> validate(AgreementDTO agreementDTO, PersonDTO personDTO) {
        if (!isPersonCodeNullOrBlank(personDTO) && !isValidFormat(personDTO)) {
            Placeholder placeholder = new Placeholder("PERSONAL_CODE", personDTO.getPersonCode());
            return Optional.of(errorFactory.buildError("ERROR_CODE_20", List.of(placeholder)));
        } else {
            return Optional.empty();
        }
    }

    private boolean isPersonCodeNullOrBlank(PersonDTO personDTO) {
        return personDTO.getPersonCode() == null || personDTO.getPersonCode().isBlank();
    }

    private boolean isValidFormat(PersonDTO personDTO) {
        String regex = "^\\d{6}-\\d{5}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(personDTO.getPersonCode());
        return matcher.matches();
    }
}
