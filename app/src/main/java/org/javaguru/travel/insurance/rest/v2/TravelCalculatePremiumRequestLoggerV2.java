package org.javaguru.travel.insurance.rest.v2;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.javaguru.travel.insurance.dto.v2.TravelCalculatePremiumRequestV2;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class TravelCalculatePremiumRequestLoggerV2 {

    private final ObjectMapper objectMapper;

    void log(TravelCalculatePremiumRequestV2 request) {
        try {
            String json = objectMapper.writeValueAsString(request);
            log.info("REQUEST: {}", json);
        } catch (JsonProcessingException e) {
            log.error("Error to convert request to JSON", e);
        }
    }
}
