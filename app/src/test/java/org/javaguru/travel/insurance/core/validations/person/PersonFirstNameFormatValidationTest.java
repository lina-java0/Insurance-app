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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PersonFirstNameFormatValidationTest {

    @Mock
    private ValidationErrorFactory errorFactory;

    @InjectMocks
    private PersonFirstNameFormatValidation validation;

    @ParameterizedTest
    @ValueSource(strings = {"John Doe-Smith 11", "Lina_Lu", "Abraham Vangelsing-3"})
    @DisplayName("Should return error when personFirstName format is invalid")
    void shouldReturnErrorWhenPersonFirstNameFormatIsInvalid(String invalidFormatPersonFirstName) {
        AgreementDTO agreementDTO = new AgreementDTO();
        PersonDTO personDTO = mockRequestWithPersonFirstName(invalidFormatPersonFirstName);

        ValidationErrorDTO expectedError = new ValidationErrorDTO("ERROR_CODE_21", "Error description");
        when(errorFactory.buildError(eq("ERROR_CODE_21"), any())).thenReturn(expectedError);

        Optional<ValidationErrorDTO> errorOpt = validation.validate(agreementDTO, personDTO);

        assertTrue(errorOpt.isPresent());
        ValidationErrorDTO error = errorOpt.get();
        assertEquals("ERROR_CODE_21", error.getErrorCode());
        assertEquals("Error description", error.getDescription());
        verify(errorFactory).buildError(eq("ERROR_CODE_21"), any());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("Should not return error when personFirstName is empty")
    void shouldNotReturnErrorWhenPersonFirstNameIsEmpty(String emptyPersonFirstName) {
        AgreementDTO agreementDTO = new AgreementDTO();
        PersonDTO personDTO = mockRequestWithPersonFirstName(emptyPersonFirstName);

        Optional<ValidationErrorDTO> errorOpt = validation.validate(agreementDTO, personDTO);

        assertTrue(errorOpt.isEmpty());
        verifyNoInteractions(errorFactory);
    }

    @ParameterizedTest
    @ValueSource(strings = {"John Doe-Smith", "Lina Lu", "Abraham Vangelsing-Thord"})
    @DisplayName("Should not return error when personFirstName format is valid")
    void shouldNotReturnErrorWhenPersonFirstNameFormatIsValid(String validFormatPersonFirstName) {
        AgreementDTO agreementDTO = new AgreementDTO();
        PersonDTO personDTO = mockRequestWithPersonFirstName(validFormatPersonFirstName);

        Optional<ValidationErrorDTO> errorOpt = validation.validate(agreementDTO, personDTO);

        assertTrue(errorOpt.isEmpty());
        verifyNoInteractions(errorFactory);
    }

    private PersonDTO mockRequestWithPersonFirstName(String personFirstName) {
        PersonDTO personDTO = mock(PersonDTO.class);
        when(personDTO.getPersonFirstName()).thenReturn(personFirstName);
        return personDTO;
    }
}
