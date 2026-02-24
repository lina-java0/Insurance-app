package org.export.travel.insurance.core.validations.agreement;

import org.export.travel.insurance.core.api.dto.AgreementDTO;
import org.export.travel.insurance.core.api.dto.PersonDTO;
import org.export.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.export.travel.insurance.core.validations.ValidationErrorFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PeopleListNotEmptyValidationTest {

    @Mock
    private ValidationErrorFactory errorFactory;

    @InjectMocks
    private PeopleListNotEmptyValidation validation;

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("Should return errors when people list is empty")
    void shouldReturnErrorsWhenPeopleListIsEmptyOrNull(List<PersonDTO> people) {
        AgreementDTO agreement = mockAgreement(people);

        ValidationErrorDTO error7 = new ValidationErrorDTO("ERROR_CODE_7", "Error description 7");
        ValidationErrorDTO error8 = new ValidationErrorDTO("ERROR_CODE_8", "Error description 8");
        ValidationErrorDTO error16 = new ValidationErrorDTO("ERROR_CODE_16", "Error description 16");
        ValidationErrorDTO error12 = new ValidationErrorDTO("ERROR_CODE_12", "Error description 12");
        ValidationErrorDTO error14 = new ValidationErrorDTO("ERROR_CODE_14", "Error description 14");

        when(errorFactory.buildError("ERROR_CODE_7")).thenReturn(error7);
        when(errorFactory.buildError("ERROR_CODE_8")).thenReturn(error8);
        when(errorFactory.buildError("ERROR_CODE_16")).thenReturn(error16);
        when(errorFactory.buildError("ERROR_CODE_12")).thenReturn(error12);
        when(errorFactory.buildError("ERROR_CODE_14")).thenReturn(error14);

        List<ValidationErrorDTO> result = validation.validateList(agreement);

        assertEquals(5, result.size());
        assertTrue(result.containsAll(List.of(error7, error8, error16, error12, error14)));

        verify(errorFactory).buildError("ERROR_CODE_7");
        verify(errorFactory).buildError("ERROR_CODE_8");
        verify(errorFactory).buildError("ERROR_CODE_16");
        verify(errorFactory).buildError("ERROR_CODE_12");
        verify(errorFactory).buildError("ERROR_CODE_14");
    }

    static Stream<List<PersonDTO>> validPeopleLists() {
        return Stream.of(
                List.of(new PersonDTO()),
                List.of(new PersonDTO(), new PersonDTO())
        );
    }

    @ParameterizedTest
    @MethodSource("validPeopleLists")
    @DisplayName("Should not return errors when people list is valid")
    void shouldNotReturnErrorsWhenPeopleListIsValid(List<PersonDTO> people) {
        AgreementDTO agreement = mockAgreement(people);

        List<ValidationErrorDTO> result = validation.validateList(agreement);

        assertTrue(result.isEmpty());
        verifyNoInteractions(errorFactory);
    }

    private AgreementDTO mockAgreement(List<PersonDTO> people) {
        AgreementDTO agreement = mock(AgreementDTO.class);
        when(agreement.getPeople()).thenReturn(people);
        return agreement;
    }
}