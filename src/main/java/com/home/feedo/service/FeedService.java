package com.home.feedo.service;

import com.home.feedo.model.Quote;
import com.home.feedo.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
public class FeedService {

    @Autowired
    private NbuService nbuService;

    @Autowired
    private MarketService marketService;

    public List<Quote> getData(){
        System.out.println("FEED SERVICE CALL");

        Date startDate = new Date();
        Date endDate = new Date();
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd:MM:yyyy");
        try {
            startDate = dateFormat1.parse("05:01:2016");
            endDate = dateFormat1.parse("31:01:2016");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        ExecutorService executorService = Executors.newCachedThreadPool();
        Future<List<Quote>> marketDataFuture = executorService.submit(marketService);
        Future<List<Quote>> nbuDataFuture = executorService.submit(nbuService);

        LinkedList<Quote> nbuQuotes = null;
        LinkedList<Quote> marketQuotes = null;
        try {
            nbuQuotes = new LinkedList<>(nbuDataFuture.get());
            marketQuotes = new LinkedList<>(marketDataFuture.get());
            System.out.println("NBU FUTURE: " + nbuQuotes);
            System.out.println("MARKET FUTURE: " + marketQuotes);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return getDiff(startDate, endDate, nbuQuotes, marketQuotes);
    }

    private List<Quote> getDiff(Date startDate, Date endDate, LinkedList<Quote> nbuQuotes, LinkedList<Quote> marketQuotes) {
        HashMap<Date,Quote> nbuQuoteMap = new HashMap<>();
        for (Quote quote : nbuQuotes) {
            nbuQuoteMap.put(quote.getDate(), quote);
        }

        HashMap<Date,Quote> marketQuoteMap = new HashMap<>();
        for (Quote quote : marketQuotes) {
            marketQuoteMap.put(quote.getDate(), quote);
        }

        List<Date> datesPeriod = DateUtils.getPeriod(startDate, endDate);
        List<Quote> diffQuoteList = new LinkedList<>();
        for (Date date : datesPeriod) {
            Quote nbuQuote = nbuQuoteMap.get(date);
            Quote marketQuote = marketQuoteMap.get(date);

            Quote diffQuote = new Quote();
            diffQuote.setDate(date);
            diffQuote.setBid(marketQuote.getBid()-nbuQuote.getBid());
            diffQuote.setAsk(marketQuote.getAsk()-nbuQuote.getAsk());
            diffQuote.setAvarage(marketQuote.getAsk()-nbuQuote.getAsk());

            diffQuoteList.add(diffQuote);
        }
        return diffQuoteList;
    }
}
