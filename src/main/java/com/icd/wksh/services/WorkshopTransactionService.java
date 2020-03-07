package com.icd.wksh.services;

import com.icd.wksh.models.WorkshopA;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Service
public class WorkshopTransactionService {
    private static final Logger log = LoggerFactory.getLogger(WorkshopTransactionService.class);
    @Autowired
    private WorkshopService workshopService;

    @Transactional( value = "msTransaction", propagation = Propagation.REQUIRED)
    public int insert(WorkshopA object){
        log.debug("start transaction name={}", TransactionSynchronizationManager.getCurrentTransactionName());
        int row = 0;
        row += workshopService.insertWorkshopA(object);
        row += workshopService.insertWorkshopB(object);
        return row;
    }
}
