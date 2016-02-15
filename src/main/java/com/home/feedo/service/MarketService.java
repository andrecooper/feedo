package com.home.feedo.service;

import com.home.feedo.component.QuoteFilter;
import com.home.feedo.dao.MarketQuoteDao;
import com.home.feedo.model.Quote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.concurrent.Callable;

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
        List<Quote> marketQuoteList = quoteDao.getData();
        quoteFilter.filterByDate(marketQuoteList, startDate, endDate);
        return marketQuoteList;
    }


    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
