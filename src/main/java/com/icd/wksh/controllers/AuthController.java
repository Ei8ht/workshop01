package com.icd.wksh.controllers;

import com.icd.wksh.commons.Response;
import com.icd.wksh.daos.UserDao;
import com.icd.wksh.exceptions.CustomException;
import com.icd.wksh.models.User;
import com.icd.wksh.payloads.LoginRequest;
import com.icd.wksh.payloads.LoginResponse;
import com.icd.wksh.securities.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDao userDao;

    @PostMapping("/signin")
    public ResponseEntity signIn(@RequestBody LoginRequest loginRequest) {
        log.debug("controller: signIn: {}",loginRequest);
//        if("admin".equals(loginRequest.getUsername())){
//            throw new CustomException("admin log-in");
//        }
        boolean authSuccess = authenticate(loginRequest.getUsername(), loginRequest.getPassword());
        LoginResponse response = null;
        if(authSuccess){
            response = new LoginResponse(jwtTokenUtil.generateToken(loginRequest.getUsername()));
            return ResponseEntity.ok(Response.success(response));
        } else {
            return ResponseEntity.ok(Response.fail("invalid username or password"));
        }
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
}
