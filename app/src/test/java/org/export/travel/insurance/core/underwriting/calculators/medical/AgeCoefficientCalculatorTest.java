package org.export.travel.insurance.core.underwriting.calculators.medical;

import org.export.travel.insurance.core.api.dto.PersonDTO;
import org.export.travel.insurance.core.domain.AgeCoefficient;
import org.export.travel.insurance.core.repositories.AgeCoefficientRepository;
import org.export.travel.insurance.core.util.DateTimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AgeCoefficientCalculatorTest {

    @Mock
    private DateTimeUtil dateTimeUtil;

    @Mock
    private AgeCoefficientRepository ageCoefficientRepository;

    @InjectMocks
    private AgeCoefficientCalculator ageCoefficientCalculator;

    private PersonDTO personDTO;

    @BeforeEach
    void setUp() {
        personDTO = new PersonDTO();
        personDTO.setPersonBirthDate(LocalDate.of(1998, 8, 6));

        ReflectionTestUtils.setField(ageCoefficientCalculator, "medicalRiskAgeCoefficientEnabled", true);
    }

    @Test
    @DisplayName("Should find coefficient when age coefficient exists")
    void shouldFindCoefficientWhenAgeCoefficientExists() {
        LocalDate currentDay = LocalDate.of(2025, 8, 7);
        int age = 27;
        BigDecimal expectedCoefficient = BigDecimal.valueOf(1.1);

        when(dateTimeUtil.getCurrentDate()).thenReturn(currentDay);
        AgeCoefficient ageCoefficient = mock(AgeCoefficient.class);
        when(ageCoefficient.getCoefficient()).thenReturn(expectedCoefficient);
        when(ageCoefficientRepository.findCoefficient(age)).thenReturn(Optional.of(ageCoefficient));

        BigDecimal actualCoefficient = ageCoefficientCalculator.calculate(personDTO);

        assertEquals(expectedCoefficient, actualCoefficient);
    }

    @Test
    @DisplayName("Should throw exception when age coefficient not found")
    void shouldThrowExceptionWhenAgeCoefficientNotFound() {
        LocalDate currentDay = LocalDate.of(2025, 8, 7);
        int age = 27;

        when(dateTimeUtil.getCurrentDate()).thenReturn(currentDay);
        when(ageCoefficientRepository.findCoefficient(age)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> ageCoefficientCalculator.calculate(personDTO));
        assertEquals("Age coefficient not found for age = " + age, exception.getMessage());
    }

    @Test
    @DisplayName("Should return default value when medicalRiskAgeCoefficientEnabled disabled")
    void shouldReturnDefaultValueWhenMedicalRiskAgeCoefficientEnabledDisabled() {
        BigDecimal defaultCoefficient = BigDecimal.ONE;

        ReflectionTestUtils.setField(ageCoefficientCalculator, "medicalRiskAgeCoefficientEnabled", false);

        BigDecimal actualCoefficient = ageCoefficientCalculator.calculate(personDTO);

        assertEquals(defaultCoefficient, actualCoefficient);
    }
}
