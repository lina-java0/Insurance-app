package org.javaguru.travel.insurance.dto.v1;

import org.javaguru.travel.insurance.core.api.command.TravelCalculatePremiumCoreCommand;
import org.javaguru.travel.insurance.core.api.command.TravelCalculatePremiumCoreResult;
import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.dto.RiskPremium;
import org.javaguru.travel.insurance.dto.ValidationError;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DtoV1Converter {

    public TravelCalculatePremiumCoreCommand buildCoreCommand(TravelCalculatePremiumRequestV1 request) {
        AgreementDTO agreement = buildAgreement(request);
        return new TravelCalculatePremiumCoreCommand(agreement);
    }

    public TravelCalculatePremiumResponseV1 buildResponse(TravelCalculatePremiumCoreResult coreResult) {
        return coreResult.hasErrors()
                ? buildResponseWithErrors(coreResult.getErrors())
                : buildSuccessfulResponse(coreResult);
    }

    private TravelCalculatePremiumResponseV1 buildResponseWithErrors(List<ValidationErrorDTO> coreErrors) {
        List<ValidationError> errors = transformValidationErrorsToV1(coreErrors);
        return new TravelCalculatePremiumResponseV1(errors);
    }

    private List<ValidationError> transformValidationErrorsToV1(List<ValidationErrorDTO> coreErrors) {
        return coreErrors.stream()
                .map(error -> new ValidationError(error.getErrorCode(), error.getDescription()))
                .toList();
    }

    private TravelCalculatePremiumResponseV1 buildSuccessfulResponse(TravelCalculatePremiumCoreResult coreResult) {
        AgreementDTO agreement = coreResult.getAgreement();
        PersonDTO person = agreement.getPeople().get(0);

        List<RiskPremium> riskPremiums = person.getRisks().stream()
                .map(risk -> RiskPremium.builder()
                        .riskIc(risk.getRiskIc())
                        .premium(risk.getPremium())
                        .build())
                .toList();

        return TravelCalculatePremiumResponseV1.builder()
                .uuid(agreement.getUuid())
                .personFirstName(person.getPersonFirstName())
                .personLastName(person.getPersonLastName())
                .personCode(person.getPersonCode())
                .personBirthDate(person.getPersonBirthDate())
                .agreementDateFrom(agreement.getAgreementDateFrom())
                .agreementDateTo(agreement.getAgreementDateTo())
                .country(agreement.getCountry())
                .medicalRiskLimitLevel(person.getMedicalRiskLimitLevel())
                .agreementPremium(agreement.getAgreementPremium())
                .risks(riskPremiums)
                .build();
    }

    private AgreementDTO buildAgreement(TravelCalculatePremiumRequestV1 request) {
        PersonDTO person = toPersonDTO(request);

        return AgreementDTO.builder()
                .agreementDateFrom(request.getAgreementDateFrom())
                .agreementDateTo(request.getAgreementDateTo())
                .country(request.getCountry())
                .selectedRisks(request.getSelectedRisks())
                .people(List.of(person))
                .build();
    }

    private PersonDTO toPersonDTO(TravelCalculatePremiumRequestV1 request) {
        return PersonDTO.builder()
                .personFirstName(request.getPersonFirstName())
                .personLastName(request.getPersonLastName())
                .personCode(request.getPersonCode())
                .personBirthDate(request.getPersonBirthDate())
                .medicalRiskLimitLevel(request.getMedicalRiskLimitLevel())
                .build();
    }
}
