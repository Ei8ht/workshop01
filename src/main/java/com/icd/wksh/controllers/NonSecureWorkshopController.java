package com.icd.wksh.controllers;

import com.icd.wksh.commons.Response;
import com.icd.wksh.configs.Printer;
import com.icd.wksh.models.WorkshopA;
import com.icd.wksh.services.WorkshopTransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/not-secure/workshop")
public class NonSecureWorkshopController {
    private static final Logger log = LoggerFactory.getLogger(NonSecureWorkshopController.class);
    @Autowired
    private WorkshopTransactionService workshopService;
    @Autowired
    @Qualifier("Brother1")
    private Printer printer;


    @GetMapping
    public ResponseEntity getWorkshopAList(@RequestParam(value = "id", required = false) BigDecimal id){
        printer.print();
        Optional<List<WorkshopA>> workshopAList = workshopService.getWorkshopAList(id);
        if(workshopAList.isPresent()){
            return ResponseEntity.ok(Response.success(workshopAList.get()));
        } else {
            return ResponseEntity.ok(Response.fail("Not found anything"));
        }
    }
    @PostMapping
    public ResponseEntity insertBook(@RequestBody WorkshopA body) throws RuntimeException {
        log.debug("controller: insertBook: body={}",body);
        int rowEffected = workshopService.insert(body);
        if(rowEffected > 0){
            return ResponseEntity.ok(Response.success(rowEffected+" row effected"));
        } else {
            return ResponseEntity.ok(Response.fail("0 row effected"));
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity updateWorkshop(@PathVariable("id") BigDecimal id,@RequestBody WorkshopA body){
        log.debug("controller: updateWorkshop: body={}",body);
        int rowEffected = workshopService.updateWorkshop(body, id);
        if(rowEffected > 0){
            return ResponseEntity.ok(Response.success(rowEffected+" row effected"));
        } else {
            return ResponseEntity.ok(Response.fail("0 row effected"));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteWorkshop(@PathVariable("id") BigDecimal id){
        log.debug("controller: updateWorkshop: id={}",id);
        int rowEffected = workshopService.deleteWorkshop(id);
        if(rowEffected > 0){
            return ResponseEntity.ok(Response.success(rowEffected+" row effected"));
        } else {
            return ResponseEntity.ok(Response.fail("0 row effected"));
        }
    }
}
