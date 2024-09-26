package com.exist.ecc.limyu_exercise8.core.service;

import com.exist.ecc.limyu_exercise8.core.dao.repository.PersonRepository;
import com.exist.ecc.limyu_exercise8.core.dao.repository.RoleRepository;
import com.exist.ecc.limyu_exercise8.core.model.Address;
import com.exist.ecc.limyu_exercise8.core.model.ContactInformation;
import com.exist.ecc.limyu_exercise8.core.model.Name;
import com.exist.ecc.limyu_exercise8.core.model.Person;
import org.checkerframework.checker.units.qual.A;
import org.checkerframework.checker.units.qual.C;
import org.checkerframework.checker.units.qual.N;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

public class PersonServiceImplTest {

    @InjectMocks
    private PersonService personServiceImpl;

    @Mock
    private PersonRepository personRepository;

    @Mock
    private RoleRepository roleRepository;

    @Spy
    private Person person;

    @BeforeEach
    public void setUp() {
        this.personRepository = mock(PersonRepository.class);
        this.personServiceImpl = new PersonServiceImpl(personRepository, roleRepository);
        this.person = new Person();

        Name name = new Name();
        name.setFirstName("John");
        name.setLastName("Doe");
        this.person.setId(1);
        this.person.setName(name);

        Address address = new Address();
        this.person.setAddress(address);

        this.person.setGwa(1.0f);
        this.person.setCurrentlyEmployed(false);

        ContactInformation contactInformation = new ContactInformation();
        contactInformation.setEmail("johndoe@gmail.com");
        this.person.setContactInformation(contactInformation);

        doAnswer(invocation -> Optional.ofNullable(person))
                .when(personRepository).findById(person.getId());
        doAnswer(invocation -> person)
                .when(personRepository).save(person);

    }

    @Test
    public void shouldSave() {
        personServiceImpl.save(person);
        verify(personRepository).save(person);
    }

    @Test
    public void shouldGetAllPeople() {
        personServiceImpl.getAllPeople();
        verify(personRepository).findAll();
    }

    @Test
    public void shouldDeletePerson() {
        personServiceImpl.delete(person);
        verify(personRepository).delete(person);
    }

    @Test
    public void shouldDeletePersonById() {
        personServiceImpl.deleteById(1L);
        verify(personRepository).deleteById(1L);
    }

    @Test
    public void shouldUpdatePerson() {
        Person newPerson = new Person();
        Name name = new Name();
        name.setFirstName("Alan");
        name.setLastName("Limyu");
        newPerson.setName(name);
        newPerson.setGwa(2.0f);
        newPerson.setId(2);

        Person updatedPerson = personServiceImpl.update(1, newPerson).orElse(null);
        assertNotNull(updatedPerson);
        assertEquals(newPerson.getName().getFirstName(), updatedPerson.getName().getFirstName());
        assertEquals(newPerson.getName().getLastName(), updatedPerson.getName().getLastName());
        assertEquals(newPerson.getGwa(), updatedPerson.getGwa());
        assertNotEquals(newPerson.getId(), updatedPerson.getId());
    }
}
