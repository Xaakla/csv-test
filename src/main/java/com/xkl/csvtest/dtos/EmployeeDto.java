package com.xkl.csvtest.dtos;

import com.xkl.csvtest.database.employee.Employee;

public class EmployeeDto {
    private String document;
    private String name;
    private String postalCode;
    private String companyDocument;
    private AddressDto address;
    private CompanyDto company;

    public EmployeeDto() {}

    public EmployeeDto(String document, String name, String postalCode, String companyDocument, AddressDto address, CompanyDto company) {
        this.setDocument(document);
        this.setName(name);
        this.setPostalCode(postalCode);
        this.setCompanyDocument(companyDocument);
        this.setAddress(address);
        this.setCompany(company);
    }

    public EmployeeDto(Employee employee) {
        this.setDocument(employee.getDocument());
        this.setName(employee.getName());
        this.setPostalCode(employee.getPostalCode());
        this.setCompanyDocument(employee.getCompanyDocument());
        this.setAddress(
                new AddressDto(employee.getAddress().getUf(),
                        employee.getAddress().getCity(),
                        employee.getAddress().getNeighbourhood(),
                        employee.getAddress().getPlace(),
                        employee.getAddress().getComplement()));
        this.setCompany(new CompanyDto(employee.getCompany()));
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

    public AddressDto getAddress() {
        return address;
    }

    public void setAddress(AddressDto address) {
        this.address = address;
    }

    public CompanyDto getCompany() {
        return company;
    }

    public void setCompany(CompanyDto company) {
        this.company = company;
    }
}
