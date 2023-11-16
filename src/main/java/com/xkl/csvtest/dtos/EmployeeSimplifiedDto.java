package com.xkl.csvtest.dtos;

public class EmployeeSimplifiedDto {
    private String document;
    private String name;
    private String postalCode;
    private String companyDocument;

    public EmployeeSimplifiedDto(EmployeeDto employeeDto) {
        this.document = employeeDto.getDocument();
        this.name = employeeDto.getName();
        this.postalCode = employeeDto.getPostalCode();
        this.companyDocument = employeeDto.getCompanyDocument();
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
}
