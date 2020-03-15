package com.icd.wksh.payloads;

import com.icd.wksh.models.Book;

import java.util.List;

public class GetBooksResponse {
    private int totalRecord;
    private List<Book> books;

    public int getTotalRecord() {
        return totalRecord;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public void setTotalRecord(int totalRecord) {
        this.totalRecord = totalRecord;
    }

}
