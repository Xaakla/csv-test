package com.xkl.csvtest.dtos;

import com.xkl.csvtest.database.employee.Employee;
import com.xkl.csvtest.database.employee.EmployeeAddress;

public class EmployeeDto {
    private String document;
    private String name;
    private String postalCode;
    private String companyDocument;
    private EmployeeAddress address;

    public EmployeeDto() {}

    public EmployeeDto(String document, String name, String postalCode, String companyDocument) {
        this.setDocument(document);
        this.setName(name);
        this.setPostalCode(postalCode);
        this.setCompanyDocument(companyDocument);
    }

    public EmployeeDto(Employee employee) {
        this.setDocument(employee.getDocument());
        this.setName(employee.getName());
        this.setPostalCode(employee.getPostalCode());
        this.setCompanyDocument(employee.getCompanyDocument());
        this.setAddress(
                new EmployeeAddress(employee.getUf(),
                        employee.getCity(),
                        employee.getNeighbourhood(),
                        employee.getAddress(),
                        employee.getComplement()));
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCompanyDocument() {
        return companyDocument;
    }

    public void setCompanyDocument(String companyDocument) {
        this.companyDocument = companyDocument;
    }

    public EmployeeAddress getAddress() {
        return address;
    }

    public void setAddress(EmployeeAddress address) {
        this.address = address;
    }
}
