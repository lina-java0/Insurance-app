package org.javaguru.travel.insurance.core.validations.agreement;

import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.core.validations.ValidationErrorFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AgreementDateToValidationTest {

    @Mock
    private ValidationErrorFactory errorFactory;

    @InjectMocks
    private AgreementDateToValidation validation;

    @ParameterizedTest
    @MethodSource("provideInvalidDates")
    @DisplayName("Should return error when agreementDateTo is empty")
    void shouldReturnErrorForInvalidAgreementDateTo(LocalDate invalidDate) {
        AgreementDTO agreementDTO = mock(AgreementDTO.class);
        when(agreementDTO.getAgreementDateTo()).thenReturn(invalidDate);

        ValidationErrorDTO expectedError =
                new ValidationErrorDTO("ERROR_CODE_4", "Error description");
        when(errorFactory.buildError("ERROR_CODE_4")).thenReturn(expectedError);

        Optional<ValidationErrorDTO> result = validation.validate(agreementDTO);

        assertTrue(result.isPresent());
        assertEquals("ERROR_CODE_4", result.get().getErrorCode());
        assertEquals("Error description", result.get().getDescription());
        verify(errorFactory).buildError("ERROR_CODE_4");
    }

    @ParameterizedTest
    @MethodSource("provideValidDates")
    @DisplayName("Should not return error when agreementDateTo is valid")
    void shouldNotReturnErrorForValidAgreementDateTo(LocalDate validDate) {
        AgreementDTO agreementDTO = mock(AgreementDTO.class);
        when(agreementDTO.getAgreementDateTo()).thenReturn(validDate);

        Optional<ValidationErrorDTO> result = validation.validate(agreementDTO);

        assertTrue(result.isEmpty());
        verifyNoInteractions(errorFactory);
    }

    static Stream<LocalDate> provideInvalidDates() {
        return Stream.of((LocalDate) null);
    }

    static Stream<LocalDate> provideValidDates() {
        return Stream.of(
                createDate("10.01.2026"),
                createDate("20.12.2027")
        );
    }

    private static LocalDate createDate(String date) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            return LocalDate.parse(date, formatter);
        } catch (DateTimeParseException e) {
            throw new RuntimeException(e);
        }
    }
}