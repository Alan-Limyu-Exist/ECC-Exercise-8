package com.exist.ecc.limyu_exercise8.core.service;

import com.exist.ecc.limyu_exercise8.core.dao.repository.PersonRepository;
import com.exist.ecc.limyu_exercise8.core.dao.repository.RoleRepository;
import com.exist.ecc.limyu_exercise8.core.exception.ContactAlreadyExistsException;
import com.exist.ecc.limyu_exercise8.core.exception.PersonNotFoundException;
import com.exist.ecc.limyu_exercise8.core.exception.RoleNotFoundException;
import com.exist.ecc.limyu_exercise8.core.model.Address;
import com.exist.ecc.limyu_exercise8.core.model.ContactInformation;
import com.exist.ecc.limyu_exercise8.core.model.Name;
import com.exist.ecc.limyu_exercise8.core.model.Person;
import com.exist.ecc.limyu_exercise8.core.model.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
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

    @Spy
    Role role;

    @BeforeEach
    public void setUp() {
        this.personRepository = mock(PersonRepository.class);
        this.roleRepository = mock(RoleRepository.class);
        this.personServiceImpl =
                new PersonServiceImpl(personRepository, roleRepository);
        this.person = new Person();
        this.role = new Role();

        Name name = new Name();
        name.setFirstName("John");
        name.setLastName("Doe");
        this.person.setId(1);
        this.person.setName(name);

        Address address = new Address();
        this.person.setAddress(address);

        this.person.setGwa(1.0f);
        this.person.setDateHired(LocalDateTime
                                    .parse("2022-01-01T00:00:00"));
        this.person.setCurrentlyEmployed(false);

        this.role.setId(1);
        this.role.setName("Dev");
        this.person.getRoles().add(this.role);

        ContactInformation contactInformation = new ContactInformation();
        contactInformation.setEmail("johndoe@gmail.com");
        this.person.setContactInformation(contactInformation);

        doAnswer(invocation -> Optional.ofNullable(person))
                .when(personRepository).findById(person.getId());
        doAnswer(invocation -> person)
                .when(personRepository).save(person);

        when(roleRepository.findById(1L))
                .thenReturn(Optional.ofNullable(role));

        List<Person> peopleList = new ArrayList<>();
        peopleList.add(person);

        Person newPerson1 = new Person();
        Name newName1 = new Name();
        newName1.setFirstName("Vincenzo");
        newName1.setLastName("Cassano");
        newPerson1.setName(newName1);
        newPerson1.setGwa(2.0f);
        newPerson1.setDateHired(
                LocalDateTime.parse("2022-01-01T00:00:00")
        );
        newPerson1.setId(2);
        peopleList.add(newPerson1);

        Person newPerson2 = new Person();
        Name newName2 = new Name();
        newName2.setFirstName("Hyun Woo");
        newName2.setLastName("Baek");
        newPerson2.setName(newName2);
        newPerson2.setGwa(3.0f);
        newPerson1.setDateHired(
                LocalDateTime.parse("2022-01-01T00:00:01")
        );
        newPerson2.setId(2);
        peopleList.add(newPerson2);

        Person newPerson3 = new Person();
        Name newName3 = new Name();
        newName3.setFirstName("Alberto");
        newName3.setLastName("Gu");
        newPerson3.setName(newName3);
        newPerson3.setGwa(4.0f);
        newPerson1.setDateHired(
                LocalDateTime.parse("2024-01-01T00:00:00")
        );
        newPerson3.setId(2);
        peopleList.add(newPerson3);

        when(personRepository.findAll()).thenReturn(peopleList);

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
    public void shouldNotDeletePerson() {
        assertThrows(PersonNotFoundException.class,
                () -> personServiceImpl.delete(new Person()));
    }

    @Test
    public void shouldDeletePersonById() {
        personServiceImpl.deleteById(1L);
        verify(personRepository).deleteById(1L);
    }

    @Test
    public void shouldNotDeletePersonById() {
        assertThrows(PersonNotFoundException.class,
                () -> personServiceImpl.deleteById(4L));
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

        Role role = new Role();
        role.setId(2);
        role.setName("Admin");

        newPerson.setRoles(Set.of(role));

        Person updatedPerson = personServiceImpl
                .update(1, newPerson);
        assertNotNull(updatedPerson);
        assertEquals(newPerson.getName().getFirstName(),
                updatedPerson.getName().getFirstName());
        assertEquals(newPerson.getName().getLastName(),
                updatedPerson.getName().getLastName());
        assertEquals(newPerson.getGwa(), updatedPerson.getGwa());

        assertNotEquals(newPerson.getId(), updatedPerson.getId());
    }

    @Test
    public void shouldNotUpdatePerson() {
        assertThrows(PersonNotFoundException.class,
                () -> personServiceImpl.update(4L, person));
    }

    @Test
    public void shouldListPeopleByGwa() {
        List<Person> peopleByGwa = personServiceImpl.getAllPeopleByGwa();

        float lastGwa = 0;
        for (Person currentPerson : peopleByGwa) {
            assertTrue(currentPerson.getGwa() >= lastGwa);
            lastGwa = currentPerson.getGwa();
        }
    }

    @Test
    public void shouldListPeopleByDateHired() {
        List<Person> peopleByDateHired =
                personServiceImpl.getAllPeopleByDateHired();

        LocalDateTime lastDateHired = LocalDateTime.MIN;
        for (Person currentPerson : peopleByDateHired) {
            if(currentPerson.getDateHired() == null) {
                continue;
            }

            assertTrue(currentPerson.getDateHired()
                        .isAfter(lastDateHired)
                    || currentPerson.getDateHired()
                        .isEqual(lastDateHired));
            lastDateHired = currentPerson.getDateHired();
        }
    }

    @Test
    public void shouldListPeopleByLastName() {
        List<Person> peopleByLastName =
                personServiceImpl.getAllPeopleByLastName();

        String lastLastName = "";
        for (Person currentPerson : peopleByLastName) {
            assertTrue(currentPerson.getName()
                    .getLastName()
                    .compareTo(lastLastName) >= 0);

            lastLastName = currentPerson.getName().getLastName();
        }
    }

    @Test
    public void shouldAddPersonRole() {
        Role newRole = new Role();
        newRole.setId(2);
        newRole.setName("new role");

        when(roleRepository.findById(2L))
                .thenReturn(Optional.of(newRole));

        assertFalse(person.getRoles().contains(newRole));

        person = personServiceImpl.addRole(newRole, person.getId());

        assertTrue(person.getRoles().contains(newRole));
    }

    @Test
    public void shouldNotAddPersonRole() {
        Role newRole = new Role();
        newRole.setId(2);
        newRole.setName("new role");

        assertFalse(person.getRoles().contains(newRole));

        assertThrows(RoleNotFoundException.class,
                () -> personServiceImpl.addRole(newRole, person.getId()));
    }

    @Test
    public void shouldDeletePersonRole() {
        assertTrue(person.getRoles().contains(role));

        personServiceImpl.deleteRole(1, 1);

        assertFalse(person.getRoles().contains(role));
    }

    @Test
    public void shouldNotDeletePersonRole() {
        assertThrows(RoleNotFoundException.class,
                () -> personServiceImpl
                        .deleteRole(2, 1));

        assertThrows(PersonNotFoundException.class,
                () -> personServiceImpl
                        .deleteRole(1, 2));

        Role newRole = new Role();
        newRole.setId(2);
        newRole.setName("Admin 2");
        when(roleRepository.findById(newRole.getId()))
                .thenReturn(Optional.of(newRole));

        assertThrows(RoleNotFoundException.class,
                () -> personServiceImpl
                        .deleteRole(2, 1));
    }

    @Test
    public void shouldAddPersonContact() {
        ContactInformation newContactInfo = new ContactInformation();
        newContactInfo.setMobileNumber("+631234567890");
        newContactInfo.setEmail("test@test.com");
        newContactInfo.setLandline("021234567890");

        person.setContactInformation(null);

        person = personServiceImpl
                .addContactInformation(newContactInfo, 1);

        assertEquals(person.getContactInformation().getMobileNumber(),
                newContactInfo.getMobileNumber());
        assertEquals(person.getContactInformation().getEmail(),
                newContactInfo.getEmail());
        assertEquals(person.getContactInformation().getLandline(),
                newContactInfo.getLandline());
    }

    @Test
    public void shouldNotAddPersonContact() {
        assertThrows(PersonNotFoundException.class,
                () -> personServiceImpl
                        .addContactInformation(
                                new ContactInformation(), 2
                        )
        );

        assertThrows(ContactAlreadyExistsException.class,
                () -> personServiceImpl
                        .addContactInformation(
                                new ContactInformation(), 1
                        )
        );
    }

    @Test
    public void shouldUpdatePersonContact() {
        ContactInformation newContactInfo = new ContactInformation();
        newContactInfo.setMobileNumber("+631234567890");
        newContactInfo.setEmail("test@test.com");
        newContactInfo.setLandline("021234567890");

        assertNotNull(person.getContactInformation());

        person = personServiceImpl
                .updateContactInformation(newContactInfo, 1);

        assertEquals(person.getContactInformation().getMobileNumber(),
                newContactInfo.getMobileNumber());
        assertEquals(person.getContactInformation().getEmail(),
                newContactInfo.getEmail());
        assertEquals(person.getContactInformation().getLandline(),
                newContactInfo.getLandline());
    }

    @Test
    public void shouldNotUpdatePersonContact() {
        person.setContactInformation(null);
        assertNull(person.getContactInformation());

        assertThrows(NullPointerException.class,
                () -> personServiceImpl
                        .updateContactInformation(
                                new ContactInformation(), 1
                        )
        );

        assertThrows(PersonNotFoundException.class,
                () -> personServiceImpl
                        .updateContactInformation(
                                new ContactInformation(), 2
                        )
        );
    }

    @Test
    public void shouldDeletePersonContact() {
        assertNotNull(person.getContactInformation());

        personServiceImpl.deleteContactInformation(1);

        assertNull(person.getContactInformation());
    }

    @Test
    public void shouldNotDeletePersonContact() {
        person.setContactInformation(null);
        assertNull(person.getContactInformation());

        assertThrows(NullPointerException.class,
                () -> personServiceImpl.deleteContactInformation(1));

        assertThrows(PersonNotFoundException.class,
                () -> personServiceImpl.deleteContactInformation(2));
    }
}
