package org.export.travel.insurance.core.messagebroker;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.export.travel.insurance.core.api.dto.AgreementDTO;
import org.springframework.amqp.AmqpException;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Profile({"h2", "mysql-local"})
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class ProposalGeneratorQueueSenderStubImpl implements ProposalGeneratorQueueSender {

    private final ObjectMapper objectMapper;

    @Override
    public void send(AgreementDTO agreement) {
        try {
            String json = objectMapper.writeValueAsString(agreement);
            log.info("PROPOSAL GENERATION message content: " + json);
        } catch (JsonProcessingException e) {
            log.error("Error to convert agreement to JSON", e);
        } catch (AmqpException e) {
            log.error("Error to sent proposal generation message", e);
        }
    }
}
