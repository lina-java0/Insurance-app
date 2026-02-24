package org.export.travel.insurance.core.underwriting.calculators.medical;

import org.export.travel.insurance.core.api.dto.AgreementDTO;
import org.export.travel.insurance.core.domain.CountryDefaultDayRate;
import org.export.travel.insurance.core.repositories.CountryDefaultDayRateRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CountryDefaultDayRateCalculatorTest {

    @Mock
    private CountryDefaultDayRateRepository countryDefaultDayRateRepository;

    @InjectMocks
    private CountryDefaultDayRateCalculator countryDefaultDayRateCalculator;

    private AgreementDTO agreementDTO;

    @BeforeEach
    void setUp() {
        agreementDTO = new AgreementDTO();
        agreementDTO.setCountry("SPAIN");
    }

    @Test
    @DisplayName("Should calculate dayRate when countryDayRate exists")
    void shouldCalculateDayRateWhenCountryDayRateExists() {
        BigDecimal expectedDayRate = BigDecimal.valueOf(10.0);

        CountryDefaultDayRate countryDefaultDayRate = mock(CountryDefaultDayRate.class);
        when(countryDefaultDayRate.getDefaultDayRate()).thenReturn(expectedDayRate);

        when(countryDefaultDayRateRepository.findByCountryIc(agreementDTO.getCountry()))
                .thenReturn(Optional.of(countryDefaultDayRate));

        BigDecimal actualDayRate = countryDefaultDayRateCalculator.calculate(agreementDTO);
        assertEquals(expectedDayRate, actualDayRate);
    }

    @Test
    @DisplayName("Should throw exception when countryDayRate not found")
    void shouldThrowExceptionWhenCountryDayRateNotFound() {
        when(countryDefaultDayRateRepository.findByCountryIc(agreementDTO.getCountry())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> countryDefaultDayRateCalculator.calculate(agreementDTO));
        assertEquals("Country day rate not found by country id = " + agreementDTO.getCountry(), exception.getMessage());
    }
}
