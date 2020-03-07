package com.icd.wksh.controllers;

import com.icd.wksh.exceptions.CustomException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hello")
public class HelloController {
    private static final Logger log = LoggerFactory.getLogger(HelloController.class);

    public void test(){
        log.debug("test()");
    }

    @GetMapping
//    @PreAuthorize("hasAuthority('ADMIN:READ')")
    public ResponseEntity getMessage(@RequestParam("message") String message){
        log.debug("controller: getMessage: {}",message);
//        if(true)
//            throw new CustomException("Custom error ja");
        return ResponseEntity.ok("Hello "+message);
    }
}
