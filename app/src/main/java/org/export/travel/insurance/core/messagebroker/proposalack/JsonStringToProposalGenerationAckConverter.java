package org.export.travel.insurance.core.messagebroker.proposalack;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class JsonStringToProposalGenerationAckConverter {

    private final ObjectMapper mapper;

    public ProposalGenerationAck convert(String json) throws JsonProcessingException {
        return mapper.readValue(json, ProposalGenerationAck.class);
    }
}
