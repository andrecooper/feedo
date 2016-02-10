package com.home.feedo.service;

import com.home.feedo.component.QuoteFilter;
import com.home.feedo.dao.NbuQuoteDao;
import com.home.feedo.entity.NbuQuote;
import com.home.feedo.entity.Quote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by andrew on 09.02.16.
 */

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
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd:MM:yyyy");
        try {
            startDate = dateFormat1.parse("05:01:2016");
            endDate = dateFormat1.parse("31:01:2016");
        } catch (ParseException e) {
            e.printStackTrace();
        }

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
            Double ask = nbuQuote.getAsk();
            Double bid = nbuQuote.getBid();
            quote.setAsk(ask);
            quote.setBid(bid);
            quote.setAvarage((bid+ask)/2);
            quoteList.add(quote);
        }
        return quoteList;
    }

}
