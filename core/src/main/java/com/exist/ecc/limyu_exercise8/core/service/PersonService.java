package com.exist.ecc.limyu_exercise8.core.service;

import com.exist.ecc.limyu_exercise8.core.model.ContactInformation;
import com.exist.ecc.limyu_exercise8.core.model.Person;
import com.exist.ecc.limyu_exercise8.core.model.Role;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PersonService {
    List<Person> getAllPeople();

    Person save(Person person);

    void delete(Person person);

    Person update(long id, Person person);

    void deleteById(long id);

    List<Person> getAllPeopleByGwa();

    List<Person> getAllPeopleByDateHired();

    List<Person> getAllPeopleByLastName();

    Person addRole(Role role, Person person);

    Person addRole(Role role, long id);

    Person deleteRole(Role role, Person person);

    Person deleteRole(long roleId, long personId);

    Person addContactInformation(ContactInformation contactInformation, Person person);

    Person updateContactInformation(ContactInformation contactInformation, Person person);

    Person deleteContactInformation(Person person);
}
