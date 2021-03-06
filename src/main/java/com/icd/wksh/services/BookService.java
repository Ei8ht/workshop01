package com.icd.wksh.services;

import com.icd.wksh.commons.Util;
import com.icd.wksh.daos.BookDao;
import com.icd.wksh.models.Book;
import com.icd.wksh.models.Category;
import com.icd.wksh.payloads.BookRequest;
import com.icd.wksh.payloads.GetBooksResponse;
import com.icd.wksh.payloads.GetUsersResponse;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private static final Logger log = LoggerFactory.getLogger(BookService.class);
    @Autowired
    private BookDao bookDao;

    @Value("${app.books.pdf_path}")
    private String pdfPath;

    @Value("${app.books.image_path}")
    private String imagePath;

    @Value("${app.books.tmp.pdf_path}")
    private String pdfTmpPath;

    @Value("${app.books.tmp.image_path}")
    private String imageTmpPath;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

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

    public int insertBook(BookRequest request) {
        log.debug("service: insertBook: request={}",request);
        if(request.getPdfId() != null && !"".equals(request.getPdfId())){
            try {
                Path path = Paths.get(pdfTmpPath+File.separator+request.getPdfId());
                byte[] dataByte = Files.readAllBytes(path);
                String filePath = pdfPath+File.separator+request.getPdfId();
                FileUtils.writeByteArrayToFile(new File(filePath), dataByte);
            } catch (IOException e){
                log.error("error",e);
            }
        }
        if(request.getImageId() != null && !"".equals(request.getImageId())){
            try {
                Path path = Paths.get(imageTmpPath+File.separator+request.getImageId());
                byte[] dataByte = Files.readAllBytes(path);
                String filePath = imagePath+File.separator+request.getImageId();
                FileUtils.writeByteArrayToFile(new File(filePath), dataByte);
            } catch (IOException e){
                log.error("error",e);
            }
        }
        int row = bookDao.insertBook(request);
        if(row > 0){
            String message = "Insert "+request.getIsbn() + " "+ request.getTitle();
            log.info(message);
            messagingTemplate.convertAndSend("/notification/book", message);
        }
        return row;
    }

    public int updateBook(BookRequest request, BigDecimal bookId) {
        log.debug("service: updateBook: request={}, bookId={}",request,bookId);
        if(request.getPdfId() != null && !"".equals(request.getPdfId())){
            try{
                Path path = Paths.get(pdfTmpPath+File.separator+request.getPdfId());
                byte[] dataByte = Files.readAllBytes(path);
                String filePath = pdfPath+File.separator+request.getPdfId();
                FileUtils.writeByteArrayToFile(new File(filePath), dataByte);
            } catch (IOException e){
                log.error("error",e);
            }

        }
        if(request.getImageId() != null && !"".equals(request.getImageId())){
            try {
                Path path = Paths.get(imageTmpPath + File.separator + request.getImageId());
                byte[] dataByte = Files.readAllBytes(path);
                String filePath = imagePath + File.separator + request.getImageId();
                FileUtils.writeByteArrayToFile(new File(filePath), dataByte);
            } catch (IOException e){
                log.error("error",e);
            }
        }
        int row = bookDao.updateBook(request,bookId);
        if(row > 0){
            String message = "Update "+request.getIsbn() + " "+ request.getTitle();
            log.info(message);
            messagingTemplate.convertAndSend("/notification/book", message);
        }
        return row;
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
