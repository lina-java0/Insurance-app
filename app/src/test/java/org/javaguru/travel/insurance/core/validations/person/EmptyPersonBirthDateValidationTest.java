package org.javaguru.travel.insurance.core.validations.person;

import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
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
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmptyPersonBirthDateValidationTest {

    @Mock
    private ValidationErrorFactory errorFactory;

    @InjectMocks
    private EmptyPersonBirthDateValidation validation;

    static Stream<TestCase> invalidCases() {
        return Stream.of(new TestCase(null, "ERROR_CODE_12"));
    }

    @ParameterizedTest
    @MethodSource("invalidCases")
    @DisplayName("Should return error when person birth date is empty")
    void shouldReturnErrorWhenPersonBirthDateIsEmpty(TestCase testCase) {
        PersonDTO personDTO = mock(PersonDTO.class);
        when(personDTO.getPersonBirthDate()).thenReturn(testCase.birthDate());

        ValidationErrorDTO expectedError = new ValidationErrorDTO(testCase.expectedErrorCode(), "error description");
        when(errorFactory.buildError(testCase.expectedErrorCode())).thenReturn(expectedError);

        Optional<ValidationErrorDTO> result = validation.validate(new AgreementDTO(), personDTO);

        assertTrue(result.isPresent());
        assertEquals(testCase.expectedErrorCode(), result.get().getErrorCode());
        assertEquals("error description", result.get().getDescription());
        verify(errorFactory).buildError(testCase.expectedErrorCode());
    }

    static Stream<TestCase> validCases() {
        return Stream.of(new TestCase(createDate("01.01.2000"), null));
    }

    @ParameterizedTest
    @MethodSource("validCases")
    @DisplayName("Should not return error when person birth date is valid")
    void shouldNotReturnErrorWhenPersonBirthDateIsValid(TestCase testCase) {
        PersonDTO personDTO = mock(PersonDTO.class);
        when(personDTO.getPersonBirthDate()).thenReturn(testCase.birthDate());

        Optional<ValidationErrorDTO> result = validation.validate(new AgreementDTO(), personDTO);

        assertTrue(result.isEmpty());
        verifyNoInteractions(errorFactory);
    }

    private static LocalDate createDate(String date) {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    private record TestCase(LocalDate birthDate, String expectedErrorCode) {}
}