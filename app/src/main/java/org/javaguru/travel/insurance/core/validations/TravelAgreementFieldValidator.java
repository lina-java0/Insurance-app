package org.javaguru.travel.insurance.core.validations;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class TravelAgreementFieldValidator {

    private final List<TravelAgreementFieldValidation> agreementFieldValidations;

    public List<ValidationErrorDTO> validate(AgreementDTO agreementDTO) {
        return collectErrors(agreementFieldValidations, agreementDTO);
    }

    private List<ValidationErrorDTO> collectErrors(List<TravelAgreementFieldValidation> validations, AgreementDTO agreementDTO) {
        return validations.stream()
                .flatMap(v -> Stream.concat(
                        v.validate(agreementDTO).stream(),
                        toStream(v.validateList(agreementDTO))
                ))
                .toList();
    }

    private <T> Stream<T> toStream(Collection<T> collection) {
        return collection == null ? Stream.empty() : collection.stream();
    }
}
