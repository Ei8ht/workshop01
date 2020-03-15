package com.icd.wksh.payloads;

import java.math.BigDecimal;

public class BookRequest {
    private String isbn;//`book`.`isbn`,
    private String title;//`book`.`title`,
    private String author;//`book`.`author`,
    private String year;//`book`.`year`,
    private Long categoryId;//`book`.`category_id`
    private String imageId;
    private String pdfId;

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

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public String toString() {
        return "BookRequest{" +
                "isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", year='" + year + '\'' +
                ", categoryId=" + categoryId +
                ", imageId='" + imageId + '\'' +
                ", pdfId='" + pdfId + '\'' +
                '}';
    }
}
