package org.export.travel.insurance.core.validations.agreement;

import org.export.travel.insurance.core.api.dto.AgreementDTO;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CountryValidationTest {

    @Mock
    private ClassifierValueRepository classifierValueRepository;

    @Mock
    private ValidationErrorFactory errorFactory;

    @InjectMocks
    private CountryValidation validation;

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   "})
    @DisplayName("Should not return error when country is empty")
    void shouldNotValidateWhenCountryEmpty(String country) {
        AgreementDTO agreementDTO = mock(AgreementDTO.class);
        when(agreementDTO.getCountry()).thenReturn(country);

        Optional<ValidationErrorDTO> result = validation.validate(agreementDTO);

        assertTrue(result.isEmpty());
        verifyNoInteractions(classifierValueRepository, errorFactory);
    }

    @ParameterizedTest
    @ValueSource(strings = {"SPAIN", "USA"})
    @DisplayName("Should not return error when country exists in classifier")
    void shouldNotReturnErrorWhenCountryExists(String country) {
        AgreementDTO agreementDTO = mock(AgreementDTO.class);
        when(agreementDTO.getCountry()).thenReturn(country);
        when(classifierValueRepository.findByClassifierTitleAndIc("COUNTRY", country))
                .thenReturn(Optional.of(mock(ClassifierValue.class)));

        Optional<ValidationErrorDTO> result = validation.validate(agreementDTO);

        assertTrue(result.isEmpty());
        verify(classifierValueRepository).findByClassifierTitleAndIc("COUNTRY", country);
        verifyNoInteractions(errorFactory);
    }

    @ParameterizedTest
    @ValueSource(strings = {"UNKNOWN", "INVALID"})
    @DisplayName("Should return error when country does not exist in classifier")
    void shouldReturnErrorWhenCountryNotFound(String country) {
        AgreementDTO agreementDTO = mock(AgreementDTO.class);
        when(agreementDTO.getCountry()).thenReturn(country);
        when(classifierValueRepository.findByClassifierTitleAndIc("COUNTRY", country))
                .thenReturn(Optional.empty());

        ValidationErrorDTO expectedError = new ValidationErrorDTO("ERROR_CODE_11", "Error description");
        when(errorFactory.buildError(eq("ERROR_CODE_11"), anyList())).thenReturn(expectedError);

        Optional<ValidationErrorDTO> result = validation.validate(agreementDTO);

        assertTrue(result.isPresent());
        assertEquals("ERROR_CODE_11", result.get().getErrorCode());
        assertEquals("Error description", result.get().getDescription());
        verify(errorFactory).buildError(eq("ERROR_CODE_11"), anyList());
    }
}