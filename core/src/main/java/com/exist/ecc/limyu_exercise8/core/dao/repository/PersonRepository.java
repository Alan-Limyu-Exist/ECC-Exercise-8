package com.exist.ecc.limyu_exercise8.core.dao.repository;

import com.exist.ecc.limyu_exercise8.core.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    default Optional<Person> findByUuid(UUID uuid) {
        return this.findAll().stream()
                .filter(person -> person.getUuid().equals(uuid))
                .findFirst();
    }

}
