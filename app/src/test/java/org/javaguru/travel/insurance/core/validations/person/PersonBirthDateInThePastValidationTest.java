package org.javaguru.travel.insurance.core.validations.person;

import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.core.util.DateTimeUtil;
import org.javaguru.travel.insurance.core.validations.ValidationErrorFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonBirthDateInThePastValidationTest {

    @Mock
    private DateTimeUtil dateTimeUtil;

    @Mock
    private ValidationErrorFactory errorFactory;

    @InjectMocks
    private PersonBirthDateInThePastValidation validation;

    private static LocalDate toDate(String date) {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"01.01.2026", "10.10.2050"})
    @DisplayName("Should return error when birth date is in the future")
    void shouldReturnErrorWhenBirthDateIsInFuture(String date) {
        PersonDTO person = new PersonDTO();
        person.setPersonBirthDate(toDate(date));

        when(dateTimeUtil.getCurrentDate()).thenReturn(toDate("06.08.2025"));

        ValidationErrorDTO expectedError =
                new ValidationErrorDTO("ERROR_CODE_13", "Field personBirthDate must be in the past!");
        when(errorFactory.buildError("ERROR_CODE_13")).thenReturn(expectedError);

        Optional<ValidationErrorDTO> result = validation.validate(new AgreementDTO(), person);

        assertTrue(result.isPresent());
        assertEquals(expectedError, result.get());
        verify(errorFactory).buildError("ERROR_CODE_13");
    }

    @ParameterizedTest
    @ValueSource(strings = {"01.01.2000", "31.12.1990"})
    @DisplayName("Should not return error when birth date is in the past")
    void shouldNotReturnErrorWhenBirthDateIsInPast(String date) {
        PersonDTO person = new PersonDTO();
        person.setPersonBirthDate(toDate(date));

        when(dateTimeUtil.getCurrentDate()).thenReturn(toDate("06.08.2025"));

        Optional<ValidationErrorDTO> result = validation.validate(new AgreementDTO(), person);

        assertTrue(result.isEmpty());
        verifyNoInteractions(errorFactory);
    }
}