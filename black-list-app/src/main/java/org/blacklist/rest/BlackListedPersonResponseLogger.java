package org.blacklist.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.blacklist.dto.BlackListedPersonCheckResponse;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class BlackListedPersonResponseLogger {

    private final ObjectMapper objectMapper;

    void log(BlackListedPersonCheckResponse response) {
        try {
            String json = objectMapper.writeValueAsString(response);
            log.info("RESPONSE: {}", json);
        } catch (JsonProcessingException e) {
            log.error("Error to convert response to JSON", e);
        }
    }
}
