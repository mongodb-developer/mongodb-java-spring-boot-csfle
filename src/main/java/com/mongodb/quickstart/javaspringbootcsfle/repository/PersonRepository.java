package com.mongodb.quickstart.javaspringbootcsfle.repository;

import com.mongodb.quickstart.javaspringbootcsfle.model.Person;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PersonRepository extends MongoRepository<Person, String> {
}
