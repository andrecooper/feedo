package com.home.feedo.dao;

import com.home.feedo.model.Quote;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Repository
public class MarketQuoteDao {

    private final static String FINANCE_URL = "http://charts.finance.ua/ua/currency/data-archive?for=order&source=1&indicator=usd";

    public List<Quote> getData(){
        System.out.println("FETCH DATA FROM: FINANCE.UA");
        RestTemplate restTemplate = new RestTemplate();
        List<Quote> marketQuoteList = new LinkedList<>();
        List<List<String>> marketQuotesRawData = restTemplate.getForObject(FINANCE_URL, List.class);
        for (List<String> marketPriceItemList : marketQuotesRawData) {
            Quote marketQuoteItem = new Quote();
            if (marketPriceItemList.size()<4){
                continue;
            }

            String dateString = marketPriceItemList.get(0);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date date = null;
            try {
                date = dateFormat.parse(dateString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            marketQuoteItem.setDate(date);
            marketQuoteItem.setBid(Double.valueOf(marketPriceItemList.get(1)));
            marketQuoteItem.setAsk(Double.valueOf(marketPriceItemList.get(2)));
            marketQuoteItem.setAvarage(Double.valueOf(marketPriceItemList.get(3)));
            marketQuoteList.add(marketQuoteItem);
        }

        return marketQuoteList;
    }

}
