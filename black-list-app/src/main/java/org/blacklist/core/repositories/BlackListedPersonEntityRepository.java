package org.blacklist.core.repositories;

import org.blacklist.core.domain.BlackListedPersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BlackListedPersonEntityRepository extends JpaRepository<BlackListedPersonEntity, Long> {

    Optional<BlackListedPersonEntity>
    findByFirstNameAndLastNameAndPersonCode(
            String firstName,
            String lastName,
            String personCode
    );
}
