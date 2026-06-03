package org.export.travel.insurance.core.messagebroker.proposal;

import org.export.travel.insurance.core.api.dto.AgreementDTO;

public interface ProposalGeneratorQueueSender {

    void send(AgreementDTO agreementDTO);
}
