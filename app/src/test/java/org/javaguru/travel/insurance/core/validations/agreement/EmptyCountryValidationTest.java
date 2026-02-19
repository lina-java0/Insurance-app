package org.javaguru.travel.insurance.core.validations.agreement;

import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
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
class EmptyCountryValidationTest {

    @Mock
    private ValidationErrorFactory errorFactory;

    @InjectMocks
    private EmptyCountryValidation validation;

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   "})
    @DisplayName("Should return error when country is empty")
    void shouldReturnErrorWhenCountryIsEmpty(String country) {
        AgreementDTO agreementDTO = mock(AgreementDTO.class);
        when(agreementDTO.getCountry()).thenReturn(country);

        ValidationErrorDTO expectedError = new ValidationErrorDTO("ERROR_CODE_10", "Error description");
        when(errorFactory.buildError("ERROR_CODE_10")).thenReturn(expectedError);

        Optional<ValidationErrorDTO> result = validation.validate(agreementDTO);

        assertTrue(result.isPresent());
        assertEquals("ERROR_CODE_10", result.get().getErrorCode());
        assertEquals("Error description", result.get().getDescription());
        verify(errorFactory).buildError("ERROR_CODE_10");
    }

    @ParameterizedTest
    @ValueSource(strings = {"SPAIN", "USA"})
    @DisplayName("Should not return error when country is valid")
    void shouldNotReturnErrorWhenCountryIsPresent(String country) {
        AgreementDTO agreementDTO = mock(AgreementDTO.class);
        when(agreementDTO.getCountry()).thenReturn(country);

        Optional<ValidationErrorDTO> result = validation.validate(agreementDTO);

        assertTrue(result.isEmpty());
        verify(errorFactory, never()).buildError(any());
    }
}