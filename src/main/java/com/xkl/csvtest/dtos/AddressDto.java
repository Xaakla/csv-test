package com.xkl.csvtest.dtos;

public class AddressDto {
    private String uf;
    private String city;
    private String neighbourhood;
    private String place;
    private String complement;

    public AddressDto() {
    }

    public AddressDto(String uf, String city, String neighbourhood, String place, String complement) {
        this.uf = uf;
        this.city = city;
        this.neighbourhood = neighbourhood;
        this.place = place;
        this.complement = complement;
    }

    public AddressDto(PostalCodeApiResult postalCodeApiResult) {
        this.uf = postalCodeApiResult.getUf();
        this.city = postalCodeApiResult.getLocalidade();
        this.neighbourhood = postalCodeApiResult.getBairro();
        this.place = postalCodeApiResult.getLogradouro();
        this.complement = postalCodeApiResult.getComplemento();
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
