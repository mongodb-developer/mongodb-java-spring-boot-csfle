package com.mongodb.quickstart.javaspringbootcsfle.service;

import com.mongodb.quickstart.javaspringbootcsfle.dto.PersonDTO;

import java.util.List;

public interface PersonService {

    List<PersonDTO> findAll();

    PersonDTO save(PersonDTO person);
}
