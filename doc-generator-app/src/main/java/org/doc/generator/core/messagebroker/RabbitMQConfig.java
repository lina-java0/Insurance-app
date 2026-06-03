package org.doc.generator.core.messagebroker;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String QUEUE_PROPOSAL_GENERATION = "q.proposal-generation";
    public static final String QUEUE_PROPOSAL_GENERATION_ACK = "q.proposal-generation-ack";
    public static final String QUEUE_PROPOSAL_GENERATION_DLQ = "q.proposal-generation-dlq";

    @Bean
    public Queue createProposalPdfGenerationQueue() {
        return new Queue(QUEUE_PROPOSAL_GENERATION);
    }

    @Bean
    public Queue proposalPdfGenerationAckQueue() {
        return new Queue(QUEUE_PROPOSAL_GENERATION_ACK);
    }

    @Bean
    public Queue proposalPdfGenerationDeadLetterQueue() {
        return new Queue(QUEUE_PROPOSAL_GENERATION_DLQ);
    }
}
