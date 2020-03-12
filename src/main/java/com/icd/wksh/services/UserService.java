package com.icd.wksh.services;

import com.icd.wksh.daos.UserDao;
import com.icd.wksh.models.User;
import com.icd.wksh.payloads.GetUsersResponse;
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

    public int updateUser(User user, String username){
        log.debug("service: updateUser: user={}, username={}",user,username);
        return userDao.updateUser(user,username);
    }

    public int deleteUser(String userId){
        log.debug("service: updateUser: userId={}",userId);
        return userDao.deleteUser(userId);
    }

    public GetUsersResponse getUsersResponse(int pageIndex,int pageSize, String username){
        log.debug("service: getUsersResponse: pageIndex={}, pageSize={}, username={}",pageIndex,pageSize,username);
        GetUsersResponse response = new GetUsersResponse();
        response.setTotalRecord(userDao.countUsers());
        response.setUserList(userDao.getUsers(pageSize, getOffset(pageIndex, pageSize)));
        return response;
    }

    private int getOffset(int pageIndex,int pageSize){
        return ( pageIndex-1 ) * pageSize;
    }

}
