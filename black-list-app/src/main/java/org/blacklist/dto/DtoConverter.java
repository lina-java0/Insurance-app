package org.blacklist.dto;

import org.blacklist.core.api.command.BlackListedPersonCoreCommand;
import org.blacklist.core.api.command.BlackListedPersonCoreResult;
import org.blacklist.core.api.dto.BlackListedPersonDTO;
import org.blacklist.core.api.dto.ValidationErrorDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DtoConverter {

    public BlackListedPersonCoreCommand buildCoreCommand(BlackListedPersonCheckRequest request) {
        BlackListedPersonDTO personDTO = buildPerson(request);
        return new BlackListedPersonCoreCommand(personDTO);
    }

    public BlackListedPersonCheckResponse buildResponse(BlackListedPersonCoreResult coreResult) {
        return coreResult.hasErrors()
                ? buildResponseWithErrors(coreResult.getErrors())
                : buildSuccessfulResponse(coreResult);
    }

    private BlackListedPersonCheckResponse buildResponseWithErrors(List<ValidationErrorDTO> coreErrors) {
        List<ValidationError> errors = transformValidationErrors(coreErrors);
        return new BlackListedPersonCheckResponse(errors);
    }

    private List<ValidationError> transformValidationErrors(List<ValidationErrorDTO> coreErrors) {
        return coreErrors.stream()
                .map(error -> new ValidationError(error.getErrorCode(), error.getDescription()))
                .toList();
    }

    private BlackListedPersonCheckResponse buildSuccessfulResponse(BlackListedPersonCoreResult coreResult) {
        BlackListedPersonDTO personDTO = coreResult.getPersonDTO();

        return BlackListedPersonCheckResponse.builder()
                .personFirstName(personDTO.getPersonFirstName())
                .personLastName(personDTO.getPersonLastName())
                .personCode(personDTO.getPersonCode())
                .blacklisted(personDTO.getBlackListed())
                .build();
    }

    private BlackListedPersonDTO buildPerson(BlackListedPersonCheckRequest request) {
        return BlackListedPersonDTO.builder()
                .personFirstName(request.getPersonFirstName())
                .personLastName(request.getPersonLastName())
                .personCode(request.getPersonCode())
                .build();
    }
}
