package org.javaguru.travel.insurance.core.repositories;

import org.javaguru.travel.insurance.core.domain.Classifier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@DataJpaTest
class ClassifierRepositoryTest {

    @Autowired private ClassifierRepository classifierRepository;

    @Test
    @DisplayName("Injected repository is not null")
    public void injectedRepositoryAreNotNull() {
        assertNotNull(classifierRepository);
    }

    @ParameterizedTest
    @DisplayName("Should find classifier with supported title")
    @CsvSource({
          "RISK_TYPE"
    })
    public void shouldFindClassifierWithSupportedTitle(String title) {
        Optional<Classifier> classifierOpt = classifierRepository.findByTitle(title);
        assertTrue(classifierOpt.isPresent());
        assertEquals(title, classifierOpt.get().getTitle());
    }

    @ParameterizedTest
    @DisplayName("Should not find classifier with unsupported title")
    @CsvSource({
            "RISKS_TYPE",
            "NOT_EXISTING",
            "UNKNOWN"
    })
    public void shouldNotFindClassifierWithUnsupportedTitle(String fakeTitle) {
        Optional<Classifier> classifierOpt = classifierRepository.findByTitle(fakeTitle);
        assertTrue(classifierOpt.isEmpty());
    }
}