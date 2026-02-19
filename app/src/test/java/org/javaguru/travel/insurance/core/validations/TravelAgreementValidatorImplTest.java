package org.javaguru.travel.insurance.core.validations;

import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TravelAgreementValidatorImplTest {

    @Mock
    private TravelAgreementFieldValidator agreementFieldValidator;

    @Mock
    private TravelPersonFieldValidator personFieldValidator;

    @InjectMocks
    private TravelAgreementValidatorImpl validator;

    private AgreementDTO agreementDTO;
    private PersonDTO personDTO1;
    private PersonDTO personDTO2;

    @BeforeEach
    void setUp() {
        personDTO1 = new PersonDTO();
        personDTO2 = new PersonDTO();
        agreementDTO = new AgreementDTO();
        agreementDTO.setPeople(List.of(personDTO1, personDTO2));
    }

    @Test
    @DisplayName("Should collect agreement and people errors when they exist")
    void shouldCollectAgreementAndPeopleErrorsWhenTheyExist() {
        ValidationErrorDTO agreementDTOError = new ValidationErrorDTO("agreementField", "agreementError");
        ValidationErrorDTO personDTO1Error = new ValidationErrorDTO("person1Field", "person1Error");
        ValidationErrorDTO personDTO2Error = new ValidationErrorDTO("person2Field", "person2Error");

        when(agreementFieldValidator.validate(agreementDTO)).thenReturn(List.of(agreementDTOError));
        when(personFieldValidator.validate(agreementDTO, personDTO1)).thenReturn(List.of(personDTO1Error));
        when(personFieldValidator.validate(agreementDTO, personDTO2)).thenReturn(List.of(personDTO2Error));

        List<ValidationErrorDTO> result = validator.validate(agreementDTO);

        assertEquals(List.of(agreementDTOError, personDTO1Error, personDTO2Error), result);

        verify(agreementFieldValidator).validate(agreementDTO);
        verify(personFieldValidator).validate(agreementDTO, personDTO1);
        verify(personFieldValidator).validate(agreementDTO, personDTO2);
    }

    @Test
    @DisplayName("Should return empty list when agreement and people errors do not exist")
    void shouldReturnEmptyListWhenAgreementAndPeopleErrorsDoNotExist() {
        when(agreementFieldValidator.validate(agreementDTO)).thenReturn(List.of());
        when(personFieldValidator.validate(agreementDTO, personDTO1)).thenReturn(List.of());
        when(personFieldValidator.validate(agreementDTO, personDTO2)).thenReturn(List.of());

        List<ValidationErrorDTO> result = validator.validate(agreementDTO);

        assertEquals(List.of(), result);

        verify(agreementFieldValidator).validate(agreementDTO);
        verify(personFieldValidator).validate(agreementDTO, personDTO1);
        verify(personFieldValidator).validate(agreementDTO, personDTO2);
    }
}
