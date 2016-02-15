package com.home.feedo.config;

import com.home.feedo.model.AuditEntry;
import com.home.feedo.service.AuditService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Parameter;
import java.security.Principal;
import java.util.Date;

@Component
@Aspect
public class AopConfig {

    @Autowired
    private AuditService auditService;

    @Before(value = "execution(* com.home.feedo.controller.FeedController.get*(..))")
    public void logging(JoinPoint jp){
        Signature signature = jp.getSignature();
        AuditEntry auditEntry = new AuditEntry();
        auditEntry.setDate(new Date());
        if (signature instanceof MethodSignature){
            String methodName = ((MethodSignature) signature).getMethod().getName();
            auditEntry.setRequestMethod(methodName);
        }

        Object[] args = jp.getArgs();
        StringBuilder paramBuilder = new StringBuilder("");
        for (Object arg : args) {
            if (arg instanceof HttpServletRequest){
                String name = ((HttpServletRequest) arg).getUserPrincipal().getName();
                auditEntry.setUser(name);
                continue;
            }
            paramBuilder.append("\"").append(arg.toString()).append(" \" ");
            auditEntry.setParams(paramBuilder.toString());
        }
        auditService.addRecord(auditEntry);
    }
}
