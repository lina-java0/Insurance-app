package org.export.travel.insurance.core.services;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.export.travel.insurance.core.api.dto.AgreementDTO;
import org.export.travel.insurance.core.underwriting.TravelPremiumCalculationResult;
import org.export.travel.insurance.core.underwriting.TravelPremiumUnderwriting;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class AgreementPeoplePremiumCalculator {

    private final TravelPremiumUnderwriting premiumUnderwriting;

    void calculateRiskPremiumsForAllPeople(AgreementDTO agreementDTO) {
        agreementDTO.getPeople().forEach(personDTO -> {
            TravelPremiumCalculationResult calculationResult = premiumUnderwriting.calculatePremium(agreementDTO, personDTO);
            personDTO.setRisks(calculationResult.risks());
        });
    }
}

