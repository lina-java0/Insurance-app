package org.javaguru.travel.insurance.core.validations.integration;

import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.core.validations.TravelAgreementValidator;
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
public class SelectedRisksValidationIntegrationTest {

    @Autowired
    private TravelAgreementValidator validator;

    @Test
    @DisplayName("Should not return error when selected risks are valid")
    public void shouldNotReturnErrorWhenSelectedRisksAreValid() {
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
    @DisplayName("Should return error when selected risks are empty")
    public void shouldReturnErrorWhenSelectedRisksAreEmpty() {
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
                .people(List.of(personDTO))
                .build();

        List<ValidationErrorDTO> errors = validator.validate(agreementDTO);

        assertEquals(1, errors.size());
        assertEquals("ERROR_CODE_6", errors.get(0).getErrorCode());
        assertEquals("Field selectedRisks must not be empty!", errors.get(0).getDescription());
    }

    @Test
    @DisplayName("Should return error when risk type not supported")
    public void shouldReturnErrorWhenRiskTypeNotSupported() {
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
                .selectedRisks(List.of("ALIEN_ATTACK"))
                .people(List.of(personDTO))
                .build();

        List<ValidationErrorDTO> errors = validator.validate(agreementDTO);

        assertEquals(1, errors.size());
        assertEquals("ERROR_CODE_9", errors.get(0).getErrorCode());
        assertEquals("Risk Type ic = ALIEN_ATTACK not supported!", errors.get(0).getDescription());
    }

    private static LocalDate createDate(String date) {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }
}