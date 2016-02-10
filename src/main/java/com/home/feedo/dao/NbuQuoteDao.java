package com.home.feedo.dao;

import com.home.feedo.entity.NbuQuote;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Repository
public class NbuQuoteDao {

    private static final String MINFIN_URL = "http://minfin.com.ua/data/currency/ib/usd.ib.stock.json";

    public List<NbuQuote> getData(){
        System.out.println("FETCH DATA FROM: MINFIN.COM.UA");
        RestTemplate restTemplate = new RestTemplate();
        ParameterizedTypeReference<List<NbuQuote>> typeRef = new ParameterizedTypeReference<List<NbuQuote>>() {};
        ResponseEntity<List<NbuQuote>> listResponseEntity = restTemplate.exchange(MINFIN_URL, HttpMethod.GET, null, typeRef);
        return listResponseEntity.getBody();
    }

}
