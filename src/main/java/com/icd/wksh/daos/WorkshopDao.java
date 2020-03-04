package com.icd.wksh.daos;

import com.icd.wksh.commons.Util;
import com.icd.wksh.models.Book;
import com.icd.wksh.models.Category;
import com.icd.wksh.models.WorkshopA;
import com.icd.wksh.payloads.BookRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class WorkshopDao {
    private static final Logger log = LoggerFactory.getLogger(WorkshopDao.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<WorkshopA> getWorkshopAList(BigDecimal id) {
        log.debug("dao: getWorkshopAList: id={}",id);
        StringBuilder statement = new StringBuilder();
        List<WorkshopA> resultList = null;
        List<Object> param = new ArrayList<>();
        statement.append(" SELECT `workshop_a`.`id`, ");
        statement.append("     `workshop_a`.`value1`, ");
        statement.append("     `workshop_a`.`value2` ");
        statement.append(" FROM `icd-workshop-01-db`.`workshop_a` ");
        statement.append(" WHERE 1 = 1 ");
        if(Util.isNotEmpty(id)){
            statement.append(" AND `workshop_a`.`id` = ? ");
            param.add(id);
        }
        log.debug("statement: {}", statement.toString());
        resultList = jdbcTemplate.query(statement.toString(), param.toArray(), new BeanPropertyRowMapper<>(WorkshopA.class));
        log.debug("resultList size: " + String.valueOf(resultList != null ? resultList.size() : 0));
        return resultList;
    }

    public int insertWorkshopA(WorkshopA object){
        log.debug("call: insertWorkshopA: object={}",object);
        StringBuilder statement = new StringBuilder();
        List<Object> param = new ArrayList<>();
        int rows = 0;
        statement.append(" INSERT INTO `icd-workshop-01-db`.`workshop_a` ");
        statement.append(" ( ");
        statement.append(" `value1`, ");
        statement.append(" `value2`) ");
        statement.append(" VALUES ");
        statement.append(" ( ");
        statement.append(" ? , ");
        param.add(object.getValue1());
        statement.append(" ? ) ");
        param.add(object.getValue2());

        log.debug("statement: {}",statement.toString());
        rows = jdbcTemplate.update(statement.toString(), param.toArray());
        log.debug("rows: " + rows);
        return rows;
    }

    public int updateWorkshop(WorkshopA object, BigDecimal id){
        log.debug("call: updateWorkshop: object={}, id={}",object,id);
        StringBuilder statement = new StringBuilder();
        List<Object> param = new ArrayList<>();
        int rows = 0;
        statement.append(" UPDATE `icd-workshop-01-db`.`workshop_a` ");
        statement.append(" SET ");
        statement.append(" `value1` = ?, ");
        param.add(object.getValue1());
        statement.append(" `value2` = ? ");
        param.add(object.getValue2());
        statement.append(" WHERE `id` = ? ");
        param.add(id);

        log.debug("statement: {}",statement.toString());
        rows = jdbcTemplate.update(statement.toString(), param.toArray());
        log.debug("rows: " + rows);
        return rows;
    }

    public int deleteWorkshop(BigDecimal id){
        log.debug("call: deleteWorkshop: id={}",id);
        StringBuilder statement = new StringBuilder();
        List<Object> param = new ArrayList<>();
        int rows = 0;
        statement.append(" DELETE FROM `icd-workshop-01-db`.`workshop_a` ");
        statement.append(" WHERE `workshop_a`.`id` = ? ");
        param.add(id);

        log.debug("statement: {}",statement.toString());
        rows = jdbcTemplate.update(statement.toString(), param.toArray());
        log.debug("rows: " + rows);
        return rows;
    }

}
