package org.javaguru.travel.insurance.core.services;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.api.dto.RiskDTO;
import org.javaguru.travel.insurance.core.domain.entities.AgreementEntity;
import org.javaguru.travel.insurance.core.domain.entities.AgreementPersonEntity;
import org.javaguru.travel.insurance.core.domain.entities.AgreementPersonRiskEntity;
import org.javaguru.travel.insurance.core.domain.entities.SelectedRiskEntity;
import org.javaguru.travel.insurance.core.repositories.entities.AgreementEntityRepository;
import org.javaguru.travel.insurance.core.repositories.entities.AgreementPersonEntityRepository;
import org.javaguru.travel.insurance.core.repositories.entities.AgreementPersonRiskEntityRepository;
import org.javaguru.travel.insurance.core.repositories.entities.SelectedRiskEntityRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class AgreementDTOLoader {

    private final AgreementEntityRepository agreementEntityRepository;
    private final AgreementPersonEntityRepository agreementPersonEntityRepository;
    private final AgreementPersonRiskEntityRepository agreementPersonRiskEntityRepository;
    private final SelectedRiskEntityRepository selectedRiskEntityRepository;

    AgreementDTO loadAgreement(String uuid) {
        AgreementEntity agreementEntity = agreementEntityRepository.findByUuid(uuid)
                .orElseThrow(() -> new IllegalArgumentException("Agreement not found for uuid = " + uuid));

        return toAgreementDTO(agreementEntity);
    }

    private AgreementDTO toAgreementDTO(AgreementEntity agreementEntity) {
        List<PersonDTO> people = agreementPersonEntityRepository.findByAgreement(agreementEntity)
                .stream()
                .map(this::toPersonDTO)
                .toList();

        List<String> selectedRisks = selectedRiskEntityRepository.findByAgreement(agreementEntity)
                .stream()
                .map(SelectedRiskEntity::getRiskIc)
                .toList();

        return AgreementDTO.builder()
                .uuid(agreementEntity.getUuid())
                .agreementDateFrom(agreementEntity.getAgreementDateFrom())
                .agreementDateTo(agreementEntity.getAgreementDateTo())
                .country(agreementEntity.getCountry())
                .agreementPremium(agreementEntity.getAgreementPremium())
                .people(people)
                .selectedRisks(selectedRisks)
                .build();
    }

    private PersonDTO toPersonDTO(AgreementPersonEntity personEntity) {
        List<RiskDTO> risks = agreementPersonRiskEntityRepository.findByAgreementPerson(personEntity)
                .stream()
                .map(this::toRiskDTO)
                .toList();

        return PersonDTO.builder()
                .personFirstName(personEntity.getPerson().getFirstName())
                .personLastName(personEntity.getPerson().getLastName())
                .personBirthDate(personEntity.getPerson().getBirthDate())
                .personCode(personEntity.getPerson().getPersonCode())
                .medicalRiskLimitLevel(personEntity.getMedicalRiskLimitLevel())
                .risks(risks)
                .build();
    }

    private RiskDTO toRiskDTO(AgreementPersonRiskEntity riskEntity) {
        return RiskDTO.builder()
                .riskIc(riskEntity.getRiskIc())
                .premium(riskEntity.getPremium())
                .build();
    }
}
