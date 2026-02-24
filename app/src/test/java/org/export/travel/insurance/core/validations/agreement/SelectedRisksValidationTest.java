package org.export.travel.insurance.core.validations.agreement;

import org.export.travel.insurance.core.api.dto.AgreementDTO;
import org.export.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.export.travel.insurance.core.domain.ClassifierValue;
import org.export.travel.insurance.core.repositories.ClassifierValueRepository;
import org.export.travel.insurance.core.validations.ValidationErrorFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SelectedRisksValidationTest {

    @Mock
    private ClassifierValueRepository classifierValueRepository;

    @Mock
    private ValidationErrorFactory errorFactory;

    @InjectMocks
    private SelectedRisksValidation validation;

    @Test
    @DisplayName("Should not return errors when selected risks are empty")
    void shouldNotReturnErrorsWhenSelectedRisksAreNull() {
        AgreementDTO agreement = mockAgreement(null);

        List<ValidationErrorDTO> result = validation.validateList(agreement);

        assertEquals(0, result.size());
    }

    @Test
    @DisplayName("Should not return errors when all selected risks exist")
    void shouldNotReturnErrorsWhenAllSelectedRisksExist() {
        AgreementDTO agreement = mockAgreement(List.of("R1", "R2"));
        when(classifierValueRepository.findByClassifierTitleAndIc("RISK_TYPE", "R1"))
                .thenReturn(Optional.of(mock(ClassifierValue.class)));
        when(classifierValueRepository.findByClassifierTitleAndIc("RISK_TYPE", "R2"))
                .thenReturn(Optional.of(mock(ClassifierValue.class)));

        List<ValidationErrorDTO> result = validation.validateList(agreement);

        assertEquals(0, result.size());
    }

    @Test
    @DisplayName("Should return error for non-existing risks")
    void shouldReturnErrorForNonExistingRisks() {
        AgreementDTO agreement = mockAgreement(List.of("R1", "R2"));
        when(classifierValueRepository.findByClassifierTitleAndIc("RISK_TYPE", "R1"))
                .thenReturn(Optional.empty());
        when(classifierValueRepository.findByClassifierTitleAndIc("RISK_TYPE", "R2"))
                .thenReturn(Optional.of(mock(ClassifierValue.class)));

        when(errorFactory.buildError(eq("ERROR_CODE_9"), anyList()))
                .thenReturn(mock(ValidationErrorDTO.class));

        List<ValidationErrorDTO> result = validation.validateList(agreement);

        assertEquals(1, result.size());
    }

    private AgreementDTO mockAgreement(List<String> selectedRisks) {
        AgreementDTO agreement = mock(AgreementDTO.class);
        when(agreement.getSelectedRisks()).thenReturn(selectedRisks);
        return agreement;
    }
}