package com.xkl.csvtest.repository;

import com.xkl.csvtest.database.employee.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    boolean existsByCnpj(String cnpj);
    Company findByCnpj(String cnpj);
}
