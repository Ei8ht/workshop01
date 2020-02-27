package com.icd.wksh.services;

import com.icd.wksh.commons.Util;
import com.icd.wksh.daos.UserDao;
import com.icd.wksh.models.RolePermission;
import com.icd.wksh.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    private static final Logger log = LoggerFactory.getLogger(JwtUserDetailsService.class);
    @Autowired
    private UserDao userDao;;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.getUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        List<RolePermission> permissions = userDao.getPermissionByRoleId(user.getRoleId());
        if(!Util.isEmpty(permissions)){
            log.debug("permissions: {}",permissions);
            authorities = permissions.stream().map(item -> new SimpleGrantedAuthority(item.getPermissionDescription())).collect(Collectors.toList());
        }
        return new org.springframework.security.core.userdetails.User(user.getUserId(), user.getPassword(), authorities);
    }
}
