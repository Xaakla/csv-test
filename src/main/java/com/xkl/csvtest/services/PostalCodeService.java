package com.xkl.csvtest.services;

import com.xkl.csvtest.dtos.PostalCodeApiResult;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PostalCodeService {
    public PostalCodeApiResult findAddressByPostalCode(String postalCode) {
        return new RestTemplate()
                .getForEntity(String.format("https://viacep.com.br/ws/%s/json", postalCode), PostalCodeApiResult.class)
                .getBody();
    }
}
