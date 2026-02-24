package org.export.travel.insurance.core.services;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.export.travel.insurance.core.api.command.TravelCalculatePremiumCoreCommand;
import org.export.travel.insurance.core.api.command.TravelCalculatePremiumCoreResult;
import org.export.travel.insurance.core.api.dto.AgreementDTO;
import org.export.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.export.travel.insurance.core.domain.entities.AgreementEntity;
import org.export.travel.insurance.core.messagebroker.ProposalGeneratorQueueSender;
import org.export.travel.insurance.core.validations.TravelAgreementValidator;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class TravelCalculatePremiumServiceImpl implements TravelCalculatePremiumService {

    private final TravelAgreementValidator agreementValidator;
    private final AgreementPeoplePremiumCalculator peoplePremiumCalculator;
    private final AgreementTotalPremiumCalculator totalPremiumCalculator;
    private final AgreementEntityFactory agreementEntityFactory;
    private final ProposalGeneratorQueueSender proposalGeneratorQueueSender;

    @Override
    public TravelCalculatePremiumCoreResult calculatePremium(TravelCalculatePremiumCoreCommand command) {
        List<ValidationErrorDTO> errors = agreementValidator.validate(command.getAgreement());
        if (errors.isEmpty()) {
            calculatePremium(command.getAgreement());
            AgreementEntity agreement = agreementEntityFactory.createAgreementEntity(command.getAgreement());
            command.getAgreement().setUuid(agreement.getUuid());
            proposalGeneratorQueueSender.send(command.getAgreement());
            return buildResponse(command.getAgreement());
        } else {
            return buildResponse(errors);
        }
    }

    private void calculatePremium(AgreementDTO agreementDTO) {
        peoplePremiumCalculator.calculateRiskPremiumsForAllPeople(agreementDTO);
        agreementDTO.setAgreementPremium(totalPremiumCalculator.calculateTotalAgreementPremium(agreementDTO));
    }

    private TravelCalculatePremiumCoreResult buildResponse(List<ValidationErrorDTO> errors) {
        return new TravelCalculatePremiumCoreResult(errors);
    }

    private TravelCalculatePremiumCoreResult buildResponse(AgreementDTO agreementDTO) {
        return new TravelCalculatePremiumCoreResult(null, agreementDTO);
    }
}
