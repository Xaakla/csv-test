package com.xkl.csvtest.database.employee;

import jakarta.persistence.*;

@Entity
public class Employee {
    @Id
    private String document;
    private String name;
    private String postalCode;
    private String companyDocument;
    @OneToOne
    private Address address;

    @ManyToOne
    private Company company;

    public Employee() {
    }

    public Employee(
            String document,
            String name,
            String postalCode,
            String companyDocument,
            Address address,
            Company company
    ) {
        this.name = name;
        this.document = document;
        this.postalCode = postalCode;
        this.companyDocument = companyDocument;
        this.address = address;
        this.company = company;
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
