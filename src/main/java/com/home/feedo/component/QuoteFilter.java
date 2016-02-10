package com.home.feedo.component;

import com.home.feedo.entity.Quote;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class QuoteFilter {

    public void filterByDate(List<Quote> quotesList, Date startDate, Date endDate) {
        List<Quote> removalList = new ArrayList<>();
        for (Quote quote : quotesList) {
            Date date = quote.getDate();
            if (date.after(endDate) || date.before(startDate)){
                removalList.add(quote);
            }
        }
        quotesList.removeAll(removalList);
    }



}
