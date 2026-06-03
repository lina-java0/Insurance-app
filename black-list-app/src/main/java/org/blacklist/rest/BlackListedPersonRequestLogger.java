package org.blacklist.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.blacklist.dto.BlackListedPersonCheckRequest;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class BlackListedPersonRequestLogger {

    private final ObjectMapper objectMapper;

    void log(BlackListedPersonCheckRequest request) {
        try {
            String json = objectMapper.writeValueAsString(request);
            log.info("REQUEST: {}", json);
        } catch (JsonProcessingException e) {
            log.error("Error to convert request to JSON", e);
        }
    }
}
