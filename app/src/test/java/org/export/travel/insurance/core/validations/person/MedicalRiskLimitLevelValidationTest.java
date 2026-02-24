package org.export.travel.insurance.core.validations.person;

import org.export.travel.insurance.core.api.dto.AgreementDTO;
import org.export.travel.insurance.core.api.dto.PersonDTO;
import org.export.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.export.travel.insurance.core.domain.ClassifierValue;
import org.export.travel.insurance.core.repositories.ClassifierValueRepository;
import org.export.travel.insurance.core.validations.ValidationErrorFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MedicalRiskLimitLevelValidationTest {

    @Mock
    private ValidationErrorFactory errorFactory;

    @Mock
    private ClassifierValueRepository classifierValueRepository;

    @InjectMocks
    private MedicalRiskLimitLevelValidation validation;

    @ParameterizedTest
    @ValueSource(strings = {"NEGATIVE", "UNKNOWN"})
    @DisplayName("Should return error when medicalRiskLimitLevel is invalid")
    void shouldReturnErrorWhenMedicalRiskLimitLevelIsInvalid(String level) {
        AgreementDTO agreement = new AgreementDTO();
        PersonDTO person = new PersonDTO();
        person.setMedicalRiskLimitLevel(level);

        when(classifierValueRepository.findByClassifierTitleAndIc("MEDICAL_RISK_LIMIT_LEVEL", level))
                .thenReturn(Optional.empty());

        ValidationErrorDTO expectedError =
                new ValidationErrorDTO("ERROR_CODE_15", "Error description");
        when(errorFactory.buildError(eq("ERROR_CODE_15"), anyList())).thenReturn(expectedError);

        Optional<ValidationErrorDTO> result = validation.validate(agreement, person);

        assertTrue(result.isPresent());
        assertEquals(expectedError, result.get());
        verify(errorFactory).buildError(eq("ERROR_CODE_15"), anyList());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("Should not return error when medicalRiskLimitLevel is empty")
    void shouldNotReturnErrorWhenMedicalRiskLimitLevelIsEmpty(String level) {
        AgreementDTO agreement = new AgreementDTO();
        PersonDTO person = new PersonDTO();
        person.setMedicalRiskLimitLevel(level);

        Optional<ValidationErrorDTO> result = validation.validate(agreement, person);

        assertTrue(result.isEmpty());
        verifyNoInteractions(errorFactory);
    }

    @ParameterizedTest
    @ValueSource(strings = {"LEVEL_10000", "LEVEL_15000"})
    @DisplayName("Should not return error when medicalRiskLimitLevel is valid")
    void shouldNotReturnErrorWhenMedicalRiskLimitLevelIsValid(String level) {
        AgreementDTO agreement = new AgreementDTO();
        PersonDTO person = new PersonDTO();
        person.setMedicalRiskLimitLevel(level);

        when(classifierValueRepository.findByClassifierTitleAndIc("MEDICAL_RISK_LIMIT_LEVEL", level))
                .thenReturn(Optional.of(mock(ClassifierValue.class)));

        Optional<ValidationErrorDTO> result = validation.validate(agreement, person);

        assertTrue(result.isEmpty());
        verifyNoInteractions(errorFactory);
    }
}