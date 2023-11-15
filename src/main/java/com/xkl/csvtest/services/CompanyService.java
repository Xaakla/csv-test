package com.xkl.csvtest.services;

import com.xkl.csvtest.database.employee.Company;
import com.xkl.csvtest.dtos.CompanyDto;
import com.xkl.csvtest.repository.CompanyRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {
    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public Company findCompanyByCnpj(String cnpj) {
        return companyRepository.findByCnpj(cnpj);
    }

    @Transactional
    public Company addCompany(CompanyDto companyDto) {
        return companyRepository.save(new Company(companyDto));
    }
}
