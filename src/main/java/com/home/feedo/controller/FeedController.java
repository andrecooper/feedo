package com.home.feedo.controller;

import com.home.feedo.model.Quote;
import com.home.feedo.model.Role;
import com.home.feedo.service.FeedService;
import com.home.feedo.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/feed")
public class FeedController {

    @Autowired
    private FeedService feedService;


    private static final String DATE_PATTERN = "MM/dd/yyyy";
    private static final String DEMO = String.valueOf(Role.DEMO);

    @RequestMapping(value = "/market", produces = "application/json", method = RequestMethod.GET)
    public List<Quote> getMarketQuotes(@RequestParam(value = "startDate") String startDate,
                                       @RequestParam(value = "endDate") String endDate,
                                       HttpServletRequest request) {

        Date start = DateUtils.getDate(startDate, DATE_PATTERN);
        Date end = DateUtils.getDate(endDate, DATE_PATTERN);

        if (request.isUserInRole(DEMO)) {
            end = new Date();
            start = DateUtils.addDay(end, -5);
        }

        return feedService.getMarketQuotes(start, end);
    }

    @RequestMapping(value = "/nbu", produces = "application/json", method = RequestMethod.GET)
    public List<Quote> getNbuQuotes(@RequestParam(value = "startDate") String startDate,
                                    @RequestParam(value = "endDate") String endDate,
                                    HttpServletRequest request) {
        Date start = DateUtils.getDate(startDate, DATE_PATTERN);
        Date end = DateUtils.getDate(endDate, DATE_PATTERN);

        if (request.isUserInRole(DEMO)) {
            end = new Date();
            start = DateUtils.addDay(end, -5);
        }
        return feedService.getNbuQuotes(start, end);
    }

    @RequestMapping(value = "/diff", produces = "application/json", method = RequestMethod.GET)
    public List<Quote> getQuotesDiff(@RequestParam(value = "startDate") String startDate,
                                     @RequestParam(value = "endDate") String endDate,
                                     HttpServletRequest request) {
        Date start = DateUtils.getDate(startDate, DATE_PATTERN);
        Date end = DateUtils.getDate(endDate, DATE_PATTERN);

        if (request.isUserInRole(DEMO)) {
            end = new Date();
            start = DateUtils.addDay(end, -5);
        }
        return feedService.getDiffData(start, end);
    }

    @RequestMapping(value = "/helloworld", produces = "application/json")
    public String getGreeting() {
        return "Helloworld";
    }
}
