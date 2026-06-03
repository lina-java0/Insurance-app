package org.export.travel.insurance.core.blacklist;

import lombok.extern.slf4j.Slf4j;
import org.export.travel.insurance.core.api.dto.PersonDTO;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Profile({"h2"})
public class BlackListPersonCheckServiceStubImpl implements BlackListPersonCheckService {

    @Override
    public boolean isPersonBlackListed(PersonDTO personDTO) {
        log.info("BlackList stub invoked! Always return false!");
        return false;
    }
}
