package com.icd.wksh.services;

import com.icd.wksh.commons.Util;
import com.icd.wksh.daos.BookDao;
import com.icd.wksh.models.Book;
import com.icd.wksh.models.Category;
import com.icd.wksh.payloads.BookRequest;
import com.icd.wksh.payloads.GetBooksResponse;
import com.icd.wksh.payloads.GetUsersResponse;
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

    public Optional<List<Book>> getBooksRecommend(){
        log.debug("service: getBooksRecommend");
        return Util.wrap(bookDao.getBooksRecommend());
    }

    public Optional<Book> getBooksById(Long bookId){
        log.debug("service: getBooksById: bookId={}",bookId);
        List<Book> books = bookDao.getBooksNamed(bookId);
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

    public GetBooksResponse getBooksResponse(int pageIndex, int pageSize){
        log.debug("service: getBooksResponse: pageIndex={}, pageSize={}",pageIndex,pageSize);
        GetBooksResponse response = new GetBooksResponse();
        response.setTotalRecord(bookDao.countBooks());
        response.setBooks(bookDao.getBooks(pageSize, getOffset(pageIndex, pageSize)));
        return response;
    }

    private int getOffset(int pageIndex,int pageSize){
        return ( pageIndex-1 ) * pageSize;
    }

}
