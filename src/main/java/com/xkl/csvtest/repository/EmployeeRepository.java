package com.xkl.csvtest.repository;

import com.xkl.csvtest.database.employee.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    boolean existsByDocument(String document);

    Optional<Employee> findByDocument(String document);

    void deleteByDocument(String document);
}
