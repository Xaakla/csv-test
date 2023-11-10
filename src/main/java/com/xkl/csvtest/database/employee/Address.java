package com.xkl.csvtest.database.employee;

import com.xkl.csvtest.dtos.AddressDto;
import com.xkl.csvtest.dtos.PostalCodeApiResult;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Address {
    @Id
    @GeneratedValue
    private Long id;
    private String uf;
    private String city;
    private String neighbourhood;
    private String place;
    private String complement;

    public Address() {}

    public Address(String uf, String city, String neighbourhood, String place, String complement) {
        this.uf = uf;
        this.city = city;
        this.neighbourhood = neighbourhood;
        this.place = place;
        this.complement = complement;
    }

    public Address(PostalCodeApiResult postalCodeApiResult) {
        this.uf = postalCodeApiResult.getUf();
        this.city = postalCodeApiResult.getLocalidade();
        this.neighbourhood = postalCodeApiResult.getBairro();
        this.place = postalCodeApiResult.getLogradouro();
        this.complement = postalCodeApiResult.getComplemento();
    }

    public Address(AddressDto address) {
        this.uf = address.getUf();
        this.city = address.getCity();
        this.neighbourhood = address.getNeighbourhood();
        this.place = address.getPlace();
        this.complement = address.getComplement();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }
}
