package org.export.travel.insurance.core.messagebroker.proposalack;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.export.travel.insurance.core.domain.entities.AgreementProposalAckEntity;
import org.export.travel.insurance.core.repositories.entities.AgreementProposalAckEntityRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
@Transactional
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class ProposalGenerationAckService {

    private final AgreementProposalAckEntityRepository proposalAckEntityRepository;

    public void process(ProposalGenerationAck proposalGenerationAck) {
        log.info("Start to process proposal ack: " + proposalGenerationAck.getAgreementUuid());

        AgreementProposalAckEntity ack =
                AgreementProposalAckEntity.builder()
                        .agreementUuid(proposalGenerationAck.getAgreementUuid())
                        .alreadyGenerated(true)
                        .proposalFilePath(proposalGenerationAck.getProposalFilePath())
                        .build();

        proposalAckEntityRepository.save(ack);

        log.info("Finish to process proposal ack: " + proposalGenerationAck.getAgreementUuid());
    }
}
