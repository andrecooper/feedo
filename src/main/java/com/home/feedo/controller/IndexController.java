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

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

/**
 * Created by andrew on 07.02.16.
 */

@Controller
public class IndexController {

    @RequestMapping(value = "/")
    public ModelAndView getMainPage(HttpServletRequest request){

        ModelAndView mv = new ModelAndView();
        mv.setViewName("main");
        return mv;
    }

}
