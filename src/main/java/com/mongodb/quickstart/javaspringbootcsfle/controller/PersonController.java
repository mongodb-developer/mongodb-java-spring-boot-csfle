package com.mongodb.quickstart.javaspringbootcsfle.controller;

import com.mongodb.quickstart.javaspringbootcsfle.csfleServiceImpl.MasterKeyServiceImpl;
import com.mongodb.quickstart.javaspringbootcsfle.dto.PersonDTO;
import com.mongodb.quickstart.javaspringbootcsfle.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * REST API for the persons.
 */
@RestController
public class PersonController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MasterKeyServiceImpl.class);
    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/persons")
    public List<PersonDTO> getAllPersons() {
        try {
            return personService.findAll();
        } catch (Exception exception) {
            LOGGER.error("=> Couldn't retrieve the Persons from MongoDB.");// todo fix all this
            return new ArrayList<>();
        }
    }

    @PostMapping("/person")
    @ResponseStatus(HttpStatus.CREATED)
    public PersonDTO createPerson(@RequestBody PersonDTO personDTO) {
        LOGGER.info("Saving person: {}", personDTO);
        return personService.save(personDTO);
    }

    // TODO Add find by SSN
}
