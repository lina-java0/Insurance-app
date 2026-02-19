package org.javaguru.travel.insurance.rest.v2;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.javaguru.travel.insurance.dto.v2.TravelCalculatePremiumResponseV2;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class TravelCalculatePremiumResponseLoggerV2 {

    private final ObjectMapper objectMapper;

    void log(TravelCalculatePremiumResponseV2 response) {
        try {
            String json = objectMapper.writeValueAsString(response);
            log.info("RESPONSE: {}", json);
        } catch (JsonProcessingException e) {
            log.error("Error to convert response to JSON", e);
        }
    }
}
