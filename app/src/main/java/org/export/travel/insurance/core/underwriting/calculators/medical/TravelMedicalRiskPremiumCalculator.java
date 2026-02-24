package org.export.travel.insurance.core.underwriting.calculators.medical;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.export.travel.insurance.core.api.dto.AgreementDTO;
import org.export.travel.insurance.core.api.dto.PersonDTO;
import org.export.travel.insurance.core.underwriting.TravelRiskPremiumCalculator;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class TravelMedicalRiskPremiumCalculator implements TravelRiskPremiumCalculator {

    private final DayCountCalculator dayCountCalculator;
    private final AgeCoefficientCalculator ageCoefficientCalculator;
    private final CountryDefaultDayRateCalculator countryDefaultDayRateCalculator;
    private final RiskLimitLevelCalculator riskLimitLevelCalculator;

    @Override
    public BigDecimal calculatePremium(AgreementDTO agreementDTO, PersonDTO personDTO) {
       var daysBetween = dayCountCalculator.calculate(agreementDTO);
       var countryDefaultRate = countryDefaultDayRateCalculator.calculate(agreementDTO);
       var ageCoefficient = ageCoefficientCalculator.calculate(personDTO);
       var riskLimitLevel = riskLimitLevelCalculator.calculate(personDTO);
       return countryDefaultRate
               .multiply(daysBetween)
               .multiply(ageCoefficient)
               .multiply(riskLimitLevel)
               .setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public String getRiskIc() {
        return "TRAVEL_MEDICAL";
    }
}
