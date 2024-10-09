package com.exist.ecc.limyu_exercise8.core.service;

import com.exist.ecc.limyu_exercise8.core.model.ContactInformation;
import com.exist.ecc.limyu_exercise8.core.model.Person;
import com.exist.ecc.limyu_exercise8.core.model.dto.PersonDto;
import com.exist.ecc.limyu_exercise8.core.model.Role;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface PersonService {
    List<PersonDto> getAllPeople();

    PersonDto save(PersonDto personDto);

    void delete(PersonDto personDto);

    PersonDto update(UUID uuid, PersonDto personDto);

    void deleteByUuid(UUID uuid);

    List<PersonDto> getAllPeopleByGwa();

    List<PersonDto> getAllPeopleByDateHired();

    List<PersonDto> getAllPeopleByLastName();

    PersonDto addRole(Role role, PersonDto personDto);

    PersonDto addRole(Role role, UUID uuid);

    PersonDto deleteRole(Role role, PersonDto personDto);

    PersonDto deleteRole(UUID roleUuid, UUID personUuid);

    PersonDto addContactInformation(ContactInformation contactInformation, PersonDto personDto);

    PersonDto addContactInformation(ContactInformation contactInformation, UUID uuid);

    PersonDto updateContactInformation(ContactInformation contactInformation, PersonDto personDto);

    PersonDto updateContactInformation(ContactInformation contactInformation, UUID uuid);

    PersonDto deleteContactInformation(PersonDto personDto);

    PersonDto deleteContactInformation(UUID uuid);

    PersonDto toDto(Person person);

    Person fromDto(PersonDto personDto);
}
