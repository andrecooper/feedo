package com.home.feedo.controller;

import com.home.feedo.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by andrew on 13.02.16.
 */
@Controller
public class LoginController {

    @RequestMapping(value = "/loginme", method = RequestMethod.GET)
    public ModelAndView showForm() {
        return new ModelAndView("/loginme", "user", new User());
    }

}
