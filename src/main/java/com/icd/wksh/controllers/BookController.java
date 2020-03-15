package com.icd.wksh.controllers;

import com.icd.wksh.commons.Constant;
import com.icd.wksh.exceptions.BadRequestException;
import com.icd.wksh.models.Book;
import com.icd.wksh.models.Category;
import com.icd.wksh.payloads.BookRequest;
import com.icd.wksh.payloads.GetBooksResponse;
import com.icd.wksh.payloads.Message;
import com.icd.wksh.services.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/books")
public class BookController {
    private static final Logger log = LoggerFactory.getLogger(BookController.class);

    @Value("${app.books.pdf_path}")
    private String pdfPath;

    @Value("${app.books.image_path}")
    private String imagePath;

    @Value("${app.books.tmp.pdf_path}")
    private String pdfTmpPath;

    @Value("${app.books.tmp.image_path}")
    private String imageTmpPath;

    @Autowired
    private BookService bookService;

    @GetMapping("/categories")
    @PreAuthorize("hasAuthority('BOOK:READ')")
    public ResponseEntity getCategories(HttpServletRequest request){
        log.debug("controller: getCategories:");
        String message = (String) request.getAttribute(Constant.USERNAME);
        log.debug("message={}",message);
        Optional<List<Category>> categories = bookService.getCategories();
        log.debug("categories: {}",categories);
        if(categories.isPresent()){
            return ResponseEntity.ok(categories.get());
        } else {
            return ResponseEntity.ok(new ArrayList<>());
        }
    }

    @GetMapping
    @PreAuthorize("hasAuthority('BOOK:READ')")
    public ResponseEntity getBooks(HttpServletRequest request,
                                   @RequestParam("pageSize") int pageSize ,
                                   @RequestParam("pageIndex") int pageIndex){
        log.debug("controller: getBooks");
        GetBooksResponse response = bookService.getBooksResponse(pageIndex, pageSize);
        log.debug("response: {}",response);
        if(response != null){
            return ResponseEntity.ok(response);
        } else {
            response = new GetBooksResponse();
            response.setBooks(new ArrayList<>());
            response.setTotalRecord(0);
            return ResponseEntity.ok(response);
        }
    }

    @GetMapping("/recommend")
//    @PreAuthorize("hasAuthority('BOOK:READ')")
    public ResponseEntity getBooksRecommend(){
        log.debug("controller: getBooksRecommend");
        Optional<List<Book>> optionalBookList = bookService.getBooksRecommend();
        log.debug("optionalBookList: {}",optionalBookList);
        if(optionalBookList.isPresent()){
            return ResponseEntity.ok(optionalBookList.get());
        } else {
            return ResponseEntity.ok(new ArrayList<>());
        }
    }

    @GetMapping("/{bookId}")
    @PreAuthorize("hasAuthority('BOOK:READ')")
    public ResponseEntity getBooksById(@PathVariable String bookId){
        log.debug("controller: getBooksById:");
        Long bookIdVal = null;
        try {
            bookIdVal = new Long(bookId);
        } catch (Exception e){
            throw new BadRequestException("Invalid path_variable input");
        }
        Optional<Book> books = bookService.getBooksById(bookIdVal);
        log.debug("books: {}",books);

        if(books.isPresent()){
            return ResponseEntity.ok(books.get());
        } else {
            return ResponseEntity.ok(new ArrayList<>());
        }
    }

    @PostMapping
    @PreAuthorize("hasAuthority('BOOK:WRITE')")
    public ResponseEntity insertBook(@RequestBody BookRequest body){
        log.debug("controller: insertBook: body={}",body);
        int rowEffected = bookService.insertBook(body);
        if(rowEffected > 0){
            return ResponseEntity.ok(new Message(rowEffected+" row effected"));
        } else {
            return ResponseEntity.ok(new Message("0 row effected"));
        }
    }

    @PutMapping("/{bookId}")
    @PreAuthorize("hasAuthority('BOOK:WRITE')")
    public ResponseEntity updateBook(@PathVariable String bookId, @RequestBody BookRequest body) {
        log.debug("controller: updateBook: body={}, bookId={}",body,bookId);
        BigDecimal bookIdVal = null;
        try {
            bookIdVal = new BigDecimal(bookId);
        } catch (Exception e){
            throw new BadRequestException("Invalid path_variable input");
        }

        int rowEffected = bookService.updateBook(body, bookIdVal);
        if(rowEffected > 0){
            return ResponseEntity.ok(new Message(rowEffected+" row effected"));
        } else {
            return ResponseEntity.ok(new Message("0 row effected"));
        }
    }

    @DeleteMapping("/{bookId}")
    @PreAuthorize("hasAuthority('BOOK:WRITE')")
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
            return ResponseEntity.ok(new Message(rowEffected+" row effected"));
        } else {
            return ResponseEntity.ok(new Message("0 row effected"));
        }
    }

    @GetMapping("/image")
    @PreAuthorize("hasAnyAuthority('BOOK:READ')")
    public ResponseEntity getBookImage(HttpServletRequest request, @RequestParam("bookId") Long bookId) throws IOException {
        log.debug("controller: getBookImage");
        String username = (String) request.getAttribute(Constant.USERNAME);

        Optional<Book> books = bookService.getBooksById(bookId);
        String imageSource = imagePath+"\\"+books.get().getImageId();
        log.debug("imageSource={}",imageSource);
        Path path = Paths.get(imageSource);
        byte[] dataByte = Files.readAllBytes(path);
        HttpHeaders header = new HttpHeaders();
        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");

        return ResponseEntity.ok()
                .headers(header)
                .contentLength(dataByte.length)
                .contentType(MediaType.IMAGE_PNG)
                .body(dataByte);
    }

    @GetMapping("/pdf")
    @PreAuthorize("hasAnyAuthority('BOOK:READ')")
    public ResponseEntity getBookPdf(HttpServletRequest request,
                                     @RequestParam("bookId") Long bookId) throws IOException {
        log.debug("controller: getBookPdf");
        String username = (String) request.getAttribute(Constant.USERNAME);

        Optional<Book> books = bookService.getBooksById(bookId);
        String imageSource = pdfPath+"\\"+books.get().getPdfId();
        log.debug("imageSource={}",imageSource);
        Path path = Paths.get(imageSource);
        byte[] dataByte = Files.readAllBytes(path);
        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+books.get().getIsbn()+".pdf");
        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");

        return ResponseEntity.ok()
                .headers(header)
                .contentLength(dataByte.length)
                .contentType(MediaType.APPLICATION_PDF)
                .body(dataByte);
    }

    @PostMapping("/pdf/upload")
    @PreAuthorize("hasAnyAuthority('BOOK:WRITE')")
    public ResponseEntity getBookPdfUpload(HttpServletRequest request,@RequestParam("file") MultipartFile file) throws IOException {
        log.debug("controller: getBookPdfUpload");
        String username = (String) request.getAttribute(Constant.USERNAME);
        UUID uudid = UUID.randomUUID();
        File convFile = new File(pdfTmpPath+"/"+uudid.toString());
        file.transferTo(convFile);
        return ResponseEntity.ok(new Message(uudid.toString()));
    }

    @PostMapping("/image/upload")
    @PreAuthorize("hasAnyAuthority('BOOK:WRITE')")
    public ResponseEntity getBookImageUpload(HttpServletRequest request,@RequestParam("file") MultipartFile file) throws IOException {
        log.debug("controller: getBookImageUpload");
        String username = (String) request.getAttribute(Constant.USERNAME);
        UUID uudid = UUID.randomUUID();
        File convFile = new File(imageTmpPath+"/"+uudid.toString());
        file.transferTo(convFile);
        return ResponseEntity.ok(new Message(uudid.toString()));
    }
}
