package com.mongodb.quickstart.javaspringbootcsfle.service;

import com.mongodb.quickstart.javaspringbootcsfle.dto.CompanyDTO;

import java.util.List;

public interface CompanyService {
    List<CompanyDTO> findAll();

    CompanyDTO save(CompanyDTO company);
}
