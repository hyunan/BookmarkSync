package com.hyunan.bookmarkmanager.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "bookmarks")
public class Bookmark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String title;
    private String url;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    protected Bookmark() {}

    public Bookmark(String title, String url, User user) {
        this.title = title;
        this.url = url;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
