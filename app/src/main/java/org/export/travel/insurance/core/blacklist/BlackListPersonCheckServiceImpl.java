package org.export.travel.insurance.core.blacklist;

import lombok.extern.slf4j.Slf4j;

import org.export.travel.insurance.core.api.dto.PersonDTO;
import org.export.travel.insurance.core.blacklist.dto.BlackListedPersonCheckRequest;
import org.export.travel.insurance.core.blacklist.dto.BlackListedPersonCheckResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
@Profile({"mysql-container", "mysql-local"})
public class BlackListPersonCheckServiceImpl implements BlackListPersonCheckService{

    private final String personBlacklistedCheckUrl;
    private final RestTemplate restTemplate;

    BlackListPersonCheckServiceImpl(@Value("${person.blacklisted.check.url}")
                                    String personBlackListedCheckUrl,
                                    RestTemplate restTemplate) {
        this.personBlacklistedCheckUrl = personBlackListedCheckUrl;
        this.restTemplate = restTemplate;
    }


    @Override
    public boolean isPersonBlackListed(PersonDTO personDTO) {
        log.info("Blacklisted check for person with code " + personDTO.getPersonCode() + " started!");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        BlackListedPersonCheckRequest request = new BlackListedPersonCheckRequest();
        request.setPersonFirstName(personDTO.getPersonFirstName());
        request.setPersonLastName(personDTO.getPersonLastName());
        request.setPersonCode(personDTO.getPersonCode());

        HttpEntity<BlackListedPersonCheckRequest> requestHttpEntity = new HttpEntity<>(request, headers);

        ResponseEntity<BlackListedPersonCheckResponse> responseEntity = restTemplate.postForEntity(personBlacklistedCheckUrl, requestHttpEntity, BlackListedPersonCheckResponse.class);

        BlackListedPersonCheckResponse response = responseEntity.getBody();

        log.info("Blacklisted check for person with code " + personDTO.getPersonCode() + " return " + response.getBlacklisted());

        return response.getBlacklisted();
    }
}
