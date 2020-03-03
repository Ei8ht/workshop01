package com.icd.wksh.controllers;

import com.icd.wksh.commons.Constant;
import com.icd.wksh.commons.Response;
import com.icd.wksh.exceptions.BadRequestException;
import com.icd.wksh.models.Book;
import com.icd.wksh.models.Category;
import com.icd.wksh.payloads.BookRequest;
import com.icd.wksh.services.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/books")
public class BookController {
    private static final Logger log = LoggerFactory.getLogger(BookController.class);
    @Autowired
    private BookService bookService;

    @GetMapping("/categories")
    @PreAuthorize("hasAuthority('ADMIN:READ')")
    public ResponseEntity getCategories(HttpServletRequest request){
        log.debug("controller: getCategories:");
        String message = (String)request.getAttribute(Constant.MESSAGE);
        log.debug("message={}",message);
        Optional<List<Category>> categories = bookService.getCategories();
        log.debug("categories: {}",categories);
        if(categories.isPresent()){
            return ResponseEntity.ok(Response.success(categories.get()));
        } else {
            return ResponseEntity.ok(Response.fail("Not found result"));
        }
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN:READ')")
    public ResponseEntity getBooks(){
        log.debug("controller: getBooks:");
        Optional<List<Book>> books = bookService.getBooks();
        log.debug("books: {}",books);
        if(books.isPresent()){
            return ResponseEntity.ok(Response.success(books.get()));
        } else {
            return ResponseEntity.ok(Response.fail("Not found result"));
        }
    }

    @GetMapping("/{bookId}")
    @PreAuthorize("hasAuthority('ADMIN:READ')")
    public ResponseEntity getBooksById(@PathVariable String bookId){
        log.debug("controller: getBooksById:");
        BigDecimal bookIdVal = null;
        try {
            bookIdVal = new BigDecimal(bookId);
        } catch (Exception e){
            throw new BadRequestException("Invalid path_variable input");
        }
        Optional<Book> books = bookService.getBooksById(bookIdVal);
        log.debug("books: {}",books);

        if(books.isPresent()){
            return ResponseEntity.ok(Response.success(books.get()));
        } else {
            return ResponseEntity.ok(Response.fail("Not found result"));
        }
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN:WRITE')")
    public ResponseEntity insertBook(@RequestBody BookRequest body){
        log.debug("controller: insertBook: body={}",body);
        int rowEffected = bookService.insertBook(body);
        if(rowEffected > 0){
            return ResponseEntity.ok(Response.success(rowEffected+" row effected"));
        } else {
            return ResponseEntity.ok(Response.fail("0 row effected"));
        }
    }

    @PutMapping("/{bookId}")
    @PreAuthorize("hasAuthority('BOOK:WRITE')")
    public ResponseEntity updateBook(@PathVariable String bookId, @RequestBody BookRequest body){
        log.debug("controller: updateBook: body={}, bookId={}",body,bookId);
        BigDecimal bookIdVal = null;
        try {
            bookIdVal = new BigDecimal(bookId);
        } catch (Exception e){
            throw new BadRequestException("Invalid path_variable input");
        }

        int rowEffected = bookService.updateBook(body, bookIdVal);
        if(rowEffected > 0){
            return ResponseEntity.ok(Response.success(rowEffected+" row effected"));
        } else {
            return ResponseEntity.ok(Response.fail("0 row effected"));
        }
    }

    @DeleteMapping("/{bookId}")
    @PreAuthorize("hasAuthority('ADMIN:WRITE')")
    public ResponseEntity deleteBook(@PathVariable String bookId){
        log.debug("controller: deleteBook: bookId={}",bookId);
        BigDecimal bookIdVal = null;
        try {
            bookIdVal = new BigDecimal(bookId);
        } catch (Exception e){
            throw new BadRequestException("Invalid path_variable input");
        }

        int rowEffected = bookService.deleteBook(bookIdVal);
        if(rowEffected > 0){
            return ResponseEntity.ok(Response.success(rowEffected+" row effected"));
        } else {
            return ResponseEntity.ok(Response.fail("0 row effected"));
        }
    }
}
