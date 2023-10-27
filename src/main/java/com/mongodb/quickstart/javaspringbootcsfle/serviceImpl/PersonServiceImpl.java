package com.mongodb.quickstart.javaspringbootcsfle.serviceImpl;

import com.mongodb.quickstart.javaspringbootcsfle.dto.PersonDTO;
import com.mongodb.quickstart.javaspringbootcsfle.exception.EntityNotFoundException;
import com.mongodb.quickstart.javaspringbootcsfle.model.PersonEntity;
import com.mongodb.quickstart.javaspringbootcsfle.repository.PersonRepository;
import com.mongodb.quickstart.javaspringbootcsfle.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Person Service.
 * Contains the business logic layer between the Controller and Repositories (database).
 */
@Service
public class PersonServiceImpl implements PersonService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonServiceImpl.class);
    private final PersonRepository personRepository;

    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public List<PersonDTO> findAll() {
        LOGGER.info("=> Find all persons.");
        List<PersonDTO> result = personRepository.findAll().stream().map(PersonDTO::new).toList();
        if (result.isEmpty()) {
            throw new EntityNotFoundException("PersonServiceImpl#findAll");
        }
        return result;
    }

    @Override
    public PersonDTO save(PersonDTO personDTO) {
        PersonEntity person = personDTO.toPersonEntity();
        LOGGER.info("=> Saving person: {}", person);
        PersonEntity personSaved = personRepository.save(person);
        return new PersonDTO(personSaved);
    }

    @Override
    public PersonDTO findBySsn(String ssn) {
        LOGGER.info("=> Find by SSN {}.", ssn);
        PersonEntity personEntity = personRepository.findOneBySsn(ssn);
        if (personEntity == null) {
            throw new EntityNotFoundException("PersonServiceImpl#findBySsn", ssn);
        }
        return new PersonDTO(personEntity);
    }
}
