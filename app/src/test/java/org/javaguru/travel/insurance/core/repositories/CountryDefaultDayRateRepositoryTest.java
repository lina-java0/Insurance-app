package org.javaguru.travel.insurance.core.repositories;

import org.javaguru.travel.insurance.core.domain.CountryDefaultDayRate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class CountryDefaultDayRateRepositoryTest {

    @Autowired
    private CountryDefaultDayRateRepository countryDefaultDayRateRepository;

    @Test
    @DisplayName("Injected repository is not null")
    public void injectedRepositoryIsNotNull() {
        assertNotNull(countryDefaultDayRateRepository);
    }

    @Test
    @DisplayName("Should not find countryDefaultDayRate with unsupported country")
    public void shouldNotFindCountryDefaultDayRateWithUnsupportedCountry() {
        Optional<CountryDefaultDayRate> valueOpt = countryDefaultDayRateRepository.findByCountryIc("FAKE_COUNTRY");
        assertTrue(valueOpt.isEmpty());
    }

    @ParameterizedTest
    @DisplayName("Should find countryDefaultDayRate with supported country")
    @CsvSource({
            "LATVIA, 1.00",
            "SPAIN, 2.50",
            "JAPAN, 3.50"
    })


    public void shouldFindCountryDefaultDayRateWithSupportedCountry(String countryIc, String dayRateStr) {
        BigDecimal dayRate = new BigDecimal(dayRateStr);
        searchCountryDefaultDayRate(countryIc, dayRate);
    }


    private void searchCountryDefaultDayRate(String countryIc, BigDecimal dayRate) {
        Optional<CountryDefaultDayRate> valueOpt = countryDefaultDayRateRepository.findByCountryIc(countryIc);
        assertTrue(valueOpt.isPresent());
        assertEquals(countryIc, valueOpt.get().getCountryIc());
        assertEquals(dayRate, valueOpt.get().getDefaultDayRate());

        assertEquals(valueOpt.get().getDefaultDayRate().stripTrailingZeros(), dayRate.stripTrailingZeros());
    }
}
