package com.exist.ecc.limyu_exercise8.core.service;

import com.exist.ecc.limyu_exercise8.core.dao.repository.PersonRepository;
import com.exist.ecc.limyu_exercise8.core.dao.repository.RoleRepository;
import com.exist.ecc.limyu_exercise8.core.exception.ContactAlreadyExistsException;
import com.exist.ecc.limyu_exercise8.core.exception.PersonNotFoundException;
import com.exist.ecc.limyu_exercise8.core.exception.RoleNotFoundException;
import com.exist.ecc.limyu_exercise8.core.model.ContactInformation;
import com.exist.ecc.limyu_exercise8.core.model.Person;
import com.exist.ecc.limyu_exercise8.core.model.dto.PersonDto;
import com.exist.ecc.limyu_exercise8.core.model.Role;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Validated
@Service
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;

    private final RoleRepository roleRepository;

    public PersonServiceImpl(PersonRepository personRepository, RoleRepository roleRepository) {
        this.personRepository = personRepository;
        this.roleRepository = roleRepository;
    }


    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'VIEWER')")
    public List<PersonDto> getAllPeople() {
        return personRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public PersonDto save(PersonDto personDto) {
        return this.toDto(personRepository.save(this.fromDto(personDto)));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'VIEWER')")
    public PersonDto get(UUID uuid) {
        PersonDto personDto = this.toDto(personRepository.findByUuid(uuid).orElse(null));

        if (personDto == null) {
            throw new PersonNotFoundException("Cannot find person");
        }

        return personDto;
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(PersonDto personDto) {
        personDto = get(personDto.getUuid());
        personRepository.delete(this.fromDto(personDto));
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteByUuid(UUID uuid) {
        personRepository.delete(this.fromDto(get(uuid)));
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public PersonDto update(UUID uuid, PersonDto personDto) {
        PersonDto updatedPerson = get(uuid);

        if (updatedPerson != null) {
            updatedPerson.setName(personDto.getName());
            updatedPerson.setAddress(personDto.getAddress());
            updatedPerson.setGwa(personDto.getGwa());
            updatedPerson.setDateHired(personDto.getDateHired());
            updatedPerson.setCurrentlyEmployed(personDto.isCurrentlyEmployed());
            updatedPerson.setContactInformation(personDto.getContactInformation());

            Set<Role> updatedRoles = new HashSet<>();
            for (Role role : personDto.getRoles()) {
                Optional<Role> fetchedRole = roleRepository.findById(role.getId());
                fetchedRole.ifPresent(updatedRoles::add);
            }
            updatedPerson.setRoles(updatedRoles);

            updatedPerson = this.toDto(personRepository.save(this.fromDto(updatedPerson)));
        }

        return updatedPerson;
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'VIEWER')")
    public List<PersonDto> getAllPeopleByGwa() {
        return personRepository.findAll().stream()
                .map(this::toDto)
                .sorted(Comparator.comparing(PersonDto::getGwa))
                .collect(Collectors.toList());
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'VIEWER')")
    public List<PersonDto> getAllPeopleByDateHired() {
        return personRepository.findAllPeopleByDateHired().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'VIEWER')")
    public List<PersonDto> getAllPeopleByLastName() {
        return personRepository.findAllPeopleByLastName().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public PersonDto addRole(Role role, PersonDto personDto) {
        Role roleInDatabase = roleRepository.findByUuid(role.getUuid()).orElse(null);
        if (roleInDatabase == null) {
            throw new RoleNotFoundException("Role '" + role.getName() + "' does not exist in database");
        }
        personDto.getRoles().add(role);
        return this.toDto(personRepository.save(this.fromDto(personDto)));
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public PersonDto addRole(Role role, UUID uuid) {
        PersonDto personDto = this.toDto(personRepository.findByUuid(uuid).orElse(null));

        return this.addRole(role, personDto);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public PersonDto deleteRole(Role role, PersonDto personDto) {
        if (!personDto.getRoles().contains(role)) {
            throw new RoleNotFoundException("Person does not have role: " + role.getName());
        }
        personDto.getRoles().remove(role);
        return this.toDto(personRepository.save(this.fromDto(personDto)));
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public PersonDto deleteRole(UUID roleUuid, UUID personUuid) {
        Role role = roleRepository.findByUuid(roleUuid)
                .orElseThrow(()
                        -> new RoleNotFoundException("Role does not exist"));

        PersonDto personDto = this.toDto(personRepository.findByUuid(personUuid)
                .orElseThrow(()
                        -> new PersonNotFoundException("Person does not exist")));

        return this.deleteRole(role, personDto);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public PersonDto addContactInformation(ContactInformation contactInformation, PersonDto personDto) {
        if (personDto.getContactInformation() != null) {
            throw new ContactAlreadyExistsException("Cannot add contact because contact is not empty");
        }

        personDto.setContactInformation(contactInformation);
        return this.toDto(personRepository.save(this.fromDto(personDto)));
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public PersonDto addContactInformation(ContactInformation contactInformation, UUID uuid) {
        PersonDto personDto = this.toDto(personRepository.findByUuid(uuid)
                .orElseThrow(()
                        -> new PersonNotFoundException("Person does not exist")));

        return this.addContactInformation(contactInformation, personDto);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public PersonDto updateContactInformation(ContactInformation contactInformation, PersonDto personDto) {
        if (personDto.getContactInformation() == null) {
            throw new NullPointerException("Cannot update contact because contact is null");
        }
        personDto.getContactInformation().setLandline(contactInformation.getLandline());
        personDto.getContactInformation().setMobileNumber(contactInformation.getMobileNumber());
        personDto.getContactInformation().setEmail(contactInformation.getEmail());
        return this.toDto(personRepository.save(this.fromDto(personDto)));
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public PersonDto updateContactInformation(ContactInformation contactInformation, UUID uuid) {
        PersonDto personDto = this.toDto(personRepository.findByUuid(uuid)
                .orElseThrow(()
                        -> new PersonNotFoundException("Person does not exist")));

        return this.updateContactInformation(contactInformation, personDto);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public PersonDto deleteContactInformation(PersonDto personDto) {
        if (personDto.getContactInformation() == null) {
            throw new NullPointerException("Cannot delete contact because contact is null");
        }
        personDto.setContactInformation(null);
        return this.toDto(personRepository.save(this.fromDto(personDto)));
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public PersonDto deleteContactInformation(UUID uuid) {
        PersonDto personDto = this.toDto(personRepository.findByUuid(uuid)
                .orElseThrow(()
                        -> new PersonNotFoundException("Person does not exist")));

        return this.deleteContactInformation(personDto);
    }

    @Override
    public PersonDto toDto(Person person) {
        if (person == null) {
            return null;
        }

        return new PersonDto(
                person.getId(),
                person.getUuid(),
                person.getName(),
                person.getAddress(),
                person.getGwa(),
                person.getDateHired(),
                person.isCurrentlyEmployed(),
                person.getContactInformation(),
                person.getRoles()
        );
    }

    @Override
    public Person fromDto(PersonDto personDto) {
        if (personDto == null) {
            return null;
        }

        Person person = new Person();
        person.setId(personDto.getId());

        if (personDto.getUuid() != null) {
            person.setUuid(personDto.getUuid());
        }

        person.setName(personDto.getName());
        person.setAddress(personDto.getAddress());
        person.setGwa(personDto.getGwa());
        person.setDateHired(personDto.getDateHired());
        person.setCurrentlyEmployed(personDto.isCurrentlyEmployed());
        person.setContactInformation(personDto.getContactInformation());
        person.setRoles(personDto.getRoles());
        return person;
    }
}
