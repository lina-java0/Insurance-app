package org.javaguru.travel.insurance.core.validations.person;

import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.core.validations.ValidationErrorFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmptyMedicalRiskLimitLevelValidationTest {

    @Mock
    private ValidationErrorFactory errorFactory;

    @InjectMocks
    private EmptyMedicalRiskLimitLevelValidation validation;

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" "})
    @DisplayName("Should return error when medicalRiskLimitLevel is empty")
    void shouldReturnErrorWhenMedicalRiskLimitLevelIsEmpty(String level) {
        AgreementDTO agreement = new AgreementDTO();
        agreement.setSelectedRisks(List.of("TRAVEL_MEDICAL"));

        PersonDTO person = new PersonDTO();
        person.setMedicalRiskLimitLevel(level);

        // Включаем флаг через Reflection (по аналогии с оригиналом)
        ReflectionTestUtils.setField(validation, "medicalRiskLimitLevelEnabled", true);

        ValidationErrorDTO expectedError =
                new ValidationErrorDTO("ERROR_CODE_14", "Field medicalRiskLimitLevel must not be empty!");
        when(errorFactory.buildError("ERROR_CODE_14")).thenReturn(expectedError);

        Optional<ValidationErrorDTO> result = validation.validate(agreement, person);

        assertTrue(result.isPresent());
        assertEquals(expectedError, result.get());
        verify(errorFactory).buildError("ERROR_CODE_14");
    }

    @ParameterizedTest
    @ValueSource(strings = {"LEVEL_10000", "LEVEL_15000"})
    @DisplayName("Should not return error when medicalRiskLimitLevel is valid")
    void shouldNotReturnErrorWhenMedicalRiskLimitLevelIsValid(String level) {
        AgreementDTO agreement = new AgreementDTO();
        agreement.setSelectedRisks(List.of("TRAVEL_MEDICAL"));

        PersonDTO person = new PersonDTO();
        person.setMedicalRiskLimitLevel(level);

        ReflectionTestUtils.setField(validation, "medicalRiskLimitLevelEnabled", true);

        Optional<ValidationErrorDTO> result = validation.validate(agreement, person);

        assertTrue(result.isEmpty());
        verifyNoInteractions(errorFactory);
    }
}