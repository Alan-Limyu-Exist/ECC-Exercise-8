package com.exist.ecc.limyu_exercise8.core.service;

import com.exist.ecc.limyu_exercise8.core.dao.repository.PersonRepository;
import com.exist.ecc.limyu_exercise8.core.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertTrue;

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
    private Person person;

    @BeforeEach
    public void setUp() {
        this.personRepository = mock(PersonRepository.class);
        this.personServiceImpl = new PersonServiceImpl(personRepository);
        this.person = mock(Person.class);
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
}
