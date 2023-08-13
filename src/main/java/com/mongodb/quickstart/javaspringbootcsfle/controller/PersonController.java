package com.mongodb.quickstart.javaspringbootcsfle.controller;

import com.mongodb.quickstart.javaspringbootcsfle.model.Person;
import com.mongodb.quickstart.javaspringbootcsfle.repository.PersonRepository;
import com.mongodb.quickstart.javaspringbootcsfle.servicesImpl.MasterKeyServiceImpl;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PersonController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MasterKeyServiceImpl.class);
    private final PersonRepository personRepository;

    public PersonController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @GetMapping("/hello")
    public String hello() {
        return "hello CSFLE!";
    }

    @GetMapping("/persons")
    public List<Person> getAllPersons() {
        try {
            return this.personRepository.findAll();
        } catch (Exception exception) {
            LOGGER.error("=> Couldn't retrieve the Persons from MongoDB.");// todo fix all this
            return new ArrayList<>();
        }
    }

    @PostMapping("/person")
    @ResponseStatus(HttpStatus.CREATED)
    public Person createPerson(@RequestBody Person person) {
        LOGGER.info("Saving person: " + person);
        return this.personRepository.save(person); // todo Person from the request body should be different from the person I'm storing in MDB?
        // todo remove one to one mapping from front <=> MDB doc.
    }
}
