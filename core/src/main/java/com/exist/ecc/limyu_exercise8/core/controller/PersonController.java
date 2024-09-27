package com.exist.ecc.limyu_exercise8.core.controller;

import com.exist.ecc.limyu_exercise8.core.model.ContactInformation;
import com.exist.ecc.limyu_exercise8.core.model.Person;
import com.exist.ecc.limyu_exercise8.core.model.Role;
import com.exist.ecc.limyu_exercise8.core.service.PersonService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/person")
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping
    public List<Person> getAll(
            @RequestParam(value = "sortBy", required = false) String sortBy) {
        if (sortBy == null) {
            return personService.getAllPeople();
        }

        return switch (sortBy) {
            case "gwa" -> personService.getAllPeopleByGwa();
            case "lastName" -> personService.getAllPeopleByLastName();
            case "dateHired" -> personService.getAllPeopleByDateHired();
            default -> personService.getAllPeople();
        };
    }

    @PostMapping
    public Person create(@Valid @RequestBody Person person) {
        return personService.save(person);
    }

    @PutMapping("/{id}")
    public Person update(@PathVariable long id, @Valid @RequestBody Person person) {
        return personService.update(id, person);
    }

    @DeleteMapping
    public void delete(@Valid @RequestBody Person person) {
        personService.delete(person);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable long id) {
        personService.deleteById(id);
    }

    @PatchMapping("/{id}/role")
    public Person addRole(@PathVariable long id, @Valid @RequestBody Role role) {
        return personService.addRole(role, id);
    }

    @DeleteMapping("/{personId}/role/{roleId}")
    public void deleteRole(@PathVariable long personId, @PathVariable long roleId) {
        personService.deleteRole(roleId, personId);
    }

    @PatchMapping("/{personId}/contact")
    public void addContact(@PathVariable long personId,
                           @Valid @RequestBody ContactInformation contactInformation) {
        personService.addContactInformation(contactInformation, personId);
    }

    @DeleteMapping("/{personId}/contact")
    public void deleteContact(@PathVariable long personId) {
        personService.deleteContactInformation(personId);
    }
}
