package com.home.feedo.service;

import com.home.feedo.model.AuditEntry;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class AuditService {
    private List<AuditEntry> auditEntryList = new ArrayList<>();

    public void addRecord(AuditEntry auditEntry){
        auditEntryList.add(auditEntry);
    }

    public List<AuditEntry> getData(){
        return auditEntryList;
    }

}
