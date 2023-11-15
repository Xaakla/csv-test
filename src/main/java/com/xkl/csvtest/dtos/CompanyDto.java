package com.xkl.csvtest.dtos;

import com.xkl.csvtest.database.employee.Company;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CompanyDto {
    private String cnpj;
    private String name;
    private String tradingName;
    private String mainActivity;
    private String size;
    private Double capital;
    private Date openingDate;

    public CompanyDto() {
    }

    public CompanyDto(CompanyApiResult companyApiResult) throws ParseException {
        this.cnpj = companyApiResult.getCnpj();
        this.name = companyApiResult.getNome();
        this.tradingName = companyApiResult.getFantasia();
        this.mainActivity = companyApiResult.getAtividadePrincipal().get(0).getText();
        this.size = companyApiResult.getPorte();
        this.capital = Double.parseDouble(companyApiResult.getCapitalSocial());
        this.openingDate = new SimpleDateFormat("dd/MM/yyyy").parse(companyApiResult.getAbertura());
    }

    public CompanyDto(Company company) {
        this.cnpj = company.getCnpj();
        this.name = company.getName();
        this.tradingName = company.getTradingName();
        this.mainActivity = company.getMainActivity();
        this.size = company.getSize();
        this.capital = company.getCapital();
        this.openingDate = company.getOpeningDate();
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
