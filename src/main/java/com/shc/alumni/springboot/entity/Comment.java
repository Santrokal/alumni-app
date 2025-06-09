package com.shc.alumni.springboot.entity;


import java.util.Date;



public class Comment {
    private int newsId;
    private String text;
    private Date createdAt;

    public Comment() {}

    // Getters and Setters
    public int getNewsId() {
        return newsId;
    }

    public void setNewsId(int newsId) {
        this.newsId = newsId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}

