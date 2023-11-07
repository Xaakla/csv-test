package com.xkl.csvtest.database.employee;

import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public class EmployeeAddress {
    private String uf;
    private String city;
    private String neighbourhood;
    private String address;
    private String complement;

    public EmployeeAddress() {}

    public EmployeeAddress(String uf, String city, String neighbourhood, String address, String complement) {
        this.uf = uf;
        this.city = city;
        this.neighbourhood = neighbourhood;
        this.address = address;
        this.complement = complement;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getNeighbourhood() {
        return neighbourhood;
    }

    public void setNeighbourhood(String neighbourhood) {
        this.neighbourhood = neighbourhood;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }
}
