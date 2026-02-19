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
class DateFromShouldBeEarlierThanDateToValidationTest {

    @Mock
    private ValidationErrorFactory errorFactory;

    @InjectMocks
    private DateFromShouldBeEarlierThanDateToValidation validation;

    @ParameterizedTest
    @MethodSource("provideInvalidDatePairs")
    @DisplayName("Should return error when agreementDateFrom is not earlier than agreementDateTo")
    void shouldReturnErrorForInvalidDateOrder(LocalDate dateFrom, LocalDate dateTo) {
        AgreementDTO agreementDTO = mock(AgreementDTO.class);
        when(agreementDTO.getAgreementDateFrom()).thenReturn(dateFrom);
        when(agreementDTO.getAgreementDateTo()).thenReturn(dateTo);

        ValidationErrorDTO expectedError =
                new ValidationErrorDTO("ERROR_CODE_5", "Error description");
        when(errorFactory.buildError("ERROR_CODE_5")).thenReturn(expectedError);

        Optional<ValidationErrorDTO> result = validation.validate(agreementDTO);

        assertTrue(result.isPresent());
        assertEquals("ERROR_CODE_5", result.get().getErrorCode());
        assertEquals("Error description", result.get().getDescription());
        verify(errorFactory).buildError("ERROR_CODE_5");
    }

    @ParameterizedTest
    @MethodSource("provideValidDatePairs")
    @DisplayName("Should not return error when agreementDateFrom is earlier than agreementDateTo")
    void shouldNotReturnErrorForValidDateOrder(LocalDate dateFrom, LocalDate dateTo) {
        AgreementDTO agreementDTO = mock(AgreementDTO.class);
        when(agreementDTO.getAgreementDateFrom()).thenReturn(dateFrom);
        when(agreementDTO.getAgreementDateTo()).thenReturn(dateTo);

        Optional<ValidationErrorDTO> result = validation.validate(agreementDTO);

        assertTrue(result.isEmpty());
        verifyNoInteractions(errorFactory);
    }

    static Stream<LocalDate[]> provideInvalidDatePairs() {
        return Stream.of(
                new LocalDate[]{createDate("10.01.2026"), createDate("01.01.2026")},
                new LocalDate[]{createDate("10.01.2026"), createDate("10.01.2026")}
        );
    }

    static Stream<LocalDate[]> provideValidDatePairs() {
        return Stream.of(
                new LocalDate[]{createDate("01.01.2026"), createDate("10.01.2026")},
                new LocalDate[]{createDate("05.05.2025"), createDate("06.06.2025")}
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