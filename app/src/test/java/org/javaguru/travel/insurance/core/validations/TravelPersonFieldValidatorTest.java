package org.javaguru.travel.insurance.core.validations;

import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
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
public class TravelPersonFieldValidatorTest {

    @Mock
    private TravelPersonFieldValidation validator1;

    @Mock
    private TravelPersonFieldValidation validator2;

    private TravelPersonFieldValidator personFieldValidator;

    @BeforeEach
    void setUp() {
        personFieldValidator = new TravelPersonFieldValidator(List.of(validator1, validator2));
    }

    static Stream<TestCase> invalidTestCases() {
        PersonDTO personDTO = new PersonDTO();
        AgreementDTO agreementDTO = new AgreementDTO();

        ValidationErrorDTO error1 = new ValidationErrorDTO("Field1", "Error1");
        ValidationErrorDTO error2 = new ValidationErrorDTO("Field2", "Error2");

        return Stream.of(
                new TestCase(
                        "Single + List errors combined",
                        personDTO,
                        agreementDTO,
                        List.of(
                                new ValidatorMock("validator1", Optional.of(error1), List.of()),
                                new ValidatorMock("validator2", Optional.empty(), List.of(error2))
                        ),
                        List.of(error1, error2)
                ),
                new TestCase(
                        "Single error",
                        personDTO,
                        agreementDTO,
                        List.of(
                                new ValidatorMock("validator1", Optional.of(error1), List.of()),
                                new ValidatorMock("validator2", Optional.empty(), List.of())
                        ),
                        List.of(error1)
                ),
                new TestCase(
                        "List errors",
                        personDTO,
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
        PersonDTO personDTO = new PersonDTO();
        AgreementDTO agreementDTO = new AgreementDTO();

        return Stream.of(
                new TestCase(
                        "No errors",
                        personDTO,
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
        PersonDTO personDTO = testCase.personDTO;
        AgreementDTO agreementDTO = testCase.agreementDTO;

        for (ValidatorMock vm : testCase.validatorMocks) {
            TravelPersonFieldValidation validator = vm.name.equals("validator1") ? validator1 : validator2;
            when(validator.validate(agreementDTO, personDTO)).thenReturn(vm.singleError());
            when(validator.validateList(agreementDTO, personDTO)).thenReturn(vm.listErrors());
        }

        List<ValidationErrorDTO> result = personFieldValidator.validate(agreementDTO, personDTO);

        assertEquals(testCase.expectedErrors(), result);

        for (ValidatorMock vm : testCase.validatorMocks) {
            TravelPersonFieldValidation validator = vm.name.equals("validator1") ? validator1 : validator2;
            verify(validator).validate(agreementDTO, personDTO);
            verify(validator).validateList(agreementDTO, personDTO);
        }
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("validTestCases")
    @DisplayName("Should return empty list when errors do not exist")
    void shouldReturnEmptyListWhenErrorsDoNotExist(TestCase testCase) {
        PersonDTO personDTO = testCase.personDTO;
        AgreementDTO agreementDTO = testCase.agreementDTO;

        for (ValidatorMock vm : testCase.validatorMocks) {
            TravelPersonFieldValidation validator = vm.name.equals("validator1") ? validator1 : validator2;
            when(validator.validate(agreementDTO, personDTO)).thenReturn(vm.singleError());
            when(validator.validateList(agreementDTO, personDTO)).thenReturn(vm.listErrors());
        }

        List<ValidationErrorDTO> result = personFieldValidator.validate(agreementDTO, personDTO);

        assertEquals(testCase.expectedErrors(), result);

        for (ValidatorMock vm : testCase.validatorMocks) {
            TravelPersonFieldValidation validator = vm.name.equals("validator1") ? validator1 : validator2;
            verify(validator).validate(agreementDTO, personDTO);
            verify(validator).validateList(agreementDTO, personDTO);
        }
    }

    private record ValidatorMock(
            String name,
            Optional<ValidationErrorDTO> singleError,
            List<ValidationErrorDTO> listErrors
    ) {}

    private record TestCase(
            String name,
            PersonDTO personDTO,
            AgreementDTO agreementDTO,
            List<ValidatorMock> validatorMocks,
            List<ValidationErrorDTO> expectedErrors
    ) {}
}
