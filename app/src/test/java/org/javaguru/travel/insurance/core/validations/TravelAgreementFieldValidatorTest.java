package org.javaguru.travel.insurance.core.validations;

import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TravelAgreementFieldValidatorTest {

    @Mock
    private TravelAgreementFieldValidation validator1;

    @Mock
    private TravelAgreementFieldValidation validator2;

    private TravelAgreementFieldValidator agreementFieldValidator;

    @BeforeEach
    void setUp() {
        agreementFieldValidator = new TravelAgreementFieldValidator(List.of(validator1, validator2));
    }

    static Stream<TestCase> invalidTestCases() {
        AgreementDTO agreementDTO = new AgreementDTO();

        ValidationErrorDTO error1 = new ValidationErrorDTO("Field1", "Error1");
        ValidationErrorDTO error2 = new ValidationErrorDTO("Field2", "Error2");

        return Stream.of(
                new TestCase(
                        "Single + List errors combined",
                        agreementDTO,
                        List.of(
                                new ValidatorMock("validator1", Optional.of(error1), List.of()),
                                new ValidatorMock("validator2", Optional.empty(), List.of(error2))
                        ),
                        List.of(error1, error2)
                ),
                new TestCase(
                        "Single error",
                        agreementDTO,
                        List.of(
                                new ValidatorMock("validator1", Optional.of(error1), List.of()),
                                new ValidatorMock("validator2", Optional.empty(), List.of())
                        ),
                        List.of(error1)
                ),
                new TestCase(
                        "List errors",
                        agreementDTO,
                        List.of(
                                new ValidatorMock("validator1", Optional.empty(), List.of()),
                                new ValidatorMock("validator2", Optional.empty(), List.of(error1, error2))
                        ),
                        List.of(error1, error2)
                )
        );
    }

    static Stream<TestCase> validTestCases() {
        AgreementDTO agreementDTO = new AgreementDTO();

        return Stream.of(
                new TestCase(
                        "No errors",
                        agreementDTO,
                        List.of(
                                new ValidatorMock("validator1", Optional.empty(), List.of()),
                                new ValidatorMock("validator2", Optional.empty(), List.of())
                        ),
                        List.of()
                )
        );
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("invalidTestCases")
    @DisplayName("Should collect all errors when they exist")
    void shouldCollectAllErrorsWhenTheyExist(TestCase testCase) {
        AgreementDTO agreementDTO = testCase.agreementDTO;

        for (ValidatorMock vm : testCase.validatorMocks) {
            TravelAgreementFieldValidation validator = vm.name.equals("validator1") ? validator1 : validator2;
            when(validator.validate(agreementDTO)).thenReturn(vm.singleError());
            when(validator.validateList(agreementDTO)).thenReturn(vm.listErrors());
        }

        List<ValidationErrorDTO> result = agreementFieldValidator.validate(agreementDTO);

        assertEquals(testCase.expectedErrors(), result);

        for (ValidatorMock vm : testCase.validatorMocks) {
            TravelAgreementFieldValidation validator = vm.name.equals("validator1") ? validator1 : validator2;
            verify(validator).validate(agreementDTO);
            verify(validator).validateList(agreementDTO);
        }
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("validTestCases")
    @DisplayName("Should return empty list when errors do not exist")
    void shouldReturnEmptyListWhenErrorsDoNotExist(TestCase testCase) {
        AgreementDTO agreementDTO = testCase.agreementDTO;

        for (ValidatorMock vm : testCase.validatorMocks) {
            TravelAgreementFieldValidation validator = vm.name.equals("validator1") ? validator1 : validator2;
            when(validator.validate(agreementDTO)).thenReturn(vm.singleError());
            when(validator.validateList(agreementDTO)).thenReturn(vm.listErrors());
        }

        List<ValidationErrorDTO> result = agreementFieldValidator.validate(agreementDTO);

        assertEquals(testCase.expectedErrors(), result);

        for (ValidatorMock vm : testCase.validatorMocks) {
            TravelAgreementFieldValidation validator = vm.name.equals("validator1") ? validator1 : validator2;
            verify(validator).validate(agreementDTO);
            verify(validator).validateList(agreementDTO);
        }
    }

    private record ValidatorMock(
            String name,
            Optional<ValidationErrorDTO> singleError,
            List<ValidationErrorDTO> listErrors
    ) {}

    private record TestCase(
            String name,
            AgreementDTO agreementDTO,
            List<ValidatorMock> validatorMocks,
            List<ValidationErrorDTO> expectedErrors
    ) {}
}
