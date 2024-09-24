package com.exist.ecc.limyu_exercise8.repository;

import com.exist.ecc.limyu_exercise8.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
}
