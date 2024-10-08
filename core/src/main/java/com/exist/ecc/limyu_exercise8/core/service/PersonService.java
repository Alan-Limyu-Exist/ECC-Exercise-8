package com.exist.ecc.limyu_exercise8.core.service;

import com.exist.ecc.limyu_exercise8.core.model.ContactInformation;
import com.exist.ecc.limyu_exercise8.core.model.Person;
import com.exist.ecc.limyu_exercise8.core.model.dto.PersonDto;
import com.exist.ecc.limyu_exercise8.core.model.Role;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PersonService {
    List<PersonDto> getAllPeople();

    PersonDto save(PersonDto personDto);

    void delete(PersonDto personDto);

    PersonDto update(long id, PersonDto personDto);

    void deleteById(long id);

    List<PersonDto> getAllPeopleByGwa();

    List<PersonDto> getAllPeopleByDateHired();

    List<PersonDto> getAllPeopleByLastName();

    PersonDto addRole(Role role, PersonDto personDto);

    PersonDto addRole(Role role, long id);

    PersonDto deleteRole(Role role, PersonDto personDto);

    PersonDto deleteRole(long roleId, long personId);

    PersonDto addContactInformation(ContactInformation contactInformation, PersonDto personDto);

    PersonDto addContactInformation(ContactInformation contactInformation, long id);

    PersonDto updateContactInformation(ContactInformation contactInformation, PersonDto personDto);

    PersonDto updateContactInformation(ContactInformation contactInformation, long id);

    PersonDto deleteContactInformation(PersonDto personDto);

    PersonDto deleteContactInformation(long id);

    PersonDto toDto(Person person);

    Person fromDto(PersonDto personDto);
}
