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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmptyPersonFirstNameValidationTest {

    @Mock
    private ValidationErrorFactory errorFactory;

    @InjectMocks
    private EmptyPersonFirstNameValidation validation;

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "   ", "\t", "\n"})
    @DisplayName("Should return error when personFirstName is empty")
    void shouldReturnErrorWhenPersonFirstNameIsEmpty(String invalidFirstName) {
        AgreementDTO agreementDTO = new AgreementDTO();
        PersonDTO personDTO = mockRequestWithFirstName(invalidFirstName);

        ValidationErrorDTO expectedError = new ValidationErrorDTO("ERROR_CODE_7", "Error description");
        when(errorFactory.buildError("ERROR_CODE_7")).thenReturn(expectedError);

        Optional<ValidationErrorDTO> errorOpt = validation.validate(agreementDTO, personDTO);

        assertTrue(errorOpt.isPresent());
        ValidationErrorDTO error = errorOpt.get();
        assertEquals("ERROR_CODE_7", error.getErrorCode());
        assertEquals("Error description", error.getDescription());
        verify(errorFactory).buildError("ERROR_CODE_7");
    }

    @ParameterizedTest
    @ValueSource(strings = {"Vasja", "Anna", "John"})
    @DisplayName("Should not return error when personFirstName is valid")
    void shouldNotReturnErrorWhenPersonFirstNameIsValid(String validFirstName) {
        AgreementDTO agreementDTO = new AgreementDTO();
        PersonDTO personDTO = mockRequestWithFirstName(validFirstName);

        Optional<ValidationErrorDTO> errorOpt = validation.validate(agreementDTO, personDTO);

        assertTrue(errorOpt.isEmpty());
        verifyNoInteractions(errorFactory);
    }

    private PersonDTO mockRequestWithFirstName(String firstName) {
        PersonDTO personDTO = mock(PersonDTO.class);
        when(personDTO.getPersonFirstName()).thenReturn(firstName);
        return personDTO;
    }
}
