package com.icd.wksh.daos;

import com.icd.wksh.commons.Util;
import com.icd.wksh.models.RolePermission;
import com.icd.wksh.models.User;
import com.icd.wksh.models.WorkshopA;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDao {
    private static final Logger log = LoggerFactory.getLogger(UserDao.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private PasswordEncoder encoder;

    public User getUserByUsername(String username) {
        log.debug("dao: getUserByUsername");
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
//        log.debug("resultList size: " + String.valueOf(resultList != null ? resultList.size() : 0));
        if(!Util.isEmpty(resultList)) {
            result = resultList.get(0);
        }
        return result;
    }

    public Integer countUsers() {
        log.debug("dao: countUsers");
        StringBuilder statement = new StringBuilder();
        Integer result = null;
        List<Object> param = new ArrayList<>();
        statement.append(" SELECT COUNT(*) FROM `icd-workshop-01-db`.user ");

        log.debug("statement: {}", statement.toString());
        result = jdbcTemplate.queryForObject(statement.toString(), param.toArray(), Integer.class);
        return result;
    }

    public List<User> getUsers(int limit,int offset) {
        log.debug("dao: getUsers: limit={}, offset={}", limit, offset);
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
        statement.append(" ORDER BY `user`.`create_date` ");
        statement.append(" LIMIT ? OFFSET ? ");
        param.add(limit);
        param.add(offset);

        log.debug("statement: {}", statement.toString());
        resultList = jdbcTemplate.query(statement.toString(), param.toArray(), new BeanPropertyRowMapper<>(User.class));
        return resultList;
    }

    public List<RolePermission> getPermissionByRoleId(BigDecimal roleId) {
        log.debug("dao: getUserByUsername");
        StringBuilder statement = new StringBuilder();
        List<RolePermission> resultList = null;
        List<Object> param = new ArrayList<>();
        statement.append(" SELECT `role_permission`.`permission_id`, ");
        statement.append("     `role_permission`.`role_id`, ");
        statement.append("     `role_permission`.`permission_description` ");
        statement.append(" FROM `icd-workshop-01-db`.`role_permission` ");
        statement.append(" WHERE `role_permission`.`role_id` = ? ");
        param.add(roleId);

        log.debug("statement: {}", statement.toString());
        resultList = jdbcTemplate.query(statement.toString(), param.toArray(), new BeanPropertyRowMapper<>(RolePermission.class));
//        log.debug("resultList size: " + String.valueOf(resultList != null ? resultList.size() : 0));
        return resultList;
    }

    public int insertUser(User object, String username){
        log.debug("dao: insertWorkshopB: object={}",object);
        StringBuilder statement = new StringBuilder();
        List<Object> param = new ArrayList<>();
        int rows = 0;
        statement.append(" INSERT INTO `icd-workshop-01-db`.`user` ");
        statement.append(" (`user_id`, ");
        statement.append(" `password`, ");
        statement.append(" `name`, ");
        statement.append(" `surename`, ");
        statement.append(" `role_id`, ");
        statement.append(" `active_flag`, ");
        statement.append(" `create_date`, ");
        statement.append(" `create_by`, ");
        statement.append(" `update_date`, ");
        statement.append(" `update_by`) ");
        statement.append(" VALUES ");
        statement.append(" ( ? , ");
        param.add(object.getUserId());
        statement.append(" ? , ");
        param.add(encoder.encode(object.getPassword()));
        statement.append(" ? , ");
        param.add(object.getName());
        statement.append(" ? , ");
        param.add(object.getSurename());
        statement.append(" ? , ");
        param.add(object.getRoleId());
        statement.append(" 'A' , ");
        statement.append(" NOW() , ");
        statement.append(" ? , ");
        param.add(username);
        statement.append(" NOW() , ");
        statement.append(" ? ); ");
        param.add(username);

        log.debug("statement: {}",statement.toString());
        rows = jdbcTemplate.update(statement.toString(), param.toArray());
        log.debug("rows: " + rows);
        return rows;
    }

    public int insertUserList(List<User> objects, String username){
        log.debug("dao: insertWorkshopB: objects={}",objects);
        StringBuilder statement = new StringBuilder();
        List<Object> param = new ArrayList<>();
        int rows = 0;
        statement.append(" INSERT INTO `icd-workshop-01-db`.`user` ");
        statement.append(" (`user_id`, ");
        statement.append(" `password`, ");
        statement.append(" `name`, ");
        statement.append(" `surename`, ");
        statement.append(" `role_id`, ");
        statement.append(" `active_flag`, ");
        statement.append(" `create_date`, ");
        statement.append(" `create_by`, ");
        statement.append(" `update_date`, ");
        statement.append(" `update_by`) ");
        statement.append(" VALUES ");
        statement.append(" ( ? , ");
        statement.append(" ? , ");
        statement.append(" ? , ");
        statement.append(" ? , ");
        statement.append(" ? , ");
        statement.append(" 'A' , ");
        statement.append(" NOW() , ");
        statement.append(" ? , ");
        statement.append(" NOW() , ");
        statement.append(" ? ); ");


        log.debug("statement: {}",statement.toString());
        int[] rowsEffected = jdbcTemplate.batchUpdate(statement.toString(), new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                User object = objects.get(i);
                int objIndex = 1;
                ps.setString(objIndex++, object.getUserId());//param.add(object.getUserId());
                ps.setString(objIndex++, encoder.encode(object.getPassword()));//param.add(encoder.encode(object.getPassword()));
                ps.setString(objIndex++, object.getName());//param.add(object.getName());
                ps.setString(objIndex++, object.getSurename());//param.add(object.getSurename());
                ps.setBigDecimal(objIndex++, object.getRoleId());//param.add(object.getRoleId());
                ps.setString(objIndex++, username);//param.add(username);
                ps.setString(objIndex++, username);//param.add(username);
            }

            @Override
            public int getBatchSize() {
                return objects.size();
            }
        });
        if(Util.isNotEmpty(rowsEffected)){
            for(int row : rowsEffected){
                rows += row;
            }
        }
        log.debug("rows: " + rows);
        return rows;
    }

}
