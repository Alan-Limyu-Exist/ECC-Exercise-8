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
import com.exist.ecc.limyu_exercise8.core.model.dto.PersonDto;
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
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

public class PersonServiceImplTest {

    @Spy
    @InjectMocks
    private PersonService personServiceImpl;

    @Mock
    private PersonRepository personRepository;

    @Mock
    private RoleRepository roleRepository;

    @Spy
    private PersonDto personDto;

    @Spy
    private Person person;

    @Spy
    Role role;

    @BeforeEach
    public void setUp() {
        this.personRepository = mock(PersonRepository.class);
        this.roleRepository = mock(RoleRepository.class);
        this.personServiceImpl =
                spy(new PersonServiceImpl(personRepository, roleRepository));
        this.role = new Role();
        this.role.setId(1);
        this.role.setName("Dev");

        this.person = new Person();
        this.person.setId(1);

        Name name2 = new Name();
        name2.setFirstName("John");
        name2.setLastName("Doe");
        this.person.setName(name2);

        Address address2 = new Address();
        this.person.setAddress(address2);

        this.person.setGwa(1.0f);
        this.person.setDateHired(LocalDateTime
                .parse("2022-01-01T00:00:00"));
        this.person.setCurrentlyEmployed(false);
        this.person.getRoles().add(this.role);

        ContactInformation contactInformation2 = new ContactInformation();
        contactInformation2.setEmail("johndoe@gmail.com");
        this.person.setContactInformation(contactInformation2);

        this.personDto = this.personServiceImpl.toDto(person);

        doAnswer(invocation -> Optional.ofNullable(person))
                .when(personRepository).findByUuid(person.getUuid());
        doAnswer(invocation -> person)
                .when(personRepository).save(person);

        when(roleRepository.findByUuid(role.getUuid()))
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

        doReturn(person).when(personServiceImpl).fromDto(personDto);
        doReturn(personDto).when(personServiceImpl).toDto(person);
    }

    @Test
    public void shouldSave() {
        personServiceImpl.save(personDto);
        verify(personRepository).save(person);
    }

    @Test
    public void shouldGetAllPeople() {
        personServiceImpl.getAllPeople();
        verify(personRepository).findAll();
    }

    @Test
    public void shouldDeletePerson() {
        personServiceImpl.delete(personDto);
        verify(personRepository).delete(person);
    }

    @Test
    public void shouldNotDeletePerson() {
        assertThrows(PersonNotFoundException.class,
                () -> personServiceImpl.delete(new PersonDto()));
    }

    @Test
    public void shouldDeletePersonByUuid() {
        personServiceImpl.deleteByUuid(person.getUuid());
        verify(personRepository).delete(person);
    }

    @Test
    public void shouldNotDeletePersonByUuid() {
        assertThrows(PersonNotFoundException.class,
                () -> personServiceImpl.deleteByUuid(UUID.randomUUID()));
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

        PersonDto updatedPerson = personServiceImpl
                .update(person.getUuid(), this.personServiceImpl.toDto(newPerson));
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
                () -> personServiceImpl.update(UUID.randomUUID(), personDto));
    }

    @Test
    public void shouldListPeopleByGwa() {
        List<PersonDto> peopleByGwa = personServiceImpl.getAllPeopleByGwa();

        float lastGwa = 0;
        for (PersonDto currentPerson : peopleByGwa) {
            assertTrue(currentPerson.getGwa() >= lastGwa);
            lastGwa = currentPerson.getGwa();
        }
    }

    @Test
    public void shouldListPeopleByDateHired() {
        List<PersonDto> peopleByDateHired =
                personServiceImpl.getAllPeopleByDateHired();

        LocalDateTime lastDateHired = LocalDateTime.MIN;
        for (PersonDto currentPerson : peopleByDateHired) {
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
        List<PersonDto> peopleByLastName =
                personServiceImpl.getAllPeopleByLastName();

        String lastLastName = "";
        for (PersonDto currentPerson : peopleByLastName) {
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

        when(roleRepository.findByUuid(newRole.getUuid()))
                .thenReturn(Optional.of(newRole));

        assertFalse(personDto.getRoles().contains(newRole));

        personDto = personServiceImpl.addRole(newRole, personDto.getUuid());

        assertTrue(personDto.getRoles().contains(newRole));
    }

    @Test
    public void shouldNotAddPersonRole() {
        Role newRole = new Role();
        newRole.setId(2);
        newRole.setName("new role");

        assertFalse(person.getRoles().contains(newRole));

        assertThrows(RoleNotFoundException.class,
                () -> personServiceImpl.addRole(newRole, person.getUuid()));
    }

    @Test
    public void shouldDeletePersonRole() {
        assertTrue(person.getRoles().contains(role));

        personDto = personServiceImpl.deleteRole(role.getUuid(), person.getUuid());

        assertFalse(personDto.getRoles().contains(role));
    }

    @Test
    public void shouldNotDeletePersonRole() {
        assertThrows(RoleNotFoundException.class,
                () -> personServiceImpl
                        .deleteRole(UUID.randomUUID(), person.getUuid()));

        assertThrows(PersonNotFoundException.class,
                () -> personServiceImpl
                        .deleteRole(role.getUuid(), UUID.randomUUID()));

        Role newRole = new Role();
        newRole.setId(2);
        newRole.setName("Admin 2");
        when(roleRepository.findByUuid(newRole.getUuid()))
                .thenReturn(Optional.of(newRole));

        assertThrows(RoleNotFoundException.class,
                () -> personServiceImpl
                        .deleteRole(newRole.getUuid(), person.getUuid()));
    }

    @Test
    public void shouldAddPersonContact() {
        ContactInformation newContactInfo = new ContactInformation();
        newContactInfo.setMobileNumber("+631234567890");
        newContactInfo.setEmail("test@test.com");
        newContactInfo.setLandline("021234567890");
        person.setContactInformation(null);
        personDto.setContactInformation(null);

        personDto = personServiceImpl
                .addContactInformation(newContactInfo, person.getUuid());

        assertEquals(personDto.getContactInformation().getMobileNumber(),
                newContactInfo.getMobileNumber());
        assertEquals(personDto.getContactInformation().getEmail(),
                newContactInfo.getEmail());
        assertEquals(personDto.getContactInformation().getLandline(),
                newContactInfo.getLandline());
    }

    @Test
    public void shouldNotAddPersonContact() {
        assertThrows(PersonNotFoundException.class,
                () -> personServiceImpl
                        .addContactInformation(
                                new ContactInformation(), UUID.randomUUID()
                        )
        );

        assertThrows(ContactAlreadyExistsException.class,
                () -> personServiceImpl
                        .addContactInformation(
                                new ContactInformation(), person.getUuid()
                        )
        );
    }

    @Test
    public void shouldUpdatePersonContact() {
        ContactInformation newContactInfo = new ContactInformation();
        newContactInfo.setMobileNumber("+631234567890");
        newContactInfo.setEmail("test@test.com");
        newContactInfo.setLandline("021234567890");
        personDto.setContactInformation(newContactInfo);

        assertNotNull(personDto.getContactInformation());

        personDto = personServiceImpl
                .updateContactInformation(newContactInfo, person.getUuid());

        assertEquals(personDto.getContactInformation().getMobileNumber(),
                newContactInfo.getMobileNumber());
        assertEquals(personDto.getContactInformation().getEmail(),
                newContactInfo.getEmail());
        assertEquals(personDto.getContactInformation().getLandline(),
                newContactInfo.getLandline());
    }

    @Test
    public void shouldNotUpdatePersonContact() {
        person.setContactInformation(null);
        personDto.setContactInformation(null);
        assertNull(person.getContactInformation());

        assertThrows(NullPointerException.class,
                () -> personServiceImpl
                        .updateContactInformation(
                                new ContactInformation(), person.getUuid()
                        )
        );

        assertThrows(PersonNotFoundException.class,
                () -> personServiceImpl
                        .updateContactInformation(
                                new ContactInformation(), UUID.randomUUID()
                        )
        );
    }

    @Test
    public void shouldDeletePersonContact() {
        assertNotNull(person.getContactInformation());

        personDto = personServiceImpl.deleteContactInformation(person.getUuid());

        assertNull(personDto.getContactInformation());
    }

    @Test
    public void shouldNotDeletePersonContact() {
        person.setContactInformation(null);
        personDto.setContactInformation(null);
        assertNull(person.getContactInformation());

        assertThrows(NullPointerException.class,
                () -> personServiceImpl.deleteContactInformation(person.getUuid()));

        assertThrows(PersonNotFoundException.class,
                () -> personServiceImpl.deleteContactInformation(UUID.randomUUID()));
    }
}
