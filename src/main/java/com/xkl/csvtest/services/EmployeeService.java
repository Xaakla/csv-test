package com.xkl.csvtest.services;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReaderBuilder;
import com.xkl.csvtest.database.employee.Employee;
import com.xkl.csvtest.dtos.EmployeeDto;
import com.xkl.csvtest.repository.EmployeeRepository;
import jakarta.transaction.Transactional;
import org.apache.commons.io.input.CharSequenceReader;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static java.util.Map.*;
import static java.util.Map.entry;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepo;
    private final PostalCodeService postalCodeService;

    public EmployeeService(EmployeeRepository employeeRepo, PostalCodeService postalCodeService) {
        this.employeeRepo = employeeRepo;
        this.postalCodeService = postalCodeService;
    }

    public Set<EmployeeDto> findAllEmployees() {
        return employeeRepo.findAll().stream().map(EmployeeDto::new).collect(Collectors.toSet());
    }

    @Transactional
    public EmployeeDto addEmployee(String name, String document, String postalCode, String companyDocument) {
        if (employeeRepo.existsByDocument(document)) {
            throw new RuntimeException("Cannot add a new employee with the same document: " + document + ".");
        }

        var employeeAddress = postalCodeService.findAddressByPostalCode(postalCode);

        var employee = new Employee(
                document, name, postalCode, companyDocument,
                employeeAddress.getUf(),
                employeeAddress.getLocalidade(),
                employeeAddress.getBairro(),
                employeeAddress.getLogradouro(),
                employeeAddress.getComplemento());

        return new EmployeeDto(employeeRepo.save(employee));
    }

    @Transactional
    public EmployeeDto editEmployeeName(String document, String name) {
        var employee = employeeRepo.findByDocument(document)
                .orElseThrow(() -> new RuntimeException("Employee document " + document + " not found."));

        employee.setName(name);
        return new EmployeeDto(employeeRepo.save(employee));
    }

    @Transactional
    public EmployeeDto editEmployeePostalCode(String document, String postalCode) {
        var employee = employeeRepo.findByDocument(document)
                .orElseThrow(() -> new RuntimeException("Employee document " + document + " not found."));

        var employeeAddress = postalCodeService.findAddressByPostalCode(postalCode);

        employee.setUf(employeeAddress.getUf());
        employee.setCity(employeeAddress.getLocalidade());
        employee.setNeighbourhood(employeeAddress.getBairro());
        employee.setAddress(employeeAddress.getLogradouro());
        employee.setComplement(employeeAddress.getComplemento());
        employee.setPostalCode(postalCode);

        return new EmployeeDto(employeeRepo.save(employee));
    }

    @Transactional
    public EmployeeDto editEmployeeCompanyDocument(String document, String companyDocument) {
        var employee = employeeRepo.findByDocument(document)
                .orElseThrow(() -> new RuntimeException("Employee document " + document + " not found."));

        employee.setCompanyDocument(companyDocument);
        return new EmployeeDto(employeeRepo.save(employee));
    }

    @Transactional
    public void deleteEmployee(String document) {
        if (!employeeRepo.existsByDocument(document)) {
            throw new RuntimeException("Employee document " + document + " not found.");
        }

        employeeRepo.deleteByDocument(document);
    }

    @Transactional
    public Map<String, Object> readEmployeesCsv(String contentType, byte[] bytes) {
        var reader = new CharSequenceReader(new String(bytes, StandardCharsets.UTF_8));

        validateFileFormat(contentType);

        List<EmployeeDto> success = new ArrayList<>();
        List<Map<String, Object>> errors = new ArrayList<>();

        try {
            var csvReader = new CSVReaderBuilder(reader)
                    .withCSVParser(new CSVParserBuilder()
                            .withSeparator(';')
                            .withIgnoreQuotations(true)
                            .build())
                    .build();

            var currentLine = new AtomicInteger();

            for (String[] line : csvReader) {
                List<Map<String, Object>> errorsInLine = new ArrayList<>();
                EmployeeDto employee = parse(line);

                if (currentLine.get() > 0)  {
                    if (Optional.ofNullable(employee).isEmpty()) {
                        errors.add(of(
                                "line", currentLine.get(),
                                "reason", "Error when converting object"));
                        continue;
                    }

                    if (employeeRepo.existsByDocument(employee.getDocument())) {
                        errorsInLine.add(of(
                                "field", "document",
                                "data", Optional.ofNullable(employee.getDocument()).orElse(""),
                                "reason", "Given document already exists"));
                    }

                    if (Optional.ofNullable(employee.getName()).isEmpty()) {
                        errorsInLine.add(of(
                                "field", "name",
                                "data", Optional.ofNullable(employee.getName()).orElse(""),
                                "reason", "Given name is invalid"));
                    }

                    if (Optional.ofNullable(employee.getPostalCode()).isEmpty()) {
                        errorsInLine.add(of(
                                "field", "postalCode",
                                "data", Optional.ofNullable(employee.getPostalCode()).orElse(""),
                                "reason", "Given postalCode is invalid"));
                    }

                    if (Optional.ofNullable(employee.getCompanyDocument()).isEmpty()) {
                        errorsInLine.add(of(
                                "field", "companyDocument",
                                "data", Optional.ofNullable(employee.getCompanyDocument()).orElse(""),
                                "reason", "Given companyDocument is invalid"));
                    }

                    if (!errorsInLine.isEmpty()) {
                        errors.add(of("line", currentLine.get(), "errors", errorsInLine, "document", employee.getDocument(), "name", employee.getName()));
                        continue;
                    }
                    success.add(employee);
                }

                currentLine.incrementAndGet();
            }

            reader.close();
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getCause());
        }

        var employeesList = new ArrayList<Employee>();
        success.forEach((it) -> {
            var employeeAddress = postalCodeService.findAddressByPostalCode(it.getPostalCode());

            employeesList.add(
                    new Employee(
                            it.getDocument(), it.getName(), it.getPostalCode(), it.getCompanyDocument(),
                            employeeAddress.getUf(),
                            employeeAddress.getLocalidade(),
                            employeeAddress.getBairro(),
                            employeeAddress.getLogradouro(),
                            employeeAddress.getComplemento()));
        });

        employeeRepo.saveAll(employeesList);

        return ofEntries(
                entry("success", success),
                entry("errors", errors));
    }

    private EmployeeDto parse(String[] line) {
        try {
            return new EmployeeDto(
                    Optional.of(line[0].replaceAll("[^0-9]", "").trim()).orElse(null),
                    Optional.of(line[1].trim()).orElse(""),
                    Optional.of(line[2].replaceAll("[^0-9]", "").trim()).orElse(null),
                    Optional.of(line[3].replaceAll("[^0-9]", "").trim()).orElse(null));
        } catch (Exception e) {
            return null;
        }
    }

    public static void validateFileFormat(String contentType) {
        if (!List.of(
                "text/csv",
                "text/plain",
                "application/x-idea-csv",
                "application/vnd.ms-excel",
                "application/csv",
                "application/x-csv",
                "text/comma-separated-values",
                "text/x-comma-separated-values",
                "text/tab-separated-values").contains(contentType))
            throw new RuntimeException("Invalid file format");
    }
}