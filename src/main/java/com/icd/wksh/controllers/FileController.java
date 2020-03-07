package com.icd.wksh.controllers;

import com.icd.wksh.commons.Constant;
import com.icd.wksh.commons.Response;
import com.icd.wksh.exceptions.BadRequestException;
import com.icd.wksh.models.Book;
import com.icd.wksh.models.Category;
import com.icd.wksh.payloads.BookRequest;
import com.icd.wksh.services.BookService;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/files")
public class FileController {
    private static final Logger log = LoggerFactory.getLogger(FileController.class);
    @Value("${app.download.resource}")
    private Resource pdfResource;

    @GetMapping("/downloads")
    @PreAuthorize("hasAnyAuthority('BOOK:READ,ADMIN:READ')")
    public ResponseEntity getDownloadFile(HttpServletRequest request) throws IOException {
        log.debug("controller: getDownloadFile");
        String username = (String) request.getAttribute(Constant.USERNAME);
        log.debug("username={}",username);

        log.debug("fileName={}",pdfResource.getFile().getAbsolutePath());

        byte[] dataByte = IOUtils.toByteArray(pdfResource.getInputStream());
        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+pdfResource.getFilename());
        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");

        return ResponseEntity.ok()
                .headers(header)
                .contentLength(dataByte.length)
                .contentType(MediaType.APPLICATION_PDF)
                .body(dataByte);
    }
}
