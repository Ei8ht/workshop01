package com.icd.wksh.controllers;

import com.icd.wksh.commons.Response;
import com.icd.wksh.daos.UserDao;
import com.icd.wksh.models.User;
import com.icd.wksh.payloads.LoginRequest;
import com.icd.wksh.payloads.LoginResponse;
import com.icd.wksh.securities.JwtTokenProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private JwtTokenProvider jwtTokenProvider;
//    @Autowired
//    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDao userDao;

    @PostMapping("/signin")
    public ResponseEntity signIn(@RequestBody LoginRequest loginRequest){
        log.debug("controller: signIn: {}",loginRequest);
        User user = userDao.getUserByUsername(loginRequest.getUsername());
        LoginResponse response = null;
        if(user != null){
            if(jwtTokenProvider.matchPassword(loginRequest.getPassword(), user.getPassword())){
                log.debug("login: passed");
//                Authentication authentication = authenticationManager.authenticate(
//                        new UsernamePasswordAuthenticationToken(
//                                loginRequest.getUsername(),
//                                loginRequest.getPassword()
//                        )
//                );
                response = new LoginResponse(jwtTokenProvider.generateToken(loginRequest.getUsername()));
            }
        }

        if(response != null){
            return ResponseEntity.ok(Response.success(response));
        } else {
            return ResponseEntity.ok(Response.fail("invalid username or password"));
        }

    }
}
