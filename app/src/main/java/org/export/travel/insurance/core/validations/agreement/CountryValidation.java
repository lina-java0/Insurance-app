package org.export.travel.insurance.core.validations.agreement;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.export.travel.insurance.core.api.dto.AgreementDTO;
import org.export.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.export.travel.insurance.core.repositories.ClassifierValueRepository;
import org.export.travel.insurance.core.util.Placeholder;
import org.export.travel.insurance.core.validations.ValidationErrorFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class CountryValidation extends TravelAgreementFieldValidationImpl {

    private final ClassifierValueRepository classifierValueRepository;
    private final ValidationErrorFactory validationErrorFactory;

    @Override
    public Optional<ValidationErrorDTO> validate(AgreementDTO agreementDTO) {
        return isCountryIsNotNullAndBlank(agreementDTO)
                && !existInDatabase(agreementDTO.getCountry())
                ? Optional.of(buildValidationError(agreementDTO.getCountry()))
                : Optional.empty();
    }

    private boolean isCountryIsNotNullAndBlank(AgreementDTO agreementDTO) {
        return agreementDTO.getCountry() != null && !agreementDTO.getCountry().isBlank();
    }

    private ValidationErrorDTO buildValidationError(String countryIc) {
        Placeholder placeholder = new Placeholder("NOT_SUPPORTED_COUNTRY", countryIc);
        return validationErrorFactory.buildError("ERROR_CODE_11", List.of(placeholder));
    }

    private boolean existInDatabase(String countryIc) {
        return classifierValueRepository.findByClassifierTitleAndIc("COUNTRY", countryIc).isPresent();
    }
}
