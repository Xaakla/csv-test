package com.xkl.csvtest.services;

import com.xkl.csvtest.dtos.CompanyApiResult;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CNPJService {
    public CompanyApiResult findCompanyByCNPJ(String cnpj) {
        return new RestTemplate()
                .getForEntity(String.format("https://receitaws.com.br/v1/cnpj/%s", cnpj), CompanyApiResult.class)
                .getBody();
    }
}
