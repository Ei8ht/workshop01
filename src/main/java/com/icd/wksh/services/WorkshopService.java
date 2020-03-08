package com.icd.wksh.services;

import com.icd.wksh.daos.WorkshopDao;
import com.icd.wksh.models.WorkshopA;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class WorkshopService {
    private static final Logger log = LoggerFactory.getLogger(WorkshopService.class);
    @Autowired
    private WorkshopDao workshopDao;

    @Transactional(propagation = Propagation.REQUIRED)
    public int insertWorkshopA(WorkshopA object){
        log.debug("start transaction name={}",TransactionSynchronizationManager.getCurrentTransactionName());
        log.debug("service: insertWorkshopA: object={}",object);
        int row = 0;
        row += workshopDao.insertWorkshopA(object);
        return row;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public int insertWorkshopA(List<WorkshopA> objects){
        log.debug("start transaction name={}",TransactionSynchronizationManager.getCurrentTransactionName());
        log.debug("service: insertWorkshopA: objects={}",objects);
        int row = 0;
        row += workshopDao.insertWorkshopA(objects);
        return row;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public int insertWorkshopB(WorkshopA object){
        log.debug("start transaction name={}",TransactionSynchronizationManager.getCurrentTransactionName());
        log.debug("service: insertWorkshopB: object={}",object);
        int row = 0;
        row += workshopDao.insertWorkshopB(object);
        return row;
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Optional<List<WorkshopA>> getWorkshopAList(BigDecimal id){
        log.debug("service: getWorkshopAList: object={}",id);
        List<WorkshopA> result = null;
        result = workshopDao.getWorkshopAList(id);
        return Optional.ofNullable(result);
    }

    public int updateWorkshop(WorkshopA object,BigDecimal id){
        log.debug("service: updateWorkshop: object={}, id={}",object, id);
        return workshopDao.updateWorkshop(object, id);
    }

    public int deleteWorkshop(BigDecimal id){
        log.debug("service: deleteWorkshop: id={}",id);
        return workshopDao.deleteWorkshop(id);
    }
}
