package com.exist.ecc.limyu_exercise8.core.service;

import com.exist.ecc.limyu_exercise8.core.dao.repository.RoleRepository;
import com.exist.ecc.limyu_exercise8.core.exception.PersonNotFoundException;
import com.exist.ecc.limyu_exercise8.core.exception.RoleNotFoundException;
import com.exist.ecc.limyu_exercise8.core.model.ContactInformation;
import com.exist.ecc.limyu_exercise8.core.model.Person;
import com.exist.ecc.limyu_exercise8.core.dao.repository.PersonRepository;
import com.exist.ecc.limyu_exercise8.core.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Validated
@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private final PersonRepository personRepository;

    @Autowired
    private final RoleRepository roleRepository;

    public PersonServiceImpl(PersonRepository personRepository, RoleRepository roleRepository) {
        this.personRepository = personRepository;
        this.roleRepository = roleRepository;
    }


    @Override
    public List<Person> getAllPeople() {
        return personRepository.findAll();
    }

    @Override
    public Person save(Person person) {
        return personRepository.save(person);
    }

    public Person get(long id) {
        Person person = personRepository.findById(id).orElse(null);

        if (person == null) {
            throw new PersonNotFoundException("Cannot find person");
        }

        return person;
    }

    @Override
    public void delete(Person person) {
        person = get(person.getId());
        personRepository.delete(person);
    }

    @Override
    public void deleteById(long id) {
        get(id);
        personRepository.deleteById(id);
    }

    @Override
    public Person update(long id, Person person) {
        Person updatedPerson = get(id);

        if (updatedPerson != null) {
            updatedPerson.setName(person.getName());
            updatedPerson.setAddress(person.getAddress());
            updatedPerson.setGwa(person.getGwa());
            updatedPerson.setDateHired(person.getDateHired());
            updatedPerson.setCurrentlyEmployed(person.isCurrentlyEmployed());
            updatedPerson.setContactInformation(person.getContactInformation());

            Set<Role> updatedRoles = new HashSet<>();
            for (Role role : person.getRoles()) {
                Optional<Role> fetchedRole = roleRepository.findById(role.getId());
                fetchedRole.ifPresent(updatedRoles::add);
            }
            updatedPerson.setRoles(updatedRoles);

            updatedPerson = personRepository.save(updatedPerson);
        }

        return updatedPerson;
    }

    @Override
    public List<Person> getAllPeopleByGwa() {
        return personRepository.findAll().stream()
                .sorted(Comparator.comparing(Person::getGwa))
                .collect(Collectors.toList());
    }

    @Override
    public List<Person> getAllPeopleByDateHired() {
        return personRepository.findAll().stream()
                .sorted(Comparator.comparing(Person::getDateHired,
                        Comparator.nullsLast(Comparator.naturalOrder())))
                .collect(Collectors.toList());
    }

    @Override
    public List<Person> getAllPeopleByLastName() {
        return personRepository.findAll().stream()
                .sorted(Comparator.comparing(person -> person.getName().getLastName()))
                .collect(Collectors.toList());
    }

    @Override
    public Person addRole(Role role, Person person) {
        Role roleInDatabase = roleRepository.findById(role.getId()).orElse(null);
        if (roleInDatabase == null) {
            throw new RoleNotFoundException("Role '" + role.getName() + "' does not exist in database");
        }
        person.getRoles().add(role);
        return personRepository.save(person);
    }

    @Override
    public Person deleteRole(Role role, Person person) {
        if (!person.getRoles().contains(role)) {
            throw new RoleNotFoundException("Person does not have role: " + role.getName());
        }
        person.getRoles().remove(role);
        return personRepository.save(person);
    }

    @Override
    public Person addContactInformation(ContactInformation contactInformation, Person person) {
        person.setContactInformation(contactInformation);
        return personRepository.save(person);
    }

    @Override
    public Person updateContactInformation(ContactInformation contactInformation, Person person) {
        if (person.getContactInformation() == null) {
            throw new NullPointerException("Cannot update contact because contact is null");
        }
        person.getContactInformation().setLandline(contactInformation.getLandline());
        person.getContactInformation().setMobileNumber(contactInformation.getMobileNumber());
        person.getContactInformation().setEmail(contactInformation.getEmail());
        return personRepository.save(person);
    }

    @Override
    public Person deleteContactInformation(Person person) {
        if (person.getContactInformation() == null) {
            throw new NullPointerException("Cannot update contact because contact is null");
        }
        person.setContactInformation(null);
        return personRepository.save(person);
    }
}
