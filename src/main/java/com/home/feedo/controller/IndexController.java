package com.home.feedo.controller;

import com.home.feedo.model.Quote;
import com.home.feedo.service.FeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import java.util.List;

/**
 * Created by andrew on 07.02.16.
 */

@Controller
@RequestMapping(value = "/feedo")
public class IndexController {

    @Autowired
    private FeedService feedService;


    @RequestMapping(value = "/")
    public ModelAndView getMainPage(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("main");
        return mv;
    }

    @RequestMapping(value = "/diff", produces = "application/json")
    @ResponseBody
    public List<Quote> getQuotesDiff(){
        return feedService.getData();
    }

    @RequestMapping(value = "/helloworld", produces = "application/json")
    @ResponseBody
    public String getGreeting(){
        return "Helloworld";
    }

}
