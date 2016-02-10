package com.home.feedo.service;

import com.home.feedo.component.QuoteFilter;
import com.home.feedo.dao.MarketQuoteDao;
import com.home.feedo.entity.Quote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by andrew on 09.02.16.
 */

@Service
public class MarketService implements Callable<List<Quote>> {

    @Autowired
    private MarketQuoteDao quoteDao;

    @Autowired
    QuoteFilter quoteFilter;

    private Date startDate;
    private Date endDate;

    public MarketService() {
    }

    public MarketService(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public List<Quote> call() throws Exception {
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd:MM:yyyy");
        try {
            startDate = dateFormat1.parse("05:01:2016");
            endDate = dateFormat1.parse("31:01:2016");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        List<Quote> marketQuoteList = quoteDao.getData();
        quoteFilter.filterByDate(marketQuoteList,startDate,endDate);

        return marketQuoteList;
    }


}
