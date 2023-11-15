package com.xkl.csvtest.services;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReaderBuilder;
import com.xkl.csvtest.database.employee.Company;
import com.xkl.csvtest.database.employee.Employee;
import com.xkl.csvtest.dtos.AddressDto;
import com.xkl.csvtest.dtos.CompanyDto;
import com.xkl.csvtest.dtos.EmployeeDto;
import com.xkl.csvtest.repository.CompanyRepository;
import com.xkl.csvtest.repository.EmployeeRepository;
import jakarta.transaction.Transactional;
import org.apache.commons.io.input.CharSequenceReader;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static java.util.Map.*;
import static java.util.Map.entry;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepo;
    private final CompanyRepository companyRepository;
    private final AddressService addressService;
    private final CompanyService companyService;
    private final PostalCodeService postalCodeService;
    private final CNPJService cnpjService;

    public EmployeeService(EmployeeRepository employeeRepo, CompanyRepository companyRepository, AddressService addressService, CompanyService companyService, PostalCodeService postalCodeService, CNPJService cnpjService) {
        this.employeeRepo = employeeRepo;
        this.companyRepository = companyRepository;
        this.addressService = addressService;
        this.companyService = companyService;
        this.postalCodeService = postalCodeService;
        this.cnpjService = cnpjService;
    }

    public Set<EmployeeDto> findAllEmployees() {
        return employeeRepo.findAll().stream().map(EmployeeDto::new).collect(Collectors.toSet());
    }

    @Transactional
    public EmployeeDto addEmployee(String name, String document, String postalCode, String companyDocument) throws ParseException {
        if (employeeRepo.existsByDocument(document)) {
            throw new RuntimeException("Cannot add a new employee with the same document: " + document + ".");
        }

        Company company;
        if (companyRepository.existsByCnpj(companyDocument)) {
            company = companyService.findCompanyByCnpj(companyDocument);
        } else {
            company = companyService.addCompany(new CompanyDto(cnpjService.findCompanyByCNPJ(companyDocument)));
        }

        var employeeAddress = postalCodeService.findAddressByPostalCode(postalCode);
        var address = addressService.addAddress(new AddressDto(employeeAddress));

        var employee = new Employee(document, name, postalCode, companyDocument, address, company);

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

        employee.getAddress().setUf(employeeAddress.getUf());
        employee.getAddress().setCity(employeeAddress.getLocalidade());
        employee.getAddress().setNeighbourhood(employeeAddress.getBairro());
        employee.getAddress().setPlace(employeeAddress.getLogradouro());
        employee.getAddress().setComplement(employeeAddress.getComplemento());
        employee.setPostalCode(postalCode);

        return new EmployeeDto(employeeRepo.save(employee));
    }

    @Transactional
    public EmployeeDto editEmployeeCompanyDocument(String document, String companyDocument) throws ParseException {
        var employee = employeeRepo.findByDocument(document)
                .orElseThrow(() -> new RuntimeException("Employee document " + document + " not found."));

        Company company;

        if (companyRepository.existsByCnpj(companyDocument)) {
            company = companyService.findCompanyByCnpj(companyDocument);
        } else {
            company = companyService.addCompany(new CompanyDto(cnpjService.findCompanyByCNPJ(companyDocument)));
        }

        employee.setCompanyDocument(companyDocument);
        employee.setCompany(company);
        return new EmployeeDto(employeeRepo.save(employee));
    }

    @Transactional
    public void deleteEmployee(String document) {
        var employee = employeeRepo.findByDocument(document)
                .orElseThrow(() -> new RuntimeException("Employee document " + document + " not found."));

        addressService.deleteAddress(employee.getAddress().getId());
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

                if (currentLine.get() > 0) {
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
            var addressFound = postalCodeService.findAddressByPostalCode(it.getPostalCode());
            var companyFound = cnpjService.findCompanyByCNPJ(it.getCompanyDocument());

            var address = addressService.addAddress(new AddressDto(addressFound));
            Company company;
            try {
                company = companyService.addCompany(new CompanyDto(companyFound));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

            employeesList.add(new Employee(it.getDocument(), it.getName(), it.getPostalCode(), it.getCompanyDocument(), address, company));
        });

        employeeRepo.saveAll(employeesList);

        return ofEntries(
                entry("success", success),
                entry("errors", errors));
    }

    @Transactional
    public EmployeeDto parse(String[] line) {
        try {
            return new EmployeeDto(
                    Optional.of(line[0].replaceAll("[^0-9]", "").trim()).orElse(null),
                    Optional.of(line[1].trim()).orElse(""),
                    Optional.of(line[2].replaceAll("[^0-9]", "").trim()).orElse(null),
                    Optional.of(line[3].replaceAll("[^0-9]", "").trim()).orElse(null),
                    new AddressDto(), new CompanyDto());
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
