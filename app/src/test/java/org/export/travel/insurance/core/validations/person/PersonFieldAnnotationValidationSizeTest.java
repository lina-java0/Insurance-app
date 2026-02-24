package org.export.travel.insurance.core.validations.person;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.export.travel.insurance.core.api.dto.AgreementDTO;
import org.export.travel.insurance.core.api.dto.PersonDTO;
import org.export.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.export.travel.insurance.core.validations.ValidationErrorFactory;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonFieldAnnotationValidationSizeTest {

    private static ValidatorFactory factory;
    private Validator validator;
    private ValidationErrorFactory errorFactory;
    private PersonFieldAnnotationValidation validation;

    @BeforeAll
    static void initFactory() {
        factory = Validation.buildDefaultValidatorFactory();
    }

    @AfterAll
    static void closeFactory() {
        factory.close();
    }

    @BeforeEach
    void setup() {
        validator = factory.getValidator();
        errorFactory = mock(ValidationErrorFactory.class);
        validation = new PersonFieldAnnotationValidation(errorFactory, validator);
    }

    @Test
    @DisplayName("Should return error when firstName exceeds 200 characters")
    void shouldReturnErrorWhenFirstNameExceeds200Characters() {
        AgreementDTO agreementDTO = new AgreementDTO();
        PersonDTO personDTO = new PersonDTO();
        personDTO.setPersonFirstName("A".repeat(201));
        personDTO.setPersonLastName("B".repeat(1));

        ValidationErrorDTO expectedError = new ValidationErrorDTO("ERROR_CODE_23", "Error description");
        when(errorFactory.buildError(eq("ERROR_CODE_23"), anyList())).thenReturn(expectedError);

        List<ValidationErrorDTO> errors = validation.validateList(agreementDTO, personDTO);

        assertFalse(errors.isEmpty());
        assertTrue(errors.stream().anyMatch(e -> e.getErrorCode().equals("ERROR_CODE_23")));
        verify(errorFactory, atLeastOnce()).buildError(eq("ERROR_CODE_23"), anyList());
    }

    @Test
    @DisplayName("Should return error when lastName exceeds 200 characters")
    void shouldReturnErrorWhenLastNameExceeds200Characters() {
        AgreementDTO agreementDTO = new AgreementDTO();
        PersonDTO personDTO = new PersonDTO();
        personDTO.setPersonFirstName("A".repeat(1));
        personDTO.setPersonLastName("B".repeat(201));

        ValidationErrorDTO expectedError = new ValidationErrorDTO("ERROR_CODE_23", "Error description");
        when(errorFactory.buildError(eq("ERROR_CODE_23"), anyList())).thenReturn(expectedError);

        List<ValidationErrorDTO> errors = validation.validateList(agreementDTO, personDTO);

        assertFalse(errors.isEmpty());
        assertTrue(errors.stream().anyMatch(e -> e.getErrorCode().equals("ERROR_CODE_23")));
        verify(errorFactory, atLeastOnce()).buildError(eq("ERROR_CODE_23"), anyList());
    }

    @Test
    @DisplayName("Should not return error when firstName and lastName are within 200 characters")
    void shouldNotReturnErrorWhenNameWithinLimit() {
        AgreementDTO agreementDTO = new AgreementDTO();
        PersonDTO personDTO = new PersonDTO();
        personDTO.setPersonFirstName("A".repeat(200));
        personDTO.setPersonLastName("B".repeat(200));

        List<ValidationErrorDTO> errors = validation.validateList(agreementDTO, personDTO);

        assertTrue(errors.isEmpty());
        verifyNoInteractions(errorFactory);
    }
}