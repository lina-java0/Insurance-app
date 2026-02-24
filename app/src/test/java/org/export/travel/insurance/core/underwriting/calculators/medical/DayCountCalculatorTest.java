package org.export.travel.insurance.core.underwriting.calculators.medical;

import org.export.travel.insurance.core.api.dto.AgreementDTO;
import org.export.travel.insurance.core.util.DateTimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DayCountCalculatorTest {

    @Mock
    private DateTimeUtil dateTimeUtil;

    @InjectMocks
    private DayCountCalculator dayCountCalculator;

    private AgreementDTO agreementDTO;

    @BeforeEach
    void setUp() {
        agreementDTO = new AgreementDTO();
        agreementDTO.setAgreementDateFrom(LocalDate.of(2025, 8, 16));
        agreementDTO.setAgreementDateTo(LocalDate.of(2025, 8, 20));
    }

    @Test
    @DisplayName("Should calculate day count correctly")
    void shouldCalculateDayCountCorrectly() {
        long expectedDays = 4;

        when(dateTimeUtil.calculateDaysBetween(agreementDTO.getAgreementDateFrom(), agreementDTO.getAgreementDateTo())).thenReturn(expectedDays);
        BigDecimal actualDays = dayCountCalculator.calculate(agreementDTO);

        assertEquals(BigDecimal.valueOf(expectedDays), actualDays);
    }
}
