package org.export.travel.insurance.core.repositories.entities;

import org.export.travel.insurance.core.domain.entities.AgreementPersonEntity;
import org.export.travel.insurance.core.domain.entities.AgreementPersonRiskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AgreementPersonRiskEntityRepository extends JpaRepository<AgreementPersonRiskEntity, Long> {

    List<AgreementPersonRiskEntity> findByAgreementPerson (AgreementPersonEntity agreementPersonEntity);
}
