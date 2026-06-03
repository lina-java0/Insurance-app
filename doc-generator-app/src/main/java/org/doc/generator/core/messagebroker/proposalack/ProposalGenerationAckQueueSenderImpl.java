package org.doc.generator.core.messagebroker.proposalack;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.doc.generator.core.api.dto.AgreementDTO;
import org.doc.generator.core.api.dto.ProposalGenerationAcknowledgment;
import org.doc.generator.core.messagebroker.RabbitMQConfig;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class ProposalGenerationAckQueueSenderImpl implements ProposalGenerationAckQueueSender {

    private final ObjectMapper mapper;
    private final RabbitTemplate rabbitTemplate;


    @Override
    public void send(AgreementDTO agreementDTO, String proposalFilePath) {
        ProposalGenerationAcknowledgment ackMessage =
                ProposalGenerationAcknowledgment.builder()
                        .agreementUuid(agreementDTO.getUuid())
                        .proposalFilePath(proposalFilePath)
                        .build();

        try {
            String json = mapper.writeValueAsString(ackMessage);
            log.info("PROPOSAL GENERATION ACK message content: " + json);
            rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE_PROPOSAL_GENERATION_ACK, json);
        } catch (JsonProcessingException e) {
            log.error("Error to convert proposal generation ack to JSON", e);
        } catch (AmqpException e) {
            log.error("Error sending proposal generation ack message", e);
        }
    }
}
