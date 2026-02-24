package org.export.travel.insurance.core.validations.agreement;

import org.export.travel.insurance.core.api.dto.AgreementDTO;
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
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmptySelectedRisksValidationTest {

    @Mock
    private ValidationErrorFactory errorFactory;

    @InjectMocks
    private EmptySelectedRisksValidation validation;

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("Should return error when selected risks are empty")
    void shouldReturnErrorWhenSelectedRisksAreInvalid(List<String> selectedRisks) {
        AgreementDTO agreementDTO = mockAgreement(selectedRisks);
        ValidationErrorDTO expectedError = new ValidationErrorDTO("ERROR_CODE_6", "Error description");
        when(errorFactory.buildError("ERROR_CODE_6")).thenReturn(expectedError);

        Optional<ValidationErrorDTO> result = validation.validate(agreementDTO);

        assertTrue(result.isPresent());
        assertEquals("ERROR_CODE_6", result.get().getErrorCode());
        assertEquals("Error description", result.get().getDescription());
        verify(errorFactory).buildError("ERROR_CODE_6");
    }

    static Stream<List<String>> validSelectedRisks() {
        return Stream.of(
                List.of("TRAVEL_MEDICAL"),
                List.of("TRAVEL_CANCELLATION"),
                List.of("TRAVEL_LOSS_BAGGAGE"),
                List.of("TRAVEL_THIRD_PARTY_LIABILITY"),
                List.of("TRAVEL_EVACUATION"),
                List.of("TRAVEL_SPORT_ACTIVITIES")
        );
    }

    @ParameterizedTest
    @MethodSource("validSelectedRisks")
    @DisplayName("Should not return error when selected risks are valid")
    void shouldNotReturnErrorWhenSelectedRisksAreValid(List<String> selectedRisks) {
        AgreementDTO agreementDTO = mockAgreement(selectedRisks);

        Optional<ValidationErrorDTO> result = validation.validate(agreementDTO);

        assertTrue(result.isEmpty());
        verifyNoInteractions(errorFactory);
    }

    private AgreementDTO mockAgreement(List<String> selectedRisks) {
        AgreementDTO agreement = mock(AgreementDTO.class);
        when(agreement.getSelectedRisks()).thenReturn(selectedRisks);
        return agreement;
    }
}