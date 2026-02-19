package org.javaguru.travel.insurance.core.validations.agreement;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.core.validations.ValidationErrorFactory;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class PeopleListNotEmptyValidation extends TravelAgreementFieldValidationImpl {

    private final ValidationErrorFactory errorFactory;

    @Override
    public List<ValidationErrorDTO> validateList(AgreementDTO agreement) {
        if (agreement.getPeople() == null || agreement.getPeople().isEmpty()) {
            return List.of(
                    errorFactory.buildError("ERROR_CODE_7"),
                    errorFactory.buildError("ERROR_CODE_8"),
                    errorFactory.buildError("ERROR_CODE_16"),
                    errorFactory.buildError("ERROR_CODE_12"),
                    errorFactory.buildError("ERROR_CODE_14")
            );
        }
        return Collections.emptyList();
    }
}
