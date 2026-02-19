package org.javaguru.travel.insurance.dto.v2;

import org.javaguru.travel.insurance.core.api.command.TravelCalculatePremiumCoreCommand;
import org.javaguru.travel.insurance.core.api.command.TravelCalculatePremiumCoreResult;
import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.api.dto.RiskDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.dto.RiskPremium;
import org.javaguru.travel.insurance.dto.ValidationError;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Component
public class DtoV2Converter {

    public TravelCalculatePremiumCoreCommand buildCoreCommand(TravelCalculatePremiumRequestV2 request) {
        AgreementDTO agreement = buildAgreement(request);
        return new TravelCalculatePremiumCoreCommand(agreement);
    }

    public TravelCalculatePremiumResponseV2 buildResponse(TravelCalculatePremiumCoreResult coreResult) {
        return coreResult.hasErrors()
                ? buildResponseWithErrors(coreResult.getErrors())
                : buildSuccessfulResponse(coreResult);
    }

    private TravelCalculatePremiumResponseV2 buildResponseWithErrors(List<ValidationErrorDTO> coreErrors) {
        List<ValidationError> errors = transformValidationErrorsToV2(coreErrors);
        return new TravelCalculatePremiumResponseV2(errors);
    }

    private List<ValidationError> transformValidationErrorsToV2(List<ValidationErrorDTO> coreErrors) {
        return coreErrors.stream()
                .map(error -> new ValidationError(error.getErrorCode(), error.getDescription()))
                .toList();
    }

    private TravelCalculatePremiumResponseV2 buildSuccessfulResponse(TravelCalculatePremiumCoreResult coreResult) {
        AgreementDTO agreement = coreResult.getAgreement();

        List<PersonResponseDTO> people = agreement.getPeople().stream()
                .map(this::toPersonResponseDTO)
                .toList();

        return TravelCalculatePremiumResponseV2.builder()
                .uuid(agreement.getUuid())
                .agreementDateFrom(agreement.getAgreementDateFrom())
                .agreementDateTo(agreement.getAgreementDateTo())
                .country(agreement.getCountry())
                .agreementPremium(agreement.getAgreementPremium())
                .people(people)
                .build();
    }

    private PersonResponseDTO toPersonResponseDTO(PersonDTO personDTO) {
        BigDecimal personPremium = personDTO.getRisks().stream()
                .map(RiskDTO::getPremium)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        List<RiskPremium> personRisks = personDTO.getRisks().stream()
                .map(risk -> RiskPremium.builder()
                        .riskIc(risk.getRiskIc())
                        .premium(risk.getPremium())
                        .build())
                .toList();

        return PersonResponseDTO.builder()
                .personFirstName(personDTO.getPersonFirstName())
                .personLastName(personDTO.getPersonLastName())
                .personCode(personDTO.getPersonCode())
                .personBirthDate(personDTO.getPersonBirthDate())
                .medicalRiskLimitLevel(personDTO.getMedicalRiskLimitLevel())
                .personPremium(personPremium)
                .personRisks(personRisks)
                .build();
    }

    private AgreementDTO buildAgreement(TravelCalculatePremiumRequestV2 request) {
        List<PersonDTO> people = Optional.ofNullable(request.getPeople())
                .orElse(List.of())
                .stream()
                .map(this::toPersonDTO)
                .toList();

        return AgreementDTO.builder()
                .agreementDateFrom(request.getAgreementDateFrom())
                .agreementDateTo(request.getAgreementDateTo())
                .country(request.getCountry())
                .selectedRisks(request.getSelectedRisks())
                .people(people)
                .build();
    }

    private PersonDTO toPersonDTO(PersonRequestDTO personRequestDTO) {
        return PersonDTO.builder()
                .personFirstName(personRequestDTO.getPersonFirstName())
                .personLastName(personRequestDTO.getPersonLastName())
                .personCode(personRequestDTO.getPersonCode())
                .personBirthDate(personRequestDTO.getPersonBirthDate())
                .medicalRiskLimitLevel(personRequestDTO.getMedicalRiskLimitLevel())
                .build();
    }
}
