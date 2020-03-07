package com.icd.wksh.controllers;

import com.icd.wksh.commons.Constant;
import com.icd.wksh.commons.Response;
import com.icd.wksh.daos.UserDao;
import com.icd.wksh.models.User;
import com.icd.wksh.payloads.LoginRequest;
import com.icd.wksh.payloads.LoginResponse;
import com.icd.wksh.securities.JwtTokenUtil;
import com.icd.wksh.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.MessageFormat;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/signin")
    public ResponseEntity signIn(@RequestBody LoginRequest loginRequest) {
        log.debug("controller: signIn: {}",loginRequest);
        boolean authSuccess = authenticate(loginRequest.getUsername(), loginRequest.getPassword());
        LoginResponse response = null;
        if(authSuccess){
            response = new LoginResponse(jwtTokenUtil.generateToken(loginRequest.getUsername()));
            return ResponseEntity.ok(Response.success(response));
        } else {
            return ResponseEntity.ok(Response.fail("invalid username or password"));
        }
    }

    @PostMapping("/register")
    @PreAuthorize("hasAuthority('ADMIN:WRITE')")
    public ResponseEntity register(HttpServletRequest servletRequest, @RequestBody User requestBody) {
        log.debug("controller: register: {}",requestBody);
        String username = (String) servletRequest.getAttribute(Constant.USERNAME);
        log.debug("username={}",username);
        int rowEffected = userService.insertUser(requestBody, username);
        return ResponseEntity.ok(Response.success(MessageFormat.format("register: row {0} effected",rowEffected)));
    }

    private boolean authenticate(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
//            throw new Exception("INVALID_CREDENTIALS", e);
            log.error("USER_DISABLED");
            return false;
        } catch (BadCredentialsException e) {
//            throw new Exception("INVALID_CREDENTIALS", e);
            log.error("INVALID_CREDENTIALS");
            return false;
        }
        log.debug("username: {} authenticate passed !",username);
        return true;
    }

    private boolean match(String nonHash, String hashPassword){
        return passwordEncoder.matches(nonHash, hashPassword);
    }
}
