package com.xkl.csvtest.controllers;

import com.xkl.csvtest.Routes;
import com.xkl.csvtest.dtos.EmployeeDto;
import com.xkl.csvtest.services.EmployeeService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.Map;
import java.util.Set;

@CrossOrigin
@RestController
@RequestMapping(Routes.employees)
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public Set<EmployeeDto> findAllEmployees() {
        return employeeService.findAllEmployees();
    }

    @PostMapping
    public EmployeeDto addEmployee(@RequestParam String name,
                                      @RequestParam String document,
                                      @RequestParam String postalCode,
                                      @RequestParam String companyDocument) throws ParseException {
        return employeeService.addEmployee(name, document, postalCode, companyDocument);
    }

    @PatchMapping("/{document}/edit/name")
    public EmployeeDto editEmployeeName(@PathVariable String document,
                                        @RequestParam String name) {
        return employeeService.editEmployeeName(document, name);
    }

    @PatchMapping("/{document}/edit/postalCode")
    public EmployeeDto editEmployeePostalCode(@PathVariable String document,
                                              @RequestParam String postalCode) {
        return employeeService.editEmployeePostalCode(document, postalCode);
    }

    @PatchMapping("/{document}/edit/companyDocument")
    public EmployeeDto editEmployeeCompanyDocument(@PathVariable String document,
                                                   @RequestParam String companyDocument) throws ParseException {
        return employeeService.editEmployeeCompanyDocument(document, companyDocument);
    }

    @DeleteMapping("/{document}")
    public void deleteEmployee(@PathVariable String document) {
        employeeService.deleteEmployee(document);
    }

    @PostMapping(value = "/csv/upload", consumes = "multipart/form-data")
    public Map<String, Object> readEmployeesCsv(@RequestParam("file") MultipartFile employeesCsvFile) throws IOException {
        return employeeService.readEmployeesCsv(employeesCsvFile.getContentType(), employeesCsvFile.getBytes());
    }
}
