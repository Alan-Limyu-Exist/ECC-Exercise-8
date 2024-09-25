package com.exist.ecc.limyu_exercise8.core.dao.repository;

import com.exist.ecc.limyu_exercise8.core.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
}
