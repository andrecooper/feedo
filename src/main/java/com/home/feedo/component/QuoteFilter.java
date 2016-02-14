package com.home.feedo.component;

import com.home.feedo.model.Quote;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class QuoteFilter {

    public synchronized void filterByDate(List<Quote> quotesList, Date startDate, Date endDate) {
        List<Quote> removalList = new ArrayList<>();
        for (Quote quote : quotesList) {
            Date date = quote.getDate();
//            System.out.println("FILTER: " + date + " : " + "\n  before "+ startDate + ": " + date.before(startDate) + "    after "+endDate+": " + date.after(endDate));
            if (date.after(endDate) || date.before(startDate)){
                removalList.add(quote);
            }
        }
        quotesList.removeAll(removalList);
    }

}
