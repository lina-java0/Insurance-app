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
public class PersonCodeFormatValidationTest {

    @Mock
    private ValidationErrorFactory errorFactory;

    @InjectMocks
    private PersonCodeFormatValidation validation;

    @ParameterizedTest
    @ValueSource(strings = {"123", "123456-1234", "a55555-a4444"})
    @DisplayName("Should return error when person code format is invalid")
    void shouldReturnErrorWhenPersonCodeFormatIsInvalid(String invalidFormatPersonCode) {
        AgreementDTO agreementDTO = new AgreementDTO();
        PersonDTO personDTO = mockRequestWithPersonCode(invalidFormatPersonCode);

        ValidationErrorDTO expectedError = new ValidationErrorDTO("ERROR_CODE_20", "Error description");
        when(errorFactory.buildError(eq("ERROR_CODE_20"), any())).thenReturn(expectedError);

        Optional<ValidationErrorDTO> errorOpt = validation.validate(agreementDTO, personDTO);

        assertTrue(errorOpt.isPresent());
        ValidationErrorDTO error = errorOpt.get();
        assertEquals("ERROR_CODE_20", error.getErrorCode());
        assertEquals("Error description", error.getDescription());
        verify(errorFactory).buildError(eq("ERROR_CODE_20"), any());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("Should not return error when person code is empty")
    void shouldNotReturnErrorWhenPersonCodeIsEmpty(String emptyPersonCode) {
        AgreementDTO agreementDTO = new AgreementDTO();
        PersonDTO personDTO = mockRequestWithPersonCode(emptyPersonCode);

        Optional<ValidationErrorDTO> errorOpt = validation.validate(agreementDTO, personDTO);

        assertTrue(errorOpt.isEmpty());
        verifyNoInteractions(errorFactory);
    }

    @ParameterizedTest
    @ValueSource(strings = {"123456-12345", "666666-33333", "111111-00000"})
    @DisplayName("Should not return error when person code format is valid")
    void shouldNotReturnErrorWhenPersonCodeFormatIsValid(String validFormatPersonCode) {
        AgreementDTO agreementDTO = new AgreementDTO();
        PersonDTO personDTO = mockRequestWithPersonCode(validFormatPersonCode);

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
