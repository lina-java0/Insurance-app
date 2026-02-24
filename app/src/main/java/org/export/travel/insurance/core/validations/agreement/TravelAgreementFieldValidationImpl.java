package org.export.travel.insurance.core.validations.agreement;

import org.export.travel.insurance.core.api.dto.AgreementDTO;
import org.export.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.export.travel.insurance.core.validations.TravelAgreementFieldValidation;

import java.util.List;
import java.util.Optional;

abstract public class TravelAgreementFieldValidationImpl implements TravelAgreementFieldValidation {

    @Override
    public Optional<ValidationErrorDTO> validate(AgreementDTO agreementDTO) {
        return Optional.empty();
    }

    @Override
    public List<ValidationErrorDTO> validateList(AgreementDTO agreementDTO) {
        return List.of();
    }

}
