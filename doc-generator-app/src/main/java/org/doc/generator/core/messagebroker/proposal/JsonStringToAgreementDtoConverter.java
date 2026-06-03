package org.doc.generator.core.messagebroker.proposal;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.doc.generator.core.api.dto.AgreementDTO;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class JsonStringToAgreementDtoConverter {

    private final ObjectMapper mapper;

    public AgreementDTO convert(String json) throws JsonProcessingException {
        return mapper.readValue(json, AgreementDTO.class);
    }
}
