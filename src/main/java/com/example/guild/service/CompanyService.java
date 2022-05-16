package com.example.guild.service;

import com.example.guild.controller.dto.CompanyDto;
import com.example.guild.repository.CompanyDetailsRepository;
import com.example.guild.repository.CompanyRepository;
import com.example.guild.repository.entity.Company;
import com.example.guild.repository.entity.reporting.CompanyDetails;
import com.example.guild.repository.entity.compositePK.CompanyYearPK;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CompanyService {

    CompanyDetailsRepository companyDetailsRepository;
    CompanyRepository companyRepository;


    public CompanyDto getCompany(long companyId, int year) {
        Optional<Company> company = companyRepository.findById(companyId);
        if (!company.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Company " + companyId + " does not exist.");
        CompanyDetails companyDetails = getCompanyDetails(companyId, year);
        return new CompanyDto(companyId, company.get().getName(), companyDetails.getYear(), companyDetails.getBudget(), companyDetails.getRevenue());
    }

    private CompanyDetails getCompanyDetails(long companyId, int year) {
        Optional<CompanyDetails> companyDetailsByYear = companyDetailsRepository.findById(new CompanyYearPK(companyId, year));
        if (companyDetailsByYear.isPresent())
            return companyDetailsByYear.get();
        else
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Company " + companyId + " has no info for the year " + year);
    }
}
