package com.icd.wksh.services;

import com.icd.wksh.commons.Util;
import com.icd.wksh.daos.BookDao;
import com.icd.wksh.models.Book;
import com.icd.wksh.models.Category;
import com.icd.wksh.payloads.BookRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private static final Logger log = LoggerFactory.getLogger(BookService.class);
    @Autowired
    private BookDao bookDao;

    public Optional<List<Category>> getCategories(){
        log.debug("service: getCategories");
        return Util.wrap(bookDao.getCategories());
    }

    public Optional<List<Book>> getBooks(){
        log.debug("service: getBooks");
        return Util.wrap(bookDao.getBooks(null));
    }

    public Optional<Book> getBooksById(BigDecimal bookId){
        log.debug("service: getBooksById: bookId={}",bookId);
        List<Book> books = bookDao.getBooks(bookId);
        Book result = null;
        if(Util.isNotEmpty(books)){
            result = books.get(0);
        }
        return Util.wrap(result);
    }

    public int insertBook(BookRequest request){
        log.debug("service: insertBook: request={}",request);
        return bookDao.insertBook(request);
    }

    public int updateBook(BookRequest request, BigDecimal bookId){
        log.debug("service: updateBook: request={}, bookId={}",request,bookId);
        return bookDao.updateBook(request,bookId);
    }

    public int deleteBook(BigDecimal bookId){
        log.debug("service: deleteBook: bookId={}",bookId);
        return bookDao.deleteBook(bookId);
    }

}
