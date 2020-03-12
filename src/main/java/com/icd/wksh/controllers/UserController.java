package com.icd.wksh.controllers;

import com.icd.wksh.commons.Constant;
import com.icd.wksh.models.User;
import com.icd.wksh.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.MessageFormat;
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

    @DeleteMapping("/{userId}")
    private ResponseEntity deleteUser(HttpServletRequest request, @PathVariable String userId){
        String username = (String) request.getAttribute(Constant.USERNAME);
        log.debug("controller: deleteUser: username={}, userId={}",username, userId);
        int rowEffect = userService.deleteUser(userId);
        return ResponseEntity.ok(MessageFormat.format("delete {0} row", rowEffect));
    }

    @PostMapping
    public ResponseEntity insertUser(HttpServletRequest request, @RequestBody User user){
        log.debug("controller: insertUser={}",user);
        String username = (String) request.getAttribute(Constant.USERNAME);
        int rowEffect = userService.insertUser(user, username);
        return ResponseEntity.ok(MessageFormat.format("insert {0} row", rowEffect));
    }

    @PutMapping
    public ResponseEntity updateUser(HttpServletRequest request, @RequestBody User user){
        log.debug("controller: updateUser={}",user);
        String username = (String) request.getAttribute(Constant.USERNAME);
        int rowEffect = userService.updateUser(user, username);
        return ResponseEntity.ok(MessageFormat.format("update {0} row", rowEffect));
    }

}
