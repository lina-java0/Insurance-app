package org.javaguru.travel.insurance.core.repositories.entities;

import org.javaguru.travel.insurance.core.domain.entities.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PersonEntityRepository extends JpaRepository<PersonEntity, Long> {
    @Query("SELECT p FROM PersonEntity p " +
            "WHERE p.firstName = :firstName" +
            "      AND p.lastName = :lastName " +
            "      AND p.personCode = :personCode"
    )
    Optional<PersonEntity> findBy(
            @Param("firstName") String firstName,
            @Param("lastName") String lastName,
            @Param("personCode") String personCode
    );
}
