package com.icd.wksh.controllers;

import com.icd.wksh.commons.Constant;
import com.icd.wksh.models.User;
import com.icd.wksh.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @GetMapping
    private ResponseEntity getUsersPaging(HttpServletRequest request,
                                          @RequestParam("pageSize") int pageSize ,
                                          @RequestParam("pageIndex") int pageIndex){
        String username = (String) request.getAttribute(Constant.USERNAME);
        log.debug("controller: getUsersPaging: username={}, pageIndex={}, pageSize={}",username,pageIndex,pageSize);
        return ResponseEntity.ok(userService.getUsersResponse(pageIndex, pageSize, username));
    }

}
