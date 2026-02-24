package org.export.travel.insurance.core.validations;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.export.travel.insurance.core.api.dto.AgreementDTO;
import org.export.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class TravelAgreementValidatorImpl implements TravelAgreementValidator{

    private final TravelAgreementFieldValidator agreementFieldValidator;
    private final TravelPersonFieldValidator personFieldValidator;

    @Override
    public List<ValidationErrorDTO> validate(AgreementDTO agreementDTO) {
        return Stream.concat(
                agreementFieldValidator.validate(agreementDTO).stream(),
                agreementDTO.getPeople().stream()
                        .flatMap(personDTO -> personFieldValidator.validate(agreementDTO, personDTO).stream())
        ).toList();
    }
}
