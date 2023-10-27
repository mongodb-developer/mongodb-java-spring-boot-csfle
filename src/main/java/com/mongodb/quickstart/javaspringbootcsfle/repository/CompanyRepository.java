package com.mongodb.quickstart.javaspringbootcsfle.repository;

import com.mongodb.quickstart.javaspringbootcsfle.model.CompanyEntity;
import com.mongodb.quickstart.javaspringbootcsfle.model.PersonEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the CompanyEntity
 */
@Repository
public interface CompanyRepository extends MongoRepository<CompanyEntity, String> {
}
