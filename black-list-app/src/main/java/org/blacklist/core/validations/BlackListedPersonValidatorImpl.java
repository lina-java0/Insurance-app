package org.blacklist.core.validations;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.blacklist.core.api.dto.BlackListedPersonDTO;
import org.blacklist.core.api.dto.ValidationErrorDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class BlackListedPersonValidatorImpl implements BlackListedPersonValidator {

    private final ValidationErrorFactory errorFactory;

    @Override
    public List<ValidationErrorDTO> validate(BlackListedPersonDTO personDTO) {
        return Stream.of(
                        validatePersonFirstName(personDTO),
                        validatePersonLastName(personDTO),
                        validatePersonCode(personDTO)
                ).flatMap(Optional::stream)
                .toList();
    }

    private Optional<ValidationErrorDTO> validatePersonFirstName(BlackListedPersonDTO personDTO) {
        return (personDTO.getPersonFirstName() == null || personDTO.getPersonFirstName().isEmpty())
                ? Optional.of(errorFactory.buildError("ERROR_CODE_1"))
                : Optional.empty();
    }

    private Optional<ValidationErrorDTO> validatePersonLastName(BlackListedPersonDTO personDTO) {
        return (personDTO.getPersonLastName() == null || personDTO.getPersonLastName().isEmpty())
                ? Optional.of(errorFactory.buildError("ERROR_CODE_2"))
                : Optional.empty();
    }

    private Optional<ValidationErrorDTO> validatePersonCode(BlackListedPersonDTO personDTO) {
        return (personDTO.getPersonCode() == null || personDTO.getPersonCode().isEmpty())
                ? Optional.of(errorFactory.buildError("ERROR_CODE_3"))
                : Optional.empty();
    }
}
