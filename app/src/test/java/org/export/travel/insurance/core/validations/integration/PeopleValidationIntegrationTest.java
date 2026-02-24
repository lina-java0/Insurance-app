package org.export.travel.insurance.core.validations.integration;

import org.export.travel.insurance.core.api.dto.AgreementDTO;
import org.export.travel.insurance.core.api.dto.PersonDTO;
import org.export.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.export.travel.insurance.core.validations.TravelAgreementValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PeopleValidationIntegrationTest {

    @Autowired
    private TravelAgreementValidator validator;

    @Test
    @DisplayName("Should not return error when all fields provided")
    public void shouldNotReturnErrorWhenAllFieldsProvided() {
        PersonDTO personDTO = PersonDTO.builder()
                .personFirstName("Fedor")
                .personLastName("Vencak")
                .personCode("123456-12345")
                .personBirthDate(createDate("16.08.1998"))
                .medicalRiskLimitLevel("LEVEL_10000")
                .build();

        AgreementDTO agreementDTO = AgreementDTO.builder()
                .agreementDateFrom(createDate("01.01.2030"))
                .agreementDateTo(createDate("01.01.2035"))
                .country("SPAIN")
                .selectedRisks(List.of("TRAVEL_MEDICAL"))
                .people(List.of(personDTO))
                .build();

        List<ValidationErrorDTO> errors = validator.validate(agreementDTO);

        assertEquals(0, errors.size());
    }

    @Test
    @DisplayName("Should return error when personFirstName is null")
    public void shouldReturnErrorWhenPersonFirstNameIsNull() {
        PersonDTO personDTO = PersonDTO.builder()
                .personFirstName(null)
                .personLastName("Vencak")
                .personCode("123456-12345")
                .personBirthDate(createDate("16.08.1998"))
                .medicalRiskLimitLevel("LEVEL_10000")
                .build();

        AgreementDTO agreementDTO = AgreementDTO.builder()
                .agreementDateFrom(createDate("01.01.2030"))
                .agreementDateTo(createDate("01.01.2035"))
                .country("SPAIN")
                .selectedRisks(List.of("TRAVEL_MEDICAL"))
                .people(List.of(personDTO))
                .build();

        List<ValidationErrorDTO> errors = validator.validate(agreementDTO);

        assertEquals(1, errors.size());
        assertEquals("ERROR_CODE_7", errors.get(0).getErrorCode());
        assertEquals("Field personFirstName must not be empty!", errors.get(0).getDescription());
    }

    @Test
    @DisplayName("Should return error when personLastName is null")
    public void shouldReturnErrorWhenPersonLastNameIsNull() {
        PersonDTO personDTO = PersonDTO.builder()
                .personFirstName("Fedor")
                .personLastName(null)
                .personCode("123456-12345")
                .personBirthDate(createDate("16.08.1998"))
                .medicalRiskLimitLevel("LEVEL_10000")
                .build();

        AgreementDTO agreementDTO = AgreementDTO.builder()
                .agreementDateFrom(createDate("01.01.2030"))
                .agreementDateTo(createDate("01.01.2035"))
                .country("SPAIN")
                .selectedRisks(List.of("TRAVEL_MEDICAL"))
                .people(List.of(personDTO))
                .build();

        List<ValidationErrorDTO> errors = validator.validate(agreementDTO);

        assertEquals(1, errors.size());
        assertEquals("ERROR_CODE_8", errors.get(0).getErrorCode());
        assertEquals("Field personLastName must not be empty!", errors.get(0).getDescription());
    }

    @Test
    @DisplayName("Should return error when person code is null")
    public void shouldReturnErrorWhenPersonCodeIsNull() {
        PersonDTO personDTO = PersonDTO.builder()
                .personFirstName("Fedor")
                .personLastName("Vencak")
                .personCode(null)
                .personBirthDate(createDate("16.08.1998"))
                .medicalRiskLimitLevel("LEVEL_10000")
                .build();

        AgreementDTO agreementDTO = AgreementDTO.builder()
                .agreementDateFrom(createDate("01.01.2030"))
                .agreementDateTo(createDate("01.01.2035"))
                .country("SPAIN")
                .selectedRisks(List.of("TRAVEL_MEDICAL"))
                .people(List.of(personDTO))
                .build();

        List<ValidationErrorDTO> errors = validator.validate(agreementDTO);

        assertEquals(1, errors.size());
        assertEquals("ERROR_CODE_16", errors.get(0).getErrorCode());
        assertEquals("Field personCode must not be empty!", errors.get(0).getDescription());
    }

    @Test
    @DisplayName("Should return error when birth date is null")
    public void shouldReturnErrorWhenBirthDateIsNull() {
        PersonDTO personDTO = PersonDTO.builder()
                .personFirstName("Fedor")
                .personLastName("Vencak")
                .personCode("123456-12345")
                .personBirthDate(null)
                .medicalRiskLimitLevel("LEVEL_10000")
                .build();

        AgreementDTO agreementDTO = AgreementDTO.builder()
                .agreementDateFrom(createDate("01.01.2030"))
                .agreementDateTo(createDate("01.01.2035"))
                .country("SPAIN")
                .selectedRisks(List.of("TRAVEL_MEDICAL"))
                .people(List.of(personDTO))
                .build();

        List<ValidationErrorDTO> errors = validator.validate(agreementDTO);

        assertEquals(1, errors.size());
        assertEquals("ERROR_CODE_12", errors.get(0).getErrorCode());
        assertEquals("Field personBirthDate must not be empty!", errors.get(0).getDescription());
    }

    @Test
    @DisplayName("Should return error when birth date is in the future")
    public void shouldReturnErrorWhenBirthDateIsInTheFuture() {
        PersonDTO personDTO = PersonDTO.builder()
                .personFirstName("Fedor")
                .personLastName("Vencak")
                .personCode("123456-12345")
                .personBirthDate(createDate("16.08.2033"))
                .medicalRiskLimitLevel("LEVEL_10000")
                .build();

        AgreementDTO agreementDTO = AgreementDTO.builder()
                .agreementDateFrom(createDate("01.01.2030"))
                .agreementDateTo(createDate("01.01.2035"))
                .country("SPAIN")
                .selectedRisks(List.of("TRAVEL_MEDICAL"))
                .people(List.of(personDTO))
                .build();

        List<ValidationErrorDTO> errors = validator.validate(agreementDTO);

        assertEquals(1, errors.size());
        assertEquals("ERROR_CODE_13", errors.get(0).getErrorCode());
        assertEquals("Field personBirthDate must be in the past!", errors.get(0).getDescription());
    }

    @Test
    @DisplayName("Should return error when medicalRiskLimitLevel is null")
    public void shouldReturnErrorWhenMedicalRiskLimitLevelIsNull() {
        PersonDTO personDTO = PersonDTO.builder()
                .personFirstName("Fedor")
                .personLastName("Vencak")
                .personCode("123456-12345")
                .personBirthDate(createDate("16.08.1998"))
                .medicalRiskLimitLevel(null)
                .build();

        AgreementDTO agreementDTO = AgreementDTO.builder()
                .agreementDateFrom(createDate("01.01.2030"))
                .agreementDateTo(createDate("01.01.2035"))
                .country("SPAIN")
                .selectedRisks(List.of("TRAVEL_MEDICAL"))
                .people(List.of(personDTO))
                .build();

        List<ValidationErrorDTO> errors = validator.validate(agreementDTO);

        assertEquals(1, errors.size());
        assertEquals("ERROR_CODE_14", errors.get(0).getErrorCode());
        assertEquals("Field medicalRiskLimitLevel must not be empty!", errors.get(0).getDescription());
    }

    @Test
    @DisplayName("Should return error when medicalRiskLimitLevel is not supported")
    public void shouldReturnErrorWhenMedicalRiskLimitLevelIsNotSupported() {
        PersonDTO personDTO = PersonDTO.builder()
                .personFirstName("Fedor")
                .personLastName("Vencak")
                .personCode("123456-12345")
                .personBirthDate(createDate("16.08.1998"))
                .medicalRiskLimitLevel("LEVEL_999999")
                .build();

        AgreementDTO agreementDTO = AgreementDTO.builder()
                .agreementDateFrom(createDate("01.01.2030"))
                .agreementDateTo(createDate("01.01.2035"))
                .country("SPAIN")
                .selectedRisks(List.of("TRAVEL_MEDICAL"))
                .people(List.of(personDTO))
                .build();

        List<ValidationErrorDTO> errors = validator.validate(agreementDTO);

        assertEquals(1, errors.size());
        assertEquals("ERROR_CODE_15", errors.get(0).getErrorCode());
        assertEquals("MedicalRiskLimitLevel value = LEVEL_999999 not supported!", errors.get(0).getDescription());
    }

    private static LocalDate createDate(String date) {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }
}