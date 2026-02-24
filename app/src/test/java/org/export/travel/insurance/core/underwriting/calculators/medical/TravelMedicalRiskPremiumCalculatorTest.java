package org.export.travel.insurance.core.underwriting.calculators.medical;

import org.export.travel.insurance.core.api.dto.AgreementDTO;
import org.export.travel.insurance.core.api.dto.PersonDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TravelMedicalRiskPremiumCalculatorTest {

    @Mock
    private DayCountCalculator dayCountCalculator;

    @Mock
    private CountryDefaultDayRateCalculator countryDefaultDayRateCalculator;

    @Mock
    private AgeCoefficientCalculator ageCoefficientCalculator;

    @Mock
    private RiskLimitLevelCalculator riskLimitLevelCalculator;

    @InjectMocks
    private TravelMedicalRiskPremiumCalculator travelMedicalRiskPremiumCalculator;

    private AgreementDTO agreementDTO;
    private PersonDTO personDTO;

    @BeforeEach
    void setUp() {
        agreementDTO = new AgreementDTO();
        personDTO = new PersonDTO();
    }

    @Test
    @DisplayName("Should calculate premium correctly")
    void shouldCalculatePremiumCorrectly() {
        BigDecimal daysCount = BigDecimal.valueOf(10);
        BigDecimal countryDefaultDayRate = BigDecimal.valueOf(2.5);
        BigDecimal ageCoefficient = BigDecimal.valueOf(1.1);
        BigDecimal riskLimitLevelCoefficient = BigDecimal.ONE;

        when(dayCountCalculator.calculate(agreementDTO)).thenReturn(daysCount);
        when(countryDefaultDayRateCalculator.calculate(agreementDTO)).thenReturn(countryDefaultDayRate);
        when(ageCoefficientCalculator.calculate(personDTO)).thenReturn(ageCoefficient);
        when(riskLimitLevelCalculator.calculate(personDTO)).thenReturn(riskLimitLevelCoefficient);

        BigDecimal expectedPremium = countryDefaultDayRate
                .multiply(daysCount)
                .multiply(ageCoefficient)
                .multiply(riskLimitLevelCoefficient)
                .setScale(2, RoundingMode.HALF_UP);

        BigDecimal actualPremium = travelMedicalRiskPremiumCalculator.calculatePremium(agreementDTO, personDTO);

        assertEquals(expectedPremium, actualPremium);
    }
}
