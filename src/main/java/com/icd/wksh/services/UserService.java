package com.icd.wksh.services;

import com.icd.wksh.daos.UserDao;
import com.icd.wksh.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private UserDao userDao;

    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public int insertUser(User object,String username){
        log.debug("service: insertUser: object={}, username={}",object,username);
        return userDao.insertUser(object,username);
    }

    public int insertUserList(List<User> objects, String username){
        log.debug("service: insertUserList: objects={}, username={}",objects,username);
        return userDao.insertUserList(objects,username);
    }

}
