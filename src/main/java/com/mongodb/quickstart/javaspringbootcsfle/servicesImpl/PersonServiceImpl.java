package com.mongodb.quickstart.javaspringbootcsfle.servicesImpl;

import com.mongodb.quickstart.javaspringbootcsfle.model.Person;
import com.mongodb.quickstart.javaspringbootcsfle.services.PersonService;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;

// TODO remove this class? Useless?
public class PersonServiceImpl implements PersonService {

    private final MongoTemplate mongoTemplate;

    public PersonServiceImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<Document> findAll() {
        return null;
    }

    @Override
    public Person save(Person person) {
        return null;
    }
}
