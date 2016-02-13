package com.home.feedo.controller;

import com.home.feedo.model.Quote;
import com.home.feedo.service.FeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by andrew on 07.02.16.
 */

@RestController
@RequestMapping(value = "/quotes")
public class IndexController {

    @Autowired
    private FeedService feedService;

    @RequestMapping(value = "/diff", produces = "application/json")
    public List<Quote> getQuotesDiff(){
        return feedService.getData();
    }

    @RequestMapping(value = "/helloworld", produces = "application/json")
    public String getGreeting(){
        return "Helloworld";
    }

}
