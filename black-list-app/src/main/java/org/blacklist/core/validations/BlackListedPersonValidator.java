package org.blacklist.core.validations;

import org.blacklist.core.api.dto.BlackListedPersonDTO;
import org.blacklist.core.api.dto.ValidationErrorDTO;

import java.util.List;

public interface BlackListedPersonValidator {

    List<ValidationErrorDTO> validate(BlackListedPersonDTO personDTO);
}
