package com.exist.ecc.limyu_exercise8.core.service;

import com.exist.ecc.limyu_exercise8.core.model.Person;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface PersonService {
    List<Person> getAllPeople();

    Person save(Person person);

    void delete(Person person);

    Optional<Person> update(long id, Person person);

    public void deleteById(long id);
}
