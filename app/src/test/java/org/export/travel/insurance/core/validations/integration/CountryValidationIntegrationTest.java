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
public class CountryValidationIntegrationTest {

    @Autowired
    private TravelAgreementValidator validator;

    @Test
    @DisplayName("Should not return error when country is valid")
    public void shouldNotReturnErrorWhenCountryIsValid() {
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
    @DisplayName("Should return error when country is null")
    public void shouldReturnErrorWhenCountryIsNull() {
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
                .country(null)
                .selectedRisks(List.of("TRAVEL_MEDICAL"))
                .people(List.of(personDTO))
                .build();

        List<ValidationErrorDTO> errors = validator.validate(agreementDTO);

        assertEquals(1, errors.size());
        assertEquals("ERROR_CODE_10", errors.get(0).getErrorCode());
        assertEquals("Field country must not be empty!", errors.get(0).getDescription());
    }

    @Test
    @DisplayName("Should return error when country is not supported")
    public void shouldReturnErrorWhenCountryIsNotSupported() {
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
                .country("MARS")
                .selectedRisks(List.of("TRAVEL_MEDICAL"))
                .people(List.of(personDTO))
                .build();

        List<ValidationErrorDTO> errors = validator.validate(agreementDTO);

        assertEquals(1, errors.size());
        assertEquals("ERROR_CODE_11", errors.get(0).getErrorCode());
        assertEquals("Country value = MARS not supported!", errors.get(0).getDescription());
    }

    private static LocalDate createDate(String date) {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }
}