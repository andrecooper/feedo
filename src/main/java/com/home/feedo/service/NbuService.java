package com.home.feedo.service;

import com.home.feedo.component.QuoteFilter;
import com.home.feedo.dao.NbuQuoteDao;
import com.home.feedo.model.NbuQuote;
import com.home.feedo.model.Quote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;

@Service
public class NbuService implements Callable<List<Quote>> {

    @Autowired
    private NbuQuoteDao quoteDao;

    @Autowired
    QuoteFilter quoteFilter;

    private Date startDate;
    private Date endDate;

    public NbuService(){

    }

    public NbuService(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public List<Quote> call() throws Exception {
        List<NbuQuote> minfinPricesList = quoteDao.getData();
        List<Quote> quoteList = convertToQuoteList(minfinPricesList);
        quoteFilter.filterByDate(quoteList, startDate,endDate);
        return quoteList;
    }

    private List<Quote> convertToQuoteList(List<NbuQuote> minfinPricesList) {
        List<Quote> quoteList = new LinkedList<>();
        for (NbuQuote nbuQuote : minfinPricesList) {
            Quote quote = new Quote();
            quote.setDate(nbuQuote.getDate());

            BigDecimal ask = nbuQuote.getAsk();
            BigDecimal bid = nbuQuote.getBid();

            quote.setAsk(ask);
            quote.setBid(bid);
            quote.setAverage((ask.add(bid)).divide(BigDecimal.valueOf(2)));
            quoteList.add(quote);
        }
        return quoteList;
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
