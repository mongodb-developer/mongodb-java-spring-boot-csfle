package com.mongodb.quickstart.javaspringbootcsfle.serviceImpl;

import com.mongodb.quickstart.javaspringbootcsfle.dto.CompanyDTO;
import com.mongodb.quickstart.javaspringbootcsfle.exception.EntityNotFoundException;
import com.mongodb.quickstart.javaspringbootcsfle.model.CompanyEntity;
import com.mongodb.quickstart.javaspringbootcsfle.repository.CompanyRepository;
import com.mongodb.quickstart.javaspringbootcsfle.service.CompanyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Company Service.
 * Contains the business logic layer between the Controller and Repositories (database).
 */
@Service
public class CompanyServiceImpl implements CompanyService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyServiceImpl.class);
    private final CompanyRepository companyRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public List<CompanyDTO> findAll() {
        LOGGER.info("=> Find all companies.");
        List<CompanyDTO> results = companyRepository.findAll().stream().map(CompanyDTO::new).toList();
        if (results.isEmpty()) {
            throw new EntityNotFoundException("CompanyServiceImpl#findAll");
        }
        return results;
    }

    @Override
    public CompanyDTO save(CompanyDTO companyDTO) {
        CompanyEntity company = companyDTO.toCompanyEntity();
        LOGGER.info("=> Saving company: {}", company);
        CompanyEntity companySaved = companyRepository.save(company);
        return new CompanyDTO(companySaved);
    }
}
