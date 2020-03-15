package com.icd.wksh.models;

import java.math.BigDecimal;

public class Book {
    private Long bookId;//`book`.`book_id`,
    private String isbn;//`book`.`isbn`,
    private String title;//`book`.`title`,
    private String author;//`book`.`author`,
    private String year;//`book`.`year`,
    private Long categoryId;//`book`.`category_id`
    private String categoryDescription;//category_description
    private String imageId;
    private String pdfId;

    @Override
    public String toString() {
        return "Book{" +
                "bookId=" + bookId +
                ", isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", year='" + year + '\'' +
                ", categoryDescription='" + categoryDescription + '\'' +
                ", categoryId=" + categoryId +
                '}';
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }

    public Long getBookId() {
        return bookId;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getPdfId() {
        return pdfId;
    }

    public void setPdfId(String pdfId) {
        this.pdfId = pdfId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }
}
