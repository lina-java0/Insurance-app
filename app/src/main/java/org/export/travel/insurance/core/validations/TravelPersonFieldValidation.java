package org.export.travel.insurance.core.validations;

import org.export.travel.insurance.core.api.dto.AgreementDTO;
import org.export.travel.insurance.core.api.dto.PersonDTO;
import org.export.travel.insurance.core.api.dto.ValidationErrorDTO;

import java.util.List;
import java.util.Optional;

public interface TravelPersonFieldValidation {

    Optional<ValidationErrorDTO> validate(AgreementDTO agreementDTO, PersonDTO person);

    List<ValidationErrorDTO> validateList(AgreementDTO agreementDTO, PersonDTO person);

}
