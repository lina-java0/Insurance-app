package org.export.travel.insurance.core.validations.person;

import org.export.travel.insurance.core.api.dto.AgreementDTO;
import org.export.travel.insurance.core.api.dto.PersonDTO;
import org.export.travel.insurance.core.api.dto.ValidationErrorDTO;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PersonLastNameFormatValidationTest {

    @Mock
    private ValidationErrorFactory errorFactory;

    @InjectMocks
    private PersonLastNameFormatValidation validation;

    @ParameterizedTest
    @ValueSource(strings = {"Doe_Smith", "Lab_ochev-skiy", "Vangelsing-Thord 12"})
    @DisplayName("Should return error when personLastName format is invalid")
    void shouldReturnErrorWhenPersonLastNameFormatIsInvalid(String invalidFormatPersonLastName) {
        AgreementDTO agreementDTO = new AgreementDTO();
        PersonDTO personDTO = mockRequestWithPersonLastName(invalidFormatPersonLastName);

        ValidationErrorDTO expectedError = new ValidationErrorDTO("ERROR_CODE_22", "Error description");
        when(errorFactory.buildError(eq("ERROR_CODE_22"), any())).thenReturn(expectedError);

        Optional<ValidationErrorDTO> errorOpt = validation.validate(agreementDTO, personDTO);

        assertTrue(errorOpt.isPresent());
        ValidationErrorDTO error = errorOpt.get();
        assertEquals("ERROR_CODE_22", error.getErrorCode());
        assertEquals("Error description", error.getDescription());
        verify(errorFactory).buildError(eq("ERROR_CODE_22"), any());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("Should not return error when personLastName is empty")
    void shouldNotReturnErrorWhenPersonLastNameIsEmpty(String emptyPersonLastName) {
        AgreementDTO agreementDTO = new AgreementDTO();
        PersonDTO personDTO = mockRequestWithPersonLastName(emptyPersonLastName);

        Optional<ValidationErrorDTO> errorOpt = validation.validate(agreementDTO, personDTO);

        assertTrue(errorOpt.isEmpty());
        verifyNoInteractions(errorFactory);
    }

    @ParameterizedTest
    @ValueSource(strings = {"Doe-Smith", "Labochevskiy", "Vangelsing-Thord"})
    @DisplayName("Should not return error when personLastName format is valid")
    void shouldNotReturnErrorWhenPersonLastNameFormatIsValid(String validFormatPersonLastName) {
        AgreementDTO agreementDTO = new AgreementDTO();
        PersonDTO personDTO = mockRequestWithPersonLastName(validFormatPersonLastName);

        Optional<ValidationErrorDTO> errorOpt = validation.validate(agreementDTO, personDTO);

        assertTrue(errorOpt.isEmpty());
        verifyNoInteractions(errorFactory);
    }

    private PersonDTO mockRequestWithPersonLastName(String personLastName) {
        PersonDTO personDTO = mock(PersonDTO.class);
        when(personDTO.getPersonLastName()).thenReturn(personLastName);
        return personDTO;
    }
}
