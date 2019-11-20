package com.fatihduygu.mobirollerproject.model;

import java.io.Serializable;

public class Product implements Serializable {
    private String id;
    private String categoryId;
    private String caption;
    private String description;
    private String price;
    private String imageUrl;

    public Product(String id, String categoryId, String caption, String description, String price, String imageUrl) {
        this.id = id;
        this.categoryId = categoryId;
        this.caption = caption;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public String getCaption() {
        return caption;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
