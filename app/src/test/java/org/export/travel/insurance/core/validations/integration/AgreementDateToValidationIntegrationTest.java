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
import java.time.format.DateTimeParseException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AgreementDateToValidationIntegrationTest {

    @Autowired
    private TravelAgreementValidator validator;

    @Test
    @DisplayName("Should not return error when dateTo is valid")
    public void shouldNotReturnErrorWhenDateToIsValid() {
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
    @DisplayName("Should return error when dateTo is null")
    public void shouldReturnErrorWhenDateToIsNull() {
        PersonDTO personDTO = PersonDTO.builder()
                .personFirstName("Fedor")
                .personLastName("Vencak")
                .personCode("123456-12345")
                .personBirthDate(createDate("16.08.1998"))
                .medicalRiskLimitLevel("LEVEL_10000")
                .build();

        AgreementDTO agreementDTO = AgreementDTO.builder()
                .agreementDateFrom(createDate("01.01.2030"))
                .agreementDateTo(null)
                .country("SPAIN")
                .selectedRisks(List.of("TRAVEL_MEDICAL"))
                .people(List.of(personDTO))
                .build();

        List<ValidationErrorDTO> errors = validator.validate(agreementDTO);

        assertEquals(1, errors.size());
        assertEquals("ERROR_CODE_4", errors.get(0).getErrorCode());
        assertEquals("Field agreementDateTo must not be empty!", errors.get(0).getDescription());
    }

    @Test
    @DisplayName("Should return error when dateTo is in the past")
    public void shouldReturnErrorWhenDateToIsInThePast() {
        PersonDTO personDTO = PersonDTO.builder()
                .personFirstName("Fedor")
                .personLastName("Vencak")
                .personCode("123456-12345")
                .personBirthDate(createDate("16.08.1998"))
                .medicalRiskLimitLevel("LEVEL_10000")
                .build();

        AgreementDTO agreementDTO = AgreementDTO.builder()
                .agreementDateFrom(createDate("01.01.2030"))
                .agreementDateTo(createDate("01.01.2020"))
                .country("SPAIN")
                .selectedRisks(List.of("TRAVEL_MEDICAL"))
                .people(List.of(personDTO))
                .build();

        List<ValidationErrorDTO> errors = validator.validate(agreementDTO);

        assertEquals(2, errors.size());
        assertEquals("ERROR_CODE_3", errors.get(0).getErrorCode());
        assertEquals("Field agreementDateTo must be in the future!", errors.get(0).getDescription());
        assertEquals("ERROR_CODE_5", errors.get(1).getErrorCode());
        assertEquals("Field agreementDateFrom must be earlier than agreementDateTo!", errors.get(1).getDescription());
    }

    @Test
    @DisplayName("Should return error when dateFrom is after dateTo")
    public void shouldReturnErrorWhenDateFromIsAfterDateTo() {
        PersonDTO personDTO = PersonDTO.builder()
                .personFirstName("Fedor")
                .personLastName("Vencak")
                .personCode("123456-12345")
                .personBirthDate(createDate("16.08.1998"))
                .medicalRiskLimitLevel("LEVEL_10000")
                .build();

        AgreementDTO agreementDTO = AgreementDTO.builder()
                .agreementDateFrom(createDate("01.01.2035"))
                .agreementDateTo(createDate("01.01.2030"))
                .country("SPAIN")
                .selectedRisks(List.of("TRAVEL_MEDICAL"))
                .people(List.of(personDTO))
                .build();

        List<ValidationErrorDTO> errors = validator.validate(agreementDTO);

        assertEquals(1, errors.size());
        assertEquals("ERROR_CODE_5", errors.get(0).getErrorCode());
        assertEquals("Field agreementDateFrom must be earlier than agreementDateTo!", errors.get(0).getDescription());
    }

    private static LocalDate createDate(String date) {
        try {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            return LocalDate.parse(date, dateTimeFormatter);
        } catch (DateTimeParseException e) {
            throw new RuntimeException(e);
        }
    }
}