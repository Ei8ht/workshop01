package com.icd.wksh.services;

import com.icd.wksh.daos.WorkshopDao;
import com.icd.wksh.exceptions.BadRequestException;
import com.icd.wksh.models.WorkshopA;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class WorkshopBService {
    private static final Logger log = LoggerFactory.getLogger(WorkshopBService.class);
    @Autowired
    private WorkshopDao workshopDao;

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
    public int insertWorkshopA(WorkshopA object){
        try {
            log.debug("start transaction name={}",TransactionSynchronizationManager.getCurrentTransactionName());
        } catch (Exception e){
            log.debug("not found transaction");
        }
        log.debug("service: insertWorkshopA: object={}",object);
        int row = 0;
        row += workshopDao.insertWorkshopA(object);
        return row;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public int insertWorkshopB(WorkshopA object){
        try {
            log.debug("start transaction name={}",TransactionSynchronizationManager.getCurrentTransactionName());
        } catch (Exception e){
            log.debug("not found transaction");
        }

        log.debug("service: insertWorkshopB: object={}",object);
        int row = 0;
        row += workshopDao.insertWorkshopB(object);
//        if(true){
//            throw new BadRequestException("Bad bad");
//        }
        return row;
    }

}
