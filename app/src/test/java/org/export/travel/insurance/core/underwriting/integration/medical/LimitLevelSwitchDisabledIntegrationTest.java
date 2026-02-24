package org.export.travel.insurance.core.underwriting.integration.medical;

import org.export.travel.insurance.core.api.dto.AgreementDTO;
import org.export.travel.insurance.core.api.dto.PersonDTO;
import org.export.travel.insurance.core.underwriting.TravelPremiumCalculationResult;
import org.export.travel.insurance.core.underwriting.TravelPremiumUnderwriting;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = {"medical.risk.limit.level.enabled=false"})
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class LimitLevelSwitchDisabledIntegrationTest {

    @Autowired
    private TravelPremiumUnderwriting premiumUnderwriting;

    @Test
    @DisplayName("Should be enabled medicalRiskLimitLevel")
    public void shouldBeEnabledMedicalRiskLimitLevel() {
        PersonDTO personDTO = PersonDTO.builder()
                .personFirstName("Fedor")
                .personLastName("Vencak")
                .personCode("123456-12345")
                .personBirthDate(createDate("16.08.1998"))
                .medicalRiskLimitLevel("LEVEL_10000")
                .build();

        AgreementDTO agreementDTO = AgreementDTO.builder()
                .agreementDateFrom(createDate("01.01.2030"))
                .agreementDateTo(createDate("05.01.2030"))
                .country("SPAIN")
                .selectedRisks(List.of("TRAVEL_MEDICAL"))
                .people(List.of(personDTO))
                .build();

        TravelPremiumCalculationResult result = premiumUnderwriting.calculatePremium(agreementDTO, personDTO);

        assertEquals(new BigDecimal("11.00"), result.totalPremium());
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
