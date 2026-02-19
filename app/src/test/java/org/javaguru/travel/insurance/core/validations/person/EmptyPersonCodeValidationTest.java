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
public class EmptyPersonCodeValidationTest {

    @Mock
    private ValidationErrorFactory errorFactory;

    @InjectMocks
    private EmptyPersonCodeValidation validation;

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "   ", "\t", "\n"})
    @DisplayName("Should return error when person code is empty")
    void shouldReturnErrorWhenPersonCodeIsEmpty(String invalidPersonCode) {
        AgreementDTO agreementDTO = new AgreementDTO();
        PersonDTO personDTO = mockRequestWithPersonCode(invalidPersonCode);

        ValidationErrorDTO expectedError = new ValidationErrorDTO("ERROR_CODE_16", "Error description");
        when(errorFactory.buildError("ERROR_CODE_16")).thenReturn(expectedError);

        Optional<ValidationErrorDTO> errorOpt = validation.validate(agreementDTO, personDTO);

        assertTrue(errorOpt.isPresent());
        ValidationErrorDTO error = errorOpt.get();
        assertEquals("ERROR_CODE_16", error.getErrorCode());
        assertEquals("Error description", error.getDescription());
        verify(errorFactory).buildError("ERROR_CODE_16");
    }

    @ParameterizedTest
    @ValueSource(strings = {"12345-12345", "666", "1111-0000"})
    @DisplayName("Should not return error when person code is valid")
    void shouldNotReturnErrorWhenPersonCodeIsValid(String validPersonCode) {
        AgreementDTO agreementDTO = new AgreementDTO();
        PersonDTO personDTO = mockRequestWithPersonCode(validPersonCode);

        Optional<ValidationErrorDTO> errorOpt = validation.validate(agreementDTO, personDTO);

        assertTrue(errorOpt.isEmpty());
        verifyNoInteractions(errorFactory);
    }

    private PersonDTO mockRequestWithPersonCode(String personCode) {
        PersonDTO personDTO = mock(PersonDTO.class);
        when(personDTO.getPersonCode()).thenReturn(personCode);
        return personDTO;
    }
}
