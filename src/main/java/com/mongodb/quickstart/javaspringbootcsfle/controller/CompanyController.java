package com.mongodb.quickstart.javaspringbootcsfle.controller;

import com.mongodb.quickstart.javaspringbootcsfle.csfleServiceImpl.MasterKeyServiceImpl;
import com.mongodb.quickstart.javaspringbootcsfle.dto.CompanyDTO;
import com.mongodb.quickstart.javaspringbootcsfle.exception.EntityNotFoundException;
import com.mongodb.quickstart.javaspringbootcsfle.service.CompanyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
        return companyService.findAll();
    }

    @PostMapping("/company")
    @ResponseStatus(HttpStatus.CREATED)
    public CompanyDTO createPerson(@RequestBody CompanyDTO companyDTO) {
        return companyService.save(companyDTO);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public final Exception handleNotFoundExceptions(EntityNotFoundException e) {
        LOGGER.info("=> Company not found: {}", e.toString());
        return null;
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Internal Server Error")
    public final void handleAllExceptions(RuntimeException e) {
        LOGGER.error("=> Internal server error.", e);
    }
}
