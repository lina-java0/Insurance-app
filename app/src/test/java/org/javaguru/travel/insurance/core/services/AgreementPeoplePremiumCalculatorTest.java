package org.javaguru.travel.insurance.core.services;

import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.api.dto.RiskDTO;
import org.javaguru.travel.insurance.core.underwriting.TravelPremiumCalculationResult;
import org.javaguru.travel.insurance.core.underwriting.TravelPremiumUnderwriting;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AgreementPeoplePremiumCalculatorTest {

    @Mock
    private TravelPremiumUnderwriting premiumUnderwriting;

    @InjectMocks
    AgreementPeoplePremiumCalculator calculator;

    @Test
    @DisplayName("Should calculate risk premium for all people")
    public void shouldCalculateRiskPremiumForAllPeople() {
        AgreementDTO agreementDTO =  new AgreementDTO();

        PersonDTO personDTO1 = new PersonDTO();
        PersonDTO personDTO2 = new PersonDTO();

        agreementDTO.setPeople(List.of(personDTO1, personDTO2));

        RiskDTO risk11 = new RiskDTO("RISK_1", BigDecimal.ONE);
        RiskDTO risk12 = new RiskDTO("RISK_2", BigDecimal.ONE);
        RiskDTO risk21 = new RiskDTO("RISK_1", BigDecimal.ONE);
        RiskDTO risk22 = new RiskDTO("RISK_2", BigDecimal.ONE);

        TravelPremiumCalculationResult calculationResult1 = new TravelPremiumCalculationResult(BigDecimal.ONE, List.of(risk11, risk12));
        TravelPremiumCalculationResult calculationResult2 = new TravelPremiumCalculationResult(BigDecimal.ONE, List.of(risk21, risk22));

        when(premiumUnderwriting.calculatePremium(agreementDTO, personDTO1)).thenReturn(calculationResult1);
        when(premiumUnderwriting.calculatePremium(agreementDTO, personDTO2)).thenReturn(calculationResult2);

        calculator.calculateRiskPremiumsForAllPeople(agreementDTO);

        assertEquals(2, agreementDTO.getPeople().get(0).getRisks().size());
        assertEquals(2, agreementDTO.getPeople().get(1).getRisks().size());
    }
}
