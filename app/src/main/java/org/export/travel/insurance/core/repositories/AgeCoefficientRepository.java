package org.export.travel.insurance.core.repositories;

import org.export.travel.insurance.core.domain.AgeCoefficient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AgeCoefficientRepository extends JpaRepository<AgeCoefficient, Long> {

    @Query("SELECT ac FROM AgeCoefficient ac " +
            "WHERE ac.ageFrom <= :age " +
            "AND ac.ageTo >= :age")
    Optional<AgeCoefficient> findCoefficient(@Param("age") Integer age);
}
