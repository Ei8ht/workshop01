package com.icd.wksh.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hello")
public class HelloController {
    private static final Logger log = LoggerFactory.getLogger(HelloController.class);
    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN:READ')")
    public ResponseEntity getMessage(@RequestParam("message") String message){
        log.debug("controller: getMessage: {}",message);
        return ResponseEntity.ok("Hello "+message);
    }
}
