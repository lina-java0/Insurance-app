package org.javaguru.travel.insurance.core.underwriting;

import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.api.dto.RiskDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SelectedRisksPremiumCalculatorTest {

    private SelectedRisksPremiumCalculator selectedRisksPremiumCalculator;

    private TravelRiskPremiumCalculator riskPremiumCalculator1;
    private TravelRiskPremiumCalculator riskPremiumCalculator2;

    @BeforeEach
    public void init() {
        riskPremiumCalculator1 = mock(TravelRiskPremiumCalculator.class);
        riskPremiumCalculator2 = mock(TravelRiskPremiumCalculator.class);
        var riskPremiumCalculators = List.of(riskPremiumCalculator1, riskPremiumCalculator2);
        selectedRisksPremiumCalculator = new SelectedRisksPremiumCalculator(riskPremiumCalculators);
    }

    @Test
    @DisplayName("Should calculate premium for one risk")
    void shouldCalculatePremiumForOneRisk() {
        when(riskPremiumCalculator1.getRiskIc()).thenReturn("TRAVEL_MEDICAL");
        when(riskPremiumCalculator1.calculatePremium(any(), any())).thenReturn(BigDecimal.ONE);

        AgreementDTO agreementDTO = mock(AgreementDTO.class);
        PersonDTO personDTO = mock(PersonDTO.class);

        when(agreementDTO.getSelectedRisks()).thenReturn(List.of("TRAVEL_MEDICAL"));

        List<RiskDTO> riskPremiums = selectedRisksPremiumCalculator.calculatePremiumForAllRisks(agreementDTO, personDTO);

        assertEquals(1, riskPremiums.size());
        assertEquals("TRAVEL_MEDICAL", riskPremiums.get(0).getRiskIc());
        assertEquals(BigDecimal.ONE, riskPremiums.get(0).getPremium());
    }

    @Test
    @DisplayName("Should calculate premium for two risks")
    void shouldCalculatePremiumForTwoRisks() {
        when(riskPremiumCalculator1.getRiskIc()).thenReturn("TRAVEL_MEDICAL");
        when(riskPremiumCalculator2.getRiskIc()).thenReturn("TRAVEL_EVACUATION");

        when(riskPremiumCalculator1.calculatePremium(any(), any())).thenReturn(BigDecimal.ONE);
        when(riskPremiumCalculator2.calculatePremium(any(), any())).thenReturn(BigDecimal.ONE);

        AgreementDTO agreementDTO = mock(AgreementDTO.class);
        PersonDTO personDTO = mock(PersonDTO.class);

        when(agreementDTO.getSelectedRisks()).thenReturn(List.of("TRAVEL_MEDICAL", "TRAVEL_EVACUATION"));

        List<RiskDTO> riskPremiums = selectedRisksPremiumCalculator.calculatePremiumForAllRisks(agreementDTO, personDTO);

        assertEquals(2, riskPremiums.size());
        assertEquals("TRAVEL_MEDICAL", riskPremiums.get(0).getRiskIc());
        assertEquals(BigDecimal.ONE, riskPremiums.get(0).getPremium());
        assertEquals("TRAVEL_EVACUATION", riskPremiums.get(1).getRiskIc());
        assertEquals(BigDecimal.ONE, riskPremiums.get(1).getPremium());
    }

    @Test
    @DisplayName("Should throw exception when selected risk type not supported")
    void shouldThrowExceptionWhenSelectedRiskTypeNotSupported() {
        when(riskPremiumCalculator1.getRiskIc()).thenReturn("MEDICAL");
        when(riskPremiumCalculator2.getRiskIc()).thenReturn("EVACUATION");

        AgreementDTO agreementDTO = mock(AgreementDTO.class);
        PersonDTO personDTO = mock(PersonDTO.class);

        when(agreementDTO.getSelectedRisks()).thenReturn(List.of("NOT_SUPPORTED_RISK_TYPE"));

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> selectedRisksPremiumCalculator.calculatePremiumForAllRisks(agreementDTO, personDTO));
        assertEquals("Not supported riskIc = NOT_SUPPORTED_RISK_TYPE", exception.getMessage());
    }
}
