package org.javaguru.travel.insurance.dto.internal;

import org.javaguru.travel.insurance.core.api.command.TravelGetAgreementCoreCommand;
import org.javaguru.travel.insurance.core.api.command.TravelGetAgreementCoreResult;
import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.api.dto.RiskDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.dto.RiskPremium;
import org.javaguru.travel.insurance.dto.ValidationError;
import org.javaguru.travel.insurance.dto.v2.PersonResponseDTO;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class GetAgreementDtoConverter {

    public TravelGetAgreementCoreCommand buildCoreCommand(String uuid) {
        return new TravelGetAgreementCoreCommand(uuid);
    }

    public TravelGetAgreementResponse buildResponse(TravelGetAgreementCoreResult coreResult) {
        return coreResult.hasErrors()
                ? buildResponseWithErrors(coreResult.getErrors())
                : buildSuccessfulResponse(coreResult);
    }

    private TravelGetAgreementResponse buildResponseWithErrors(List<ValidationErrorDTO> coreErrors) {
        List<ValidationError> errors = transformValidationErrors(coreErrors);
        return new TravelGetAgreementResponse(errors);
    }

    private List<ValidationError> transformValidationErrors(List<ValidationErrorDTO> coreErrors) {
        return coreErrors.stream()
                .map(error -> new ValidationError(error.getErrorCode(), error.getDescription()))
                .toList();
    }

    private TravelGetAgreementResponse buildSuccessfulResponse(TravelGetAgreementCoreResult coreResult) {
        AgreementDTO agreement = coreResult.getAgreement();

        List<PersonResponseDTO> people = agreement.getPeople().stream()
                .map(this::toPersonResponseDTO)
                .toList();

        return TravelGetAgreementResponse.builder()
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
}
