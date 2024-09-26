package com.exist.ecc.limyu_exercise8.core.controller;

import com.exist.ecc.limyu_exercise8.core.model.Person;
import com.exist.ecc.limyu_exercise8.core.service.PersonService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/person")
public class PersonController {

    @Autowired
    private PersonService personService;

    @PostMapping
    public Person create(@Valid @RequestBody Person person) {
        return personService.save(person);
    }
}
