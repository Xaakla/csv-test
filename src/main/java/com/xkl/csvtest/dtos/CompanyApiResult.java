package com.xkl.csvtest.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class CompanyApiResult {
    private String cnpj;
    private String nome;
    private String fantasia;
    @JsonProperty("atividade_principal")
    private List<MainActivityDto> atividadePrincipal;
    private String porte;
    @JsonProperty("capital_social")
    private String capitalSocial;
    private String abertura;

    public CompanyApiResult() {
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getFantasia() {
        return fantasia;
    }

    public void setFantasia(String fantasia) {
        this.fantasia = fantasia;
    }

    public List<MainActivityDto> getAtividadePrincipal() {
        return atividadePrincipal;
    }

    public void setAtividadePrincipal(List<MainActivityDto> atividadePrincipal) {
        this.atividadePrincipal = atividadePrincipal;
    }

    public String getPorte() {
        return porte;
    }

    public void setPorte(String porte) {
        this.porte = porte;
    }

    public String getCapitalSocial() {
        return capitalSocial;
    }

    public void setCapitalSocial(String capitalSocial) {
        this.capitalSocial = capitalSocial;
    }

    public String getAbertura() {
        return abertura;
    }

    public void setAbertura(String abertura) {
        this.abertura = abertura;
    }
}
