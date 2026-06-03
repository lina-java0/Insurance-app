package org.blacklist.core.services;

import org.blacklist.core.api.command.BlackListedPersonCoreCommand;
import org.blacklist.core.api.command.BlackListedPersonCoreResult;
import org.blacklist.core.api.dto.BlackListedPersonDTO;
import org.blacklist.core.api.dto.ValidationErrorDTO;
import org.blacklist.core.domain.BlackListedPersonEntity;
import org.blacklist.core.repositories.BlackListedPersonEntityRepository;
import org.blacklist.core.validations.BlackListedPersonValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BlackListedPersonServiceImplTest {

    @Mock
    private BlackListedPersonValidator personValidator;

    @Mock
    private BlackListedPersonEntityRepository repository;

    @InjectMocks
    private BlackListedPersonServiceImpl service;

    private BlackListedPersonDTO personDTO;
    private BlackListedPersonCoreCommand command;

    @BeforeEach
    void init() {
        personDTO = new BlackListedPersonDTO();
        command = new BlackListedPersonCoreCommand(personDTO);
    }

    @Test
    @DisplayName("Should return errors when validation fails")
    void shouldReturnErrorsWhenValidationFails() {
        List<ValidationErrorDTO> errors =
                List.of(new ValidationErrorDTO("ERROR_CODE_1", "Invalid"));

        when(personValidator.validate(personDTO)).thenReturn(errors);

        BlackListedPersonCoreResult result = service.checkPerson(command);

        assertEquals(errors, result.getErrors());
        assertEquals("ERROR_CODE_1",
                result.getErrors().getFirst().getErrorCode());

        verifyNoInteractions(repository);
    }

    @Test
    @DisplayName("Should return true when person is blacklisted")
    void shouldReturnTrueWhenPersonIsBlacklisted() {
        personDTO.setPersonFirstName("John");
        personDTO.setPersonLastName("Doe");
        personDTO.setPersonCode("123");

        when(personValidator.validate(personDTO))
                .thenReturn(List.of());

        when(repository.findByFirstNameAndLastNameAndPersonCode(
                "John",
                "Doe",
                "123"
        )).thenReturn(Optional.of(new BlackListedPersonEntity()));

        BlackListedPersonCoreResult result = service.checkPerson(command);

        assertTrue(result.getPersonDTO().getBlackListed());

        verify(repository)
                .findByFirstNameAndLastNameAndPersonCode(
                        "John",
                        "Doe",
                        "123"
                );
    }

    @Test
    @DisplayName("Should return false when person is not blacklisted")
    void shouldReturnFalseWhenPersonIsNotBlacklisted() {
        personDTO.setPersonFirstName("Jane");
        personDTO.setPersonLastName("Doe");
        personDTO.setPersonCode("456");

        when(personValidator.validate(personDTO))
                .thenReturn(List.of());

        when(repository.findByFirstNameAndLastNameAndPersonCode(
                "Jane",
                "Doe",
                "456"
        )).thenReturn(Optional.empty());

        BlackListedPersonCoreResult result = service.checkPerson(command);

        assertFalse(result.getPersonDTO().getBlackListed());

        verify(repository)
                .findByFirstNameAndLastNameAndPersonCode(
                        "Jane",
                        "Doe",
                        "456"
                );
    }
}