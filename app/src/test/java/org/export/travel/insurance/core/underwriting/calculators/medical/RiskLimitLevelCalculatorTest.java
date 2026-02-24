package org.export.travel.insurance.core.underwriting.calculators.medical;

import org.export.travel.insurance.core.api.dto.PersonDTO;
import org.export.travel.insurance.core.domain.MedicalRiskLimitLevel;
import org.export.travel.insurance.core.repositories.MedicalRiskLimitLevelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RiskLimitLevelCalculatorTest {

    @Mock
    private MedicalRiskLimitLevelRepository medicalRiskLimitLevelRepository;

    @InjectMocks
    private RiskLimitLevelCalculator riskLimitLevelCalculator;

    private PersonDTO personDTO;

    @BeforeEach
    void setUp() {
        personDTO = new PersonDTO();
        personDTO.setMedicalRiskLimitLevel("LEVEL_10000");

        ReflectionTestUtils.setField(riskLimitLevelCalculator, "medicalRiskLimitLevelEnabled", true);
    }

    @Test
    @DisplayName("Should calculate medicalRiskLimitLevel")
    void shouldCalculateMedicalRiskLimitLevel() {
        BigDecimal expectedCoefficient = BigDecimal.valueOf(2.0);

        MedicalRiskLimitLevel medicalRiskLimitLevel = mock(MedicalRiskLimitLevel.class);
        when(medicalRiskLimitLevel.getCoefficient()).thenReturn(expectedCoefficient);

        when(medicalRiskLimitLevelRepository.findByMedicalRiskLimitLevelIc(personDTO.getMedicalRiskLimitLevel()))
                .thenReturn(Optional.of(medicalRiskLimitLevel));

        BigDecimal actualCoefficient = riskLimitLevelCalculator.calculate(personDTO);
        assertEquals(expectedCoefficient, actualCoefficient);
    }

    @Test
    @DisplayName("Should throw exception when medicalRiskLimitLevel not found")
    void shouldThrowExceptionWhenMedicalRiskLimitLevelNotFound() {
        when(medicalRiskLimitLevelRepository.findByMedicalRiskLimitLevelIc(personDTO.getMedicalRiskLimitLevel())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> riskLimitLevelCalculator.calculate(personDTO));
        assertEquals("Medical risk limit level not found by = " + personDTO.getMedicalRiskLimitLevel(), exception.getMessage());
    }

    @Test
    @DisplayName("Should return default value when medicalRiskLimitLevel disabled")
    void shouldReturnDefaultValueWhenMedicalRiskLimitLevelDisabled() {
        BigDecimal defaultCoefficient = BigDecimal.ONE;
        ReflectionTestUtils.setField(riskLimitLevelCalculator, "medicalRiskLimitLevelEnabled", false);

        BigDecimal actualCoefficient = riskLimitLevelCalculator.calculate(personDTO);

        assertEquals(defaultCoefficient, actualCoefficient);
    }
}
