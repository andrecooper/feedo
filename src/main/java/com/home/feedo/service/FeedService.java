package com.home.feedo.service;

import com.home.feedo.model.Quote;
import com.home.feedo.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


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

    public List<Quote> getDiffData(Date startDate, Date endDate) {
        System.out.println("FEED SERVICE CALL");
        ExecutorService executorService = Executors.newCachedThreadPool();
        marketService.setStartDate(startDate);
        marketService.setEndDate(endDate);
        nbuService.setStartDate(startDate);
        nbuService.setEndDate(endDate);
        Future<List<Quote>> marketDataFuture = executorService.submit(marketService);
        Future<List<Quote>> nbuDataFuture = executorService.submit(nbuService);

        List<Quote> nbuQuotes = null;
        List<Quote> marketQuotes = null;
        try {
            nbuQuotes = new LinkedList<>(nbuDataFuture.get());
            marketQuotes = new LinkedList<>(marketDataFuture.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        List<Date> period = DateUtils.getPeriod(DateUtils.roundDate(startDate), DateUtils.roundDate(endDate));

        return getDiff(period, nbuQuotes, marketQuotes);

    }

    public List<Quote> getMarketQuotes(Date startDate, Date endDate) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        marketService.setStartDate(startDate);
        marketService.setEndDate(endDate);
        Future<List<Quote>> marketDataFuture = executorService.submit(marketService);
        List<Quote> quoteList = null;
        try {
            quoteList = marketDataFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return quoteList;
    }

    public List<Quote> getNbuQuotes(Date startDate, Date endDate) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        nbuService.setStartDate(startDate);
        nbuService.setEndDate(endDate);
        Future<List<Quote>> marketDataFuture = executorService.submit(nbuService);
        List<Quote> quoteList = null;
        try {
            quoteList = marketDataFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return quoteList;
    }

    private List<Quote> getDiff(List<Date> datesPeriod, List<Quote> nbuQuotes, List<Quote> marketQuotes) {
        Map<Date, Quote> nbuQuoteMap = new HashMap<>();
        for (Quote quote : nbuQuotes) {
            nbuQuoteMap.put(quote.getDate(), quote);
        }
        HashMap<Date, Quote> marketQuoteMap = new HashMap<>();
        for (Quote quote : marketQuotes) {
            marketQuoteMap.put(quote.getDate(), quote);
        }

        List<Quote> diffQuoteList = new LinkedList<>();
        for (Date date : datesPeriod) {
            Quote nbuQuote = nbuQuoteMap.get(date);
            Quote marketQuote = marketQuoteMap.get(date);
            if (nbuQuote == null || marketQuote == null) {
                continue;
            }
            Quote diffQuote = new Quote();
            diffQuote.setDate(date);
            diffQuote.setBid(marketQuote.getBid().subtract(nbuQuote.getBid()));
            diffQuote.setAsk(marketQuote.getAsk().subtract(nbuQuote.getAsk()));
            diffQuote.setAverage(marketQuote.getAverage().subtract(nbuQuote.getAverage()));
            diffQuoteList.add(diffQuote);
        }

        return diffQuoteList;
    }
}
