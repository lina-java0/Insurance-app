package org.export.travel.insurance.core.blacklist;

import org.export.travel.insurance.core.api.dto.PersonDTO;

public interface BlackListPersonCheckService {

    boolean isPersonBlackListed (PersonDTO personDTO);
}
