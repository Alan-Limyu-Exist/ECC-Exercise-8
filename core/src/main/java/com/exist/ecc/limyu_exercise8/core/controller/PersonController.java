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

    @PutMapping("/{id}")
    public ResponseEntity<PersonDto> update(@PathVariable long id, @Valid @RequestBody PersonDto person) {
        return new ResponseEntity<>(personService.update(id, person), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<PersonDto> delete(@Valid @RequestBody PersonDto person) {
        personService.delete(person);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable long id) {
        personService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{id}/roles")
    public ResponseEntity<PersonDto> addRole(@PathVariable long id, @Valid @RequestBody Role role) {
        return new ResponseEntity<>(personService.addRole(role, id), HttpStatus.OK);
    }

    @DeleteMapping("/{personId}/roles/{roleId}")
    public ResponseEntity<Void> deleteRole(@PathVariable long personId, @PathVariable long roleId) {
        personService.deleteRole(roleId, personId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{personId}/contacts")
    public ResponseEntity<PersonDto> addContact(@PathVariable long personId,
                           @Valid @RequestBody ContactInformation contactInformation) {
        return new ResponseEntity<>(personService.addContactInformation(contactInformation, personId), HttpStatus.OK);
    }

    @PatchMapping("/{personId}/contacts")
    public ResponseEntity<PersonDto> updateContact(@PathVariable long personId,
                           @Valid @RequestBody ContactInformation contactInformation) {
        return new ResponseEntity<>(personService.updateContactInformation(contactInformation, personId), HttpStatus.OK);
    }

    @DeleteMapping("/{personId}/contacts")
    public ResponseEntity<Void> deleteContact(@PathVariable long personId) {
        personService.deleteContactInformation(personId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
