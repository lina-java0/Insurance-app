package org.javaguru.travel.insurance.rest.v1;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumRequestV1;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class TravelCalculatePremiumRequestLoggerV1 {

    private final ObjectMapper objectMapper;

    void log(TravelCalculatePremiumRequestV1 request) {
        try {
            String json = objectMapper.writeValueAsString(request);
            log.info("REQUEST: {}", json);
        } catch (JsonProcessingException e) {
            log.error("Error to convert request to JSON", e);
        }
    }
}
