package com.mongodb.quickstart.javaspringbootcsfle.controller;

import com.mongodb.quickstart.javaspringbootcsfle.csfleServiceImpl.MasterKeyServiceImpl;
import com.mongodb.quickstart.javaspringbootcsfle.dto.CompanyDTO;
import com.mongodb.quickstart.javaspringbootcsfle.service.CompanyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * REST API for the companies.
 */
@RestController
public class CompanyController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MasterKeyServiceImpl.class);
    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping("/companies")
    public List<CompanyDTO> getAllPersons() {
        try {
            return companyService.findAll();
        } catch (Exception exception) {
            LOGGER.error("=> Couldn't retrieve the Companies from MongoDB.");// todo fix all this
            return new ArrayList<>();
        }
    }

    @PostMapping("/company")
    @ResponseStatus(HttpStatus.CREATED)
    public CompanyDTO createPerson(@RequestBody CompanyDTO companyDTO) {
        LOGGER.info("Saving person: {}", companyDTO);
        return companyService.save(companyDTO);
    }
}
