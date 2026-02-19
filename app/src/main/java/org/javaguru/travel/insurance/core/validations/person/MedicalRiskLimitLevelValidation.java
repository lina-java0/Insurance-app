package org.javaguru.travel.insurance.core.validations.person;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.core.repositories.ClassifierValueRepository;
import org.javaguru.travel.insurance.core.util.Placeholder;
import org.javaguru.travel.insurance.core.validations.ValidationErrorFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class MedicalRiskLimitLevelValidation extends TravelPersonFieldValidationImpl {

    private final ClassifierValueRepository classifierValueRepository;
    private final ValidationErrorFactory validationErrorFactory;

    @Override
    public Optional<ValidationErrorDTO> validate(AgreementDTO agreementDTO, PersonDTO personDTO) {
        return (isMedicalRiskLimitLevelNotBlank(personDTO)
                && !existInDatabase(personDTO.getMedicalRiskLimitLevel()))
                ? Optional.of(buildValidationError(personDTO.getMedicalRiskLimitLevel()))
                : Optional.empty();
    }

    private boolean isMedicalRiskLimitLevelNotBlank(PersonDTO personDTO) {
        return personDTO.getMedicalRiskLimitLevel() != null && !personDTO.getMedicalRiskLimitLevel().isBlank();
    }

    private ValidationErrorDTO buildValidationError(String medicalRiscLimitLevelIc) {
        Placeholder placeholder = new Placeholder("NOT_SUPPORTED_MEDICAL_RISK_LIMIT_LEVEL", medicalRiscLimitLevelIc);
        return validationErrorFactory.buildError("ERROR_CODE_15", List.of(placeholder));
    }

    private boolean existInDatabase(String medicalRiscLimitLevelIc) {
        return classifierValueRepository.findByClassifierTitleAndIc("MEDICAL_RISK_LIMIT_LEVEL", medicalRiscLimitLevelIc).isPresent();
    }
}
