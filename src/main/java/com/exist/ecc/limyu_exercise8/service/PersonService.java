package com.exist.ecc.limyu_exercise8.service;

import com.exist.ecc.limyu_exercise8.model.Person;

import java.util.List;

public interface PersonService {
    List<Person> getAllPeople();

    Person save(Person person);
}
