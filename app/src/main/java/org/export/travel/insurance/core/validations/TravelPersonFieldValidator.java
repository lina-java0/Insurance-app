package org.export.travel.insurance.core.validations;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.export.travel.insurance.core.api.dto.AgreementDTO;
import org.export.travel.insurance.core.api.dto.PersonDTO;
import org.export.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class TravelPersonFieldValidator {

    private final List<TravelPersonFieldValidation> personFieldValidations;

    public List<ValidationErrorDTO> validate(AgreementDTO agreementDTO, PersonDTO personDTO) {
        return collectErrors(personFieldValidations, agreementDTO, personDTO);
    }

    private List<ValidationErrorDTO> collectErrors(List<TravelPersonFieldValidation> validations, AgreementDTO agreementDTO, PersonDTO personDTO) {
        return validations.stream()
                .flatMap(v -> Stream.concat(
                        v.validate(agreementDTO, personDTO).stream(),
                        toStream(v.validateList(agreementDTO, personDTO))
                ))
                .toList();
    }

    private <T> Stream<T> toStream(Collection<T> collection) {
        return collection == null ? Stream.empty() : collection.stream();
    }
}
