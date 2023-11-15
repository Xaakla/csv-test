package com.xkl.csvtest.database.employee;

import com.xkl.csvtest.dtos.CompanyApiResult;
import com.xkl.csvtest.dtos.CompanyDto;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
public class Company {
    @Id
    private String cnpj;
    private String name;
    private String tradingName;
    private String mainActivity;
    private String size;
    private Double capital;
    private Date openingDate;

    public Company() {
    }

    public Company(CompanyDto companyDto) {
        this.cnpj = companyDto.getCnpj();
        this.name = companyDto.getName();
        this.tradingName = companyDto.getTradingName();
        this.mainActivity = companyDto.getMainActivity();
        this.size = companyDto.getSize();
        this.capital = companyDto.getCapital();
        this.openingDate = companyDto.getOpeningDate();
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTradingName() {
        return tradingName;
    }

    public void setTradingName(String tradingName) {
        this.tradingName = tradingName;
    }

    public String getMainActivity() {
        return mainActivity;
    }

    public void setMainActivity(String mainActivity) {
        this.mainActivity = mainActivity;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Double getCapital() {
        return capital;
    }

    public void setCapital(Double capital) {
        this.capital = capital;
    }

    public Date getOpeningDate() {
        return openingDate;
    }

    public void setOpeningDate(Date openingDate) {
        this.openingDate = openingDate;
    }
}
