package org.javaguru.travel.insurance.core.validations.person;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.core.validations.ValidationErrorFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class EmptyMedicalRiskLimitLevelValidation extends TravelPersonFieldValidationImpl {

    private final ValidationErrorFactory validationErrorFactory;

    @Value( "${medical.risk.limit.level.enabled:false}" )
    private Boolean medicalRiskLimitLevelEnabled;

    @Override
    public Optional<ValidationErrorDTO> validate(AgreementDTO agreementDTO, PersonDTO personDTO) {
        return (isMedicalRiskLimitLevelEnabled()
                && containsTravelMedical(agreementDTO)
                && isMedicalRiskLimitLevelIsNullAndBlank(personDTO))
                ? Optional.of(validationErrorFactory.buildError("ERROR_CODE_14"))
                : Optional.empty();
    }

    private boolean isMedicalRiskLimitLevelEnabled() {
        return medicalRiskLimitLevelEnabled;
    }

    private boolean isMedicalRiskLimitLevelIsNullAndBlank(PersonDTO personDTO) {
        return personDTO.getMedicalRiskLimitLevel() == null || personDTO.getMedicalRiskLimitLevel().isBlank();
    }

    private boolean containsTravelMedical(AgreementDTO agreementDTO) {
        return agreementDTO.getSelectedRisks() != null
                && agreementDTO.getSelectedRisks().contains("TRAVEL_MEDICAL");
    }
}
