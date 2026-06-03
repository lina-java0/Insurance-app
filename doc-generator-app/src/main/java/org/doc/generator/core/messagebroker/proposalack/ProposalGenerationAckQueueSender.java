package org.doc.generator.core.messagebroker.proposalack;

import org.doc.generator.core.api.dto.AgreementDTO;

public interface ProposalGenerationAckQueueSender {

    void send(AgreementDTO agreementDTO, String proposalFilePath);
}
