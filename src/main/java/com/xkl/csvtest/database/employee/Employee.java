package com.xkl.csvtest.database.employee;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Employee extends EmployeeAddress {
    @Id
    private String document;
    private String name;
    private String postalCode;
    private String companyDocument;

    public Employee() {
    }

    public Employee(
            String document,
            String name,
            String postalCode,
            String companyDocument,
            String uf, String city, String neighbourhood, String address, String complement
    ) {
        super();
        this.name = name;
        this.document = document;
        this.postalCode = postalCode;
        this.companyDocument = companyDocument;
        this.setUf(uf);
        this.setCity(city);
        this.setNeighbourhood(neighbourhood);
        this.setAddress(address);
        this.setComplement(complement);
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
