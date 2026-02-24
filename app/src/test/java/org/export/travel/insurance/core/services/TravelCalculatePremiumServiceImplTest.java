package org.export.travel.insurance.core.services;

import org.export.travel.insurance.core.api.command.TravelCalculatePremiumCoreCommand;
import org.export.travel.insurance.core.api.command.TravelCalculatePremiumCoreResult;
import org.export.travel.insurance.core.api.dto.AgreementDTO;
import org.export.travel.insurance.core.api.dto.PersonDTO;
import org.export.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.export.travel.insurance.core.domain.entities.AgreementEntity;
import org.export.travel.insurance.core.messagebroker.ProposalGeneratorQueueSender;
import org.export.travel.insurance.core.validations.TravelAgreementValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TravelCalculatePremiumServiceImplTest {

    @Mock
    private TravelAgreementValidator agreementValidator;

    @Mock
    private AgreementPeoplePremiumCalculator peoplePremiumCalculator;

    @Mock
    private AgreementTotalPremiumCalculator totalPremiumCalculator;

    @InjectMocks
    private TravelCalculatePremiumServiceImpl premiumService;

    @Mock
    private AgreementEntityFactory agreementEntityFactory;

    @Mock
    private ProposalGeneratorQueueSender proposalGeneratorQueueSender;

    private AgreementDTO agreementDTO;
    private TravelCalculatePremiumCoreCommand command;

    @BeforeEach
    public void init() {
        agreementDTO = new AgreementDTO();
        PersonDTO personDTO = new PersonDTO();
        command = new TravelCalculatePremiumCoreCommand(agreementDTO);
        agreementDTO.setPeople(List.of(personDTO));
    }

    @Test
    @DisplayName("Should return response with errors when validation fails")
    void shouldReturnResponseWithErrorsWhenValidationFails() {
        List<ValidationErrorDTO> errors = List.of(new ValidationErrorDTO("error", "description"));
        when(agreementValidator.validate(agreementDTO)).thenReturn(errors);

        TravelCalculatePremiumCoreResult coreResult = premiumService.calculatePremium(command);

        assertEquals(errors, coreResult.getErrors());
        assertEquals(errors.getFirst().getErrorCode(), coreResult.getErrors().getFirst().getErrorCode());
        assertEquals(errors.getFirst().getDescription(), coreResult.getErrors().getFirst().getDescription());

        verifyNoInteractions(peoplePremiumCalculator, totalPremiumCalculator, agreementEntityFactory);
    }

    @Test
    @DisplayName("Should calculate premiums when validation passes")
    void shouldCalculatePremiumsWhenValidationPasses() {
        List<ValidationErrorDTO> errors = List.of();
        when(agreementValidator.validate(agreementDTO)).thenReturn(errors);

        when(totalPremiumCalculator.calculateTotalAgreementPremium(agreementDTO)).thenReturn(BigDecimal.ONE);

        var agreementEntity = new AgreementEntity();
        when(agreementEntityFactory.createAgreementEntity(agreementDTO)).thenReturn(agreementEntity);

        TravelCalculatePremiumCoreResult coreResult = premiumService.calculatePremium(command);

        verify(peoplePremiumCalculator).calculateRiskPremiumsForAllPeople(agreementDTO);
        verify(totalPremiumCalculator).calculateTotalAgreementPremium(agreementDTO);

        assertEquals(BigDecimal.ONE, agreementDTO.getAgreementPremium());

        assertEquals(agreementDTO, coreResult.getAgreement());
        assertNull(coreResult.getErrors());
    }

    @Test
    @DisplayName("Should save agreement")
    public void shouldSaveAgreement() {
        var agreementEntity = new AgreementEntity();
        when(agreementEntityFactory.createAgreementEntity(agreementDTO)).thenReturn(agreementEntity);

        when(agreementValidator.validate(agreementDTO)).thenReturn(Collections.emptyList());

        premiumService.calculatePremium(new TravelCalculatePremiumCoreCommand(agreementDTO));

        verify(agreementEntityFactory).createAgreementEntity(agreementDTO);
    }

}