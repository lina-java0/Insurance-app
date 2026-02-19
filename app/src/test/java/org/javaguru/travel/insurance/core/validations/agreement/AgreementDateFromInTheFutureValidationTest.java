package org.javaguru.travel.insurance.core.validations.agreement;

import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.core.util.DateTimeUtil;
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
class AgreementDateFromInTheFutureValidationTest {

    @Mock
    private DateTimeUtil dateTimeUtil;

    @Mock
    private ValidationErrorFactory errorFactory;

    @InjectMocks
    private AgreementDateFromInTheFutureValidation validation;

    @ParameterizedTest
    @MethodSource("provideInvalidDates")
    @DisplayName("Should return error when agreementDateFrom is in the past")
    void shouldReturnErrorForPastAgreementDateFrom(LocalDate invalidDate) {
        AgreementDTO agreementDTO = mock(AgreementDTO.class);
        when(agreementDTO.getAgreementDateFrom()).thenReturn(invalidDate);
        when(dateTimeUtil.getCurrentDate()).thenReturn(createDate("06.06.2025"));

        ValidationErrorDTO expectedError =
                new ValidationErrorDTO("ERROR_CODE_1", "Error description");
        when(errorFactory.buildError("ERROR_CODE_1")).thenReturn(expectedError);

        Optional<ValidationErrorDTO> result = validation.validate(agreementDTO);

        assertTrue(result.isPresent());
        assertEquals("ERROR_CODE_1", result.get().getErrorCode());
        assertEquals("Error description", result.get().getDescription());
        verify(errorFactory).buildError("ERROR_CODE_1");
    }

    @ParameterizedTest
    @MethodSource("provideValidDates")
    @DisplayName("Should not return error when agreementDateFrom is in the future")
    void shouldNotReturnErrorForFutureAgreementDateFrom(LocalDate validDate) {
        AgreementDTO agreementDTO = mock(AgreementDTO.class);
        when(agreementDTO.getAgreementDateFrom()).thenReturn(validDate);
        when(dateTimeUtil.getCurrentDate()).thenReturn(createDate("06.06.2025"));

        Optional<ValidationErrorDTO> result = validation.validate(agreementDTO);

        assertTrue(result.isEmpty());
        verifyNoInteractions(errorFactory);
    }

    static Stream<LocalDate> provideInvalidDates() {
        return Stream.of(
                createDate("01.01.2024")
        );
    }

    static Stream<LocalDate> provideValidDates() {
        return Stream.of(
                createDate("01.01.2026"),
                createDate("15.07.2027")
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