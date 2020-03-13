package com.icd.wksh.controllers;

import com.icd.wksh.commons.Constant;
import com.icd.wksh.exceptions.NotFoundException;
import com.icd.wksh.models.User;
import com.icd.wksh.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
//    @PreAuthorize("hasAuthority('USER:READ')")
    private ResponseEntity getUsersPaging(HttpServletRequest request,
                                          @RequestParam("pageSize") int pageSize ,
                                          @RequestParam("pageIndex") int pageIndex){
        String username = (String) request.getAttribute(Constant.USERNAME);
        log.debug("controller: getUsersPaging: username={}, pageIndex={}, pageSize={}",username,pageIndex,pageSize);
        return ResponseEntity.ok(userService.getUsersResponse(pageIndex, pageSize, username));
    }

    @GetMapping("/{userId}")
//    @PreAuthorize("hasAuthority('USER:READ')")
    private ResponseEntity getUserById(HttpServletRequest request, @PathVariable String userId){
        String username = (String) request.getAttribute(Constant.USERNAME);
        log.debug("controller: getUsersPaging: username={}, userId={}",username,userId);
        User user = userService.getUserByUsername(userId);
        if(user == null){
            throw new NotFoundException(MessageFormat.format("Not found user={0}",userId));
        }
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{userId}")
//    @PreAuthorize("hasAuthority('USER:WRITE')")
    private ResponseEntity deleteUser(HttpServletRequest request, @PathVariable String userId){
        String username = (String) request.getAttribute(Constant.USERNAME);
        log.debug("controller: deleteUser: username={}, userId={}",username, userId);
        int rowEffect = userService.deleteUser(userId);
        return ResponseEntity.ok(MessageFormat.format("delete {0} row", rowEffect));
    }

    @PostMapping
//    @PreAuthorize("hasAuthority('USER:WRITE')")
    public ResponseEntity insertUser(HttpServletRequest request, @RequestBody User user){
        log.debug("controller: insertUser={}",user);
        String username = (String) request.getAttribute(Constant.USERNAME);
        int rowEffect = userService.insertUser(user, username);
        return ResponseEntity.ok(MessageFormat.format("insert {0} row", rowEffect));
    }

    @PutMapping
//    @PreAuthorize("hasAuthority('USER:WRITE')")
    public ResponseEntity updateUser(HttpServletRequest request, @RequestBody User user){
        log.debug("controller: updateUser={}",user);
        String username = (String) request.getAttribute(Constant.USERNAME);
        int rowEffect = userService.updateUser(user, username);
        return ResponseEntity.ok(MessageFormat.format("update {0} row", rowEffect));
    }

}
