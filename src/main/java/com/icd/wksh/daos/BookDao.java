package com.icd.wksh.daos;

import com.icd.wksh.commons.Util;
import com.icd.wksh.models.Book;
import com.icd.wksh.models.Category;
import com.icd.wksh.payloads.BookRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
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
public class BookDao {
    private static final Logger log = LoggerFactory.getLogger(BookDao.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    private SimpleJdbcCall simpleJdbcCall;

    public List<Category> getCategories() {
        log.debug("dao: getCategories");
        StringBuilder statement = new StringBuilder();
        List<Category> resultList = null;
        List<Object> param = new ArrayList<>();
        statement.append(" SELECT `category`.`category_id`, ");
        statement.append("     `category`.`category_description` ");
        statement.append(" FROM `icd-workshop-01-db`.`category` ");

        log.debug("statement: {}", statement.toString());
        resultList = jdbcTemplate.query(statement.toString(), param.toArray(), new BeanPropertyRowMapper<>(Category.class));
        log.debug("resultList size: " + String.valueOf(resultList != null ? resultList.size() : 0));
        return resultList;
    }

    public List<Book> getBooks(BigDecimal bookId) {
        log.debug("dao: getBooks: bookId={}",bookId);
        StringBuilder statement = new StringBuilder();
        List<Book> resultList = null;
        List<Object> param = new ArrayList<>();
        statement.append(" SELECT `book`.`book_id`, ");
        statement.append("     `book`.`isbn`, ");
        statement.append("     `book`.`title`, ");
        statement.append("     `book`.`author`, ");
        statement.append("     `book`.`year`, ");
        statement.append("     `book`.`category_id`, ");
        statement.append("     `category`.`category_description` ");
        statement.append(" FROM `icd-workshop-01-db`.`book` ");
        statement.append(" LEFT JOIN `icd-workshop-01-db`.`category` ON `book`.`category_id` = `category`.`category_id` ");
        statement.append(" WHERE 1 = 1 ");
        if(Util.isNotEmpty(bookId)){
            statement.append(" AND `book`.`book_id` = ? ");
            param.add(bookId);
        }


        log.debug("statement: {}", statement.toString());
        resultList = jdbcTemplate.query(statement.toString(), param.toArray(), new BeanPropertyRowMapper<>(Book.class));
        log.debug("resultList size: " + String.valueOf(resultList != null ? resultList.size() : 0));
        return resultList;
    }

    public List<Book> getBooksProcedure() {
        log.debug("dao: getBooksProcedure");
        SimpleJdbcCall simpleJdbcCall = this.simpleJdbcCall.withProcedureName("getAllBook").returningResultSet("result", new RowMapper<Book>() {
            @Override
            public Book mapRow(ResultSet rs, int i) throws SQLException {
                Book book = new Book();
                book.setBookId(rs.getBigDecimal("book_id"));//private BigDecimal bookId;//`book`.`book_id`,
                book.setIsbn(rs.getString("isbn"));//private String isbn;//`book`.`isbn`,
                book.setTitle(rs.getString("title"));//private String title;//`book`.`title`,
                book.setAuthor(rs.getString("author"));//private String author;//`book`.`author`,
                book.setYear(rs.getString("year"));//private String year;//`book`.`year`,
                book.setCategoryId(rs.getBigDecimal("category_id"));//private BigDecimal categoryId;//`book`.`category_id`
//                book.setCategoryDescription(rs.getString("category_description"));//private String categoryDescription;//category_description
                return book;
            }
        });
//        SqlParameterSource in = new MapSqlParameterSource().addValue("book_id", null);
//        Map<String, Object> result = simpleJdbcCall.execute(in);
        Map<String, Object> result = simpleJdbcCall.execute();
        log.debug("result={}", result.get("result"));
        return (List<Book>) result.get("result");
    }

    @Transactional
    public List<Book> getBooksNamed(BigDecimal bookId) {
        log.debug("dao: getBooks: bookId={}",bookId);
        StringBuilder statement = new StringBuilder();
        List<Book> resultList = null;
        Map<String,Object> parameters = new HashMap();
        statement.append(" SELECT `book`.`book_id`, ");
        statement.append("     `book`.`isbn`, ");
        statement.append("     `book`.`title`, ");
        statement.append("     `book`.`author`, ");
        statement.append("     `book`.`year`, ");
        statement.append("     `book`.`category_id`, ");
        statement.append("     `category`.`category_description` ");
        statement.append(" FROM `icd-workshop-01-db`.`book` ");
        statement.append(" LEFT JOIN `icd-workshop-01-db`.`category` ON `book`.`category_id` = `category`.`category_id` ");
        statement.append(" WHERE 1 = 1 ");
        if(Util.isNotEmpty(bookId)){
            statement.append(" AND `book`.`book_id` = :bookId ");
            parameters.put("bookId",bookId);
        }


        log.debug("statement: {}", statement.toString());
        resultList = namedParameterJdbcTemplate.query(statement.toString(), parameters, new RowMapper<Book>() {
            @Override
            public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
                Book book = new Book();
                book.setBookId(rs.getBigDecimal("book_id"));//private BigDecimal bookId;//`book`.`book_id`,
                book.setIsbn(rs.getString("isbn"));//private String isbn;//`book`.`isbn`,
                book.setTitle(rs.getString("title"));//private String title;//`book`.`title`,
                book.setAuthor(rs.getString("author"));//private String author;//`book`.`author`,
                book.setYear(rs.getString("year"));//private String year;//`book`.`year`,
                book.setCategoryId(rs.getBigDecimal("category_id"));//private BigDecimal categoryId;//`book`.`category_id`
                book.setCategoryDescription(rs.getString("category_description"));//private String categoryDescription;//category_description
                return book;
            }
        });
        log.debug("resultList size: " + String.valueOf(resultList != null ? resultList.size() : 0));
        return resultList;
    }

    public int insertBook(BookRequest request){
        log.debug("call: insertBook: request={}",request);
        StringBuilder statement = new StringBuilder();
        List<Object> param = new ArrayList<>();
        int rows = 0;
        statement.append(" INSERT INTO `icd-workshop-01-db`.`book` ");
        statement.append(" (`isbn`, ");
        statement.append(" `title`, ");
        statement.append(" `author`, ");
        statement.append(" `year`, ");
        statement.append(" `category_id`) ");
        statement.append(" VALUES ");
        statement.append(" (?, ");
        param.add(request.getIsbn());
        statement.append(" ?, ");
        param.add(request.getTitle());
        statement.append(" ?, ");
        param.add(request.getAuthor());
        statement.append(" ?, ");
        param.add(request.getYear());
        statement.append(" ?) ");
        param.add(request.getCategoryId());

        log.debug("statement: {}",statement.toString());
        rows = jdbcTemplate.update(statement.toString(), param.toArray());
        log.debug("rows: " + rows);
        return rows;
    }

    public int updateBook(BookRequest request, BigDecimal bookId){
        log.debug("call: updateBook: request={}, bookId={}",request,bookId);
        StringBuilder statement = new StringBuilder();
        List<Object> param = new ArrayList<>();
        int rows = 0;
        statement.append(" UPDATE `icd-workshop-01-db`.`book` ");
        statement.append(" SET ");
        statement.append(" `isbn` = ? , ");
        param.add(request.getIsbn());
        statement.append(" `title` = ? , ");
        param.add(request.getTitle());
        statement.append(" `author` = ? , ");
        param.add(request.getAuthor());
        statement.append(" `year` = ? , ");
        param.add(request.getYear());
        statement.append(" `category_id` = ? ");
        param.add(request.getCategoryId());
        statement.append(" WHERE `book_id` = ? ");
        param.add(bookId);

        log.debug("statement: {}",statement.toString());
        rows = jdbcTemplate.update(statement.toString(), param.toArray());
        log.debug("rows: " + rows);
        return rows;
    }

    public int deleteBook(BigDecimal bookId){
        log.debug("call: deleteBook: bookId={}",bookId);
        StringBuilder statement = new StringBuilder();
        List<Object> param = new ArrayList<>();
        int rows = 0;
        statement.append(" DELETE FROM `icd-workshop-01-db`.`book` ");
        statement.append(" WHERE `book`.`book_id` = ? ");
        param.add(bookId);

        log.debug("statement: {}",statement.toString());
        rows = jdbcTemplate.update(statement.toString(), param.toArray());
        log.debug("rows: " + rows);
        return rows;
    }

}
