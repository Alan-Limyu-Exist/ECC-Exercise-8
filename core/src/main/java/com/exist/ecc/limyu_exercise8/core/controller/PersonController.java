package com.exist.ecc.limyu_exercise8.core.controller;

import com.exist.ecc.limyu_exercise8.core.model.ContactInformation;
import com.exist.ecc.limyu_exercise8.core.model.dto.PersonDto;
import com.exist.ecc.limyu_exercise8.core.model.Role;
import com.exist.ecc.limyu_exercise8.core.service.PersonService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/people")
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping
    public ResponseEntity<List<PersonDto>> getAll(
            @RequestParam(value = "sortBy", required = false) String sortBy) {
        List<PersonDto> people;
        if (sortBy == null) {
            people = personService.getAllPeople();
        } else {
            people = switch (sortBy) {
                case "gwa" -> personService.getAllPeopleByGwa();
                case "lastName" -> personService.getAllPeopleByLastName();
                case "dateHired" -> personService.getAllPeopleByDateHired();
                default -> personService.getAllPeople();
            };
        }
        return new ResponseEntity<>(people, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PersonDto> create(@Valid @RequestBody PersonDto person) {
        return new ResponseEntity<>(personService.save(person), HttpStatus.CREATED);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<PersonDto> update(@PathVariable UUID uuid, @Valid @RequestBody PersonDto person) {
        return new ResponseEntity<>(personService.update(uuid, person), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<PersonDto> delete(@Valid @RequestBody PersonDto person) {
        personService.delete(person);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteById(@PathVariable UUID uuid) {
        personService.deleteByUuid(uuid);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{uuid}/roles")
    public ResponseEntity<PersonDto> addRole(@PathVariable UUID uuid, @Valid @RequestBody Role role) {
        return new ResponseEntity<>(personService.addRole(role, uuid), HttpStatus.OK);
    }

    @DeleteMapping("/{personUuid}/roles/{roleUuid}")
    public ResponseEntity<Void> deleteRole(@PathVariable UUID personUuid, @PathVariable UUID roleUuid) {
        personService.deleteRole(roleUuid, personUuid);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{personUuid}/contacts")
    public ResponseEntity<PersonDto> addContact(@PathVariable UUID personUuid,
                           @Valid @RequestBody ContactInformation contactInformation) {
        return new ResponseEntity<>(personService.addContactInformation(contactInformation, personUuid), HttpStatus.OK);
    }

    @PatchMapping("/{personUuid}/contacts")
    public ResponseEntity<PersonDto> updateContact(@PathVariable UUID personUuid,
                           @Valid @RequestBody ContactInformation contactInformation) {
        return new ResponseEntity<>(personService.updateContactInformation(contactInformation, personUuid), HttpStatus.OK);
    }

    @DeleteMapping("/{personUuid}/contacts")
    public ResponseEntity<Void> deleteContact(@PathVariable UUID personUuid) {
        personService.deleteContactInformation(personUuid);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
