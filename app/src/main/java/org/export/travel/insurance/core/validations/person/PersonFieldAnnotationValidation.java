package org.export.travel.insurance.core.validations.person;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.export.travel.insurance.core.api.dto.AgreementDTO;
import org.export.travel.insurance.core.api.dto.PersonDTO;
import org.export.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.export.travel.insurance.core.util.Placeholder;
import org.export.travel.insurance.core.validations.ValidationErrorFactory;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class PersonFieldAnnotationValidation extends TravelPersonFieldValidationImpl {

    private final ValidationErrorFactory errorFactory;
    private final Validator validator;

    @Override
    public List<ValidationErrorDTO> validateList(AgreementDTO agreementDTO, PersonDTO personDTO) {
        Set<ConstraintViolation<PersonDTO>> violations = validator.validate(personDTO);
        if (violations.isEmpty()) {
            return List.of();
        }

        return violations.stream()
                .sorted(Comparator.comparing(v -> v.getPropertyPath().toString()))
                .map(v -> {
                    String fieldName = v.getPropertyPath().toString();
                    String fieldMessage = v.getMessage();
                    Placeholder p1 = new Placeholder("FIELD_NAME", fieldName);
                    Placeholder p2 = new Placeholder("FIELD_MESSAGE", fieldMessage);
                    return errorFactory.buildError("ERROR_CODE_23", List.of(p1, p2));
                })
                .collect(Collectors.toList());
     }
}
