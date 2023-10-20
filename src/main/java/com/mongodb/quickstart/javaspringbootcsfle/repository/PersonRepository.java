package com.mongodb.quickstart.javaspringbootcsfle.repository;

import com.mongodb.quickstart.javaspringbootcsfle.model.PersonEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends MongoRepository<PersonEntity, String> {
}
