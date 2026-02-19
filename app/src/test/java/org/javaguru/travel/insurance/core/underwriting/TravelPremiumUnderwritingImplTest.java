package org.javaguru.travel.insurance.core.underwriting;

import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.api.dto.RiskDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TravelPremiumUnderwritingImplTest {

    @Mock
    private SelectedRisksPremiumCalculator selectedRisksPremiumCalculator;

    @InjectMocks
    private TravelPremiumUnderwritingImpl travelPremiumUnderwriting;

    @Test
    @DisplayName("Should calculate total premium as sum of riskPremiums")
    void shouldCalculateTotalPremiumAsSumOfRiskPremiums() {
        AgreementDTO agreementDTO = mock(AgreementDTO.class);
        PersonDTO personDTO = mock(PersonDTO.class);

        List<RiskDTO> riskPremiums = List.of(
                new RiskDTO("TRAVEL_MEDICAL", BigDecimal.ONE),
                new RiskDTO("TRAVEL_EVACUATION", BigDecimal.ONE)
        );

        when(selectedRisksPremiumCalculator.calculatePremiumForAllRisks(agreementDTO, personDTO)).thenReturn(riskPremiums);

        TravelPremiumCalculationResult premiumCalculationResult = travelPremiumUnderwriting.calculatePremium(agreementDTO, personDTO);

        assertEquals(BigDecimal.valueOf(2), premiumCalculationResult.totalPremium());
    }
}
