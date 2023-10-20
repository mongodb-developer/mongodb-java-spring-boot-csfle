package com.mongodb.quickstart.javaspringbootcsfle.serviceImpl;

import com.mongodb.quickstart.javaspringbootcsfle.dto.PersonDTO;
import com.mongodb.quickstart.javaspringbootcsfle.model.PersonEntity;
import com.mongodb.quickstart.javaspringbootcsfle.repository.PersonRepository;
import com.mongodb.quickstart.javaspringbootcsfle.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonServiceImpl.class);
    private final PersonRepository personRepository;

    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public List<PersonDTO> findAll() {
        return personRepository.findAll().stream().map(PersonDTO::new).toList();
    }

    @Override
    public PersonDTO save(PersonDTO personDTO) {
        PersonEntity person = personDTO.toPersonEntity();
        LOGGER.info("Saving person: {}", person);
        PersonEntity personSaved = personRepository.save(person);
        return new PersonDTO(personSaved);
    }
}
