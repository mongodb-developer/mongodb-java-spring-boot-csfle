package com.mongodb.quickstart.javaspringbootcsfle.services;

import com.mongodb.quickstart.javaspringbootcsfle.model.Person;
import org.bson.Document;

import java.util.List;

public interface PersonService { // todo useless interface?

    List<Document> findAll();

    Person save(Person person);
}
