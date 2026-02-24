package org.export.travel.insurance.core.repositories.entities;

import org.export.travel.insurance.core.domain.entities.AgreementEntity;
import org.export.travel.insurance.core.domain.entities.AgreementPersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AgreementPersonEntityRepository extends JpaRepository<AgreementPersonEntity, Long> {

    List<AgreementPersonEntity> findByAgreement(AgreementEntity agreement);
}
