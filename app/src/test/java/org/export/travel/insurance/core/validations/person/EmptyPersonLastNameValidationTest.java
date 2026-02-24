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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmptyPersonLastNameValidationTest {

    @Mock
    private ValidationErrorFactory errorFactory;

    @InjectMocks
    private EmptyPersonLastNameValidation validation;

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "   ", "\t", "\n"})
    @DisplayName("Should return error when personLastName is empty")
    void shouldReturnErrorWhenPersonLastNameIsEmpty(String invalidLastName) {
        AgreementDTO agreementDTO = new AgreementDTO();
        PersonDTO personDTO = mockRequestWithLastName(invalidLastName);

        ValidationErrorDTO expectedError = new ValidationErrorDTO("ERROR_CODE_8", "Error description");
        when(errorFactory.buildError("ERROR_CODE_8")).thenReturn(expectedError);

        Optional<ValidationErrorDTO> errorOpt = validation.validate(agreementDTO, personDTO);

        assertTrue(errorOpt.isPresent());
        ValidationErrorDTO error = errorOpt.get();
        assertEquals("ERROR_CODE_8", error.getErrorCode());
        assertEquals("Error description", error.getDescription());
        verify(errorFactory).buildError("ERROR_CODE_8");
    }

    @ParameterizedTest
    @ValueSource(strings = {"Pupkin", "Konovalova", "Doe"})
    @DisplayName("Should not return error when personLastName is valid")
    void shouldNotReturnErrorWhenPersonLastNameIsValid(String validLastName) {
        AgreementDTO agreementDTO = new AgreementDTO();
        PersonDTO personDTO = mockRequestWithLastName(validLastName);

        Optional<ValidationErrorDTO> errorOpt = validation.validate(agreementDTO, personDTO);

        assertTrue(errorOpt.isEmpty());
        verifyNoInteractions(errorFactory);
    }

    private PersonDTO mockRequestWithLastName(String lastName) {
        PersonDTO personDTO = mock(PersonDTO.class);
        when(personDTO.getPersonLastName()).thenReturn(lastName);
        return personDTO;
    }
}
