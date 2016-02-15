package com.home.feedo.controller;

import com.home.feedo.model.AuditEntry;
import com.home.feedo.service.AuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
@RequestMapping(value = "/audit")
public class AuditController {

    @Autowired
    private AuditService auditService;

    @RequestMapping(value = "/")
    public ModelAndView getMainPage(HttpServletRequest request){

        ModelAndView mv = new ModelAndView();
        mv.setViewName("audit");
        return mv;
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public List<AuditEntry> getAudit(){
        return auditService.getData();
    }

}
