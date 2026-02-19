package org.javaguru.travel.insurance.rest.internal;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TravelGetAgreementRequestLogger {

    void log(String uuid) {
        log.info("REQUEST: agreement uuid = " + uuid);
    }
}
