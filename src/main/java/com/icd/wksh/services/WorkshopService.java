package com.icd.wksh.services;

import com.icd.wksh.daos.WorkshopDao;
import com.icd.wksh.models.WorkshopA;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class WorkshopService {
    private static final Logger log = LoggerFactory.getLogger(WorkshopService.class);
    @Autowired
    private WorkshopBService workshopBService;
    @Autowired
    private WorkshopDao workshopDao;



    @Transactional( value = "msTransaction", propagation = Propagation.REQUIRED)
    public int insert(WorkshopA object){
        try {
            log.debug("start transaction name={}", TransactionSynchronizationManager.getCurrentTransactionName());
        } catch (Exception e){
            log.debug("not found transaction");
        }
        int row = 0;
        row += workshopBService.insertWorkshopA(object);
        row += workshopBService.insertWorkshopB(object);
        return row;
    }

    @Transactional( value = "msTransaction", propagation = Propagation.REQUIRED)
    public int insertWorkshop(WorkshopA object){
        log.debug("service: insertWorkshop: object={}",object);
        int row = 0;
        row += workshopDao.insertWorkshopA(object);
        return row;
    }

    public Optional<List<WorkshopA>> getWorkshopAList(BigDecimal id){
        log.debug("service: getWorkshopAList: object={}",id);
        return Optional.ofNullable(workshopDao.getWorkshopAList(id));
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
