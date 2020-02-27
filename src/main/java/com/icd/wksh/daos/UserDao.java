package com.icd.wksh.daos;

import com.icd.wksh.commons.Util;
import com.icd.wksh.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDao {
    private static final Logger log = LoggerFactory.getLogger(UserDao.class);
    @Autowired
    @Qualifier("msJdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    public User getUserByUsername(String username) {
        log.debug("call: getUserByUsername");
        StringBuilder statement = new StringBuilder();
        List<User> resultList = null;
        User result = null;
        List<Object> param = new ArrayList<>();
        statement.append(" SELECT `user`.`user_id`, ");
        statement.append("     `user`.`password`, ");
        statement.append("     `user`.`name`, ");
        statement.append("     `user`.`surename`, ");
        statement.append("     `user`.`role_id`, ");
        statement.append("     `user`.`active_flag`, ");
        statement.append("     `user`.`create_date`, ");
        statement.append("     `user`.`create_by`, ");
        statement.append("     `user`.`update_date`, ");
        statement.append("     `user`.`update_by` ");
        statement.append(" FROM `icd-workshop-01-db`.`user` ");
        statement.append(" WHERE user.user_id = ? ");
        param.add(username);
        statement.append(" and user.active_flag = 'A' ");

        log.debug("statement: {}", statement.toString());
        resultList = jdbcTemplate.query(statement.toString(), param.toArray(), new BeanPropertyRowMapper<>(User.class));
        log.debug("resultList size: " + String.valueOf(resultList != null ? resultList.size() : 0));
        if(!Util.isEmpty(resultList)) {
            result = resultList.get(0);
        }
        return result;
    }
}
