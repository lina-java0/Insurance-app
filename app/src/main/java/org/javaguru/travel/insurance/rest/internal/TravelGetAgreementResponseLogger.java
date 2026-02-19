package org.javaguru.travel.insurance.rest.internal;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.javaguru.travel.insurance.dto.internal.TravelGetAgreementResponse;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class TravelGetAgreementResponseLogger {

    private final ObjectMapper objectMapper;

    void log(TravelGetAgreementResponse response) {
        try {
            String json = objectMapper.writeValueAsString(response);
            log.info("RESPONSE: {}", json);
        } catch (JsonProcessingException e) {
            log.error("Error to convert response to JSON", e);
        }
    }
}
