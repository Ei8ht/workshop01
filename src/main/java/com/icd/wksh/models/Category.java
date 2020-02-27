package com.icd.wksh.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Category {
    private BigDecimal categoryId;//`category`.`category_id`,
    private String categoryDescription;//`category`.`category_description`

    @Override
    public String toString() {
        return "Category{" +
                "categoryId=" + categoryId +
                ", categoryDescription=" + categoryDescription +
                '}';
    }

    public BigDecimal getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(BigDecimal categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }

    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }
}
