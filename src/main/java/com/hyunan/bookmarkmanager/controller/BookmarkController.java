package com.hyunan.bookmarkmanager.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hyunan.bookmarkmanager.entity.Bookmark;
import com.hyunan.bookmarkmanager.repository.BookmarkRepository;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/bookmarks")
public class BookmarkController {
    private final BookmarkRepository bookmarkRepository;

    public BookmarkController(BookmarkRepository bookmarkRepository) {
        this.bookmarkRepository = bookmarkRepository;
    }

    @GetMapping
    public List<Bookmark> getAllBookmarks(HttpSession session) {
        Long userId = (Long) session.getAttribute("user_id");
        if (userId == null) return Collections.emptyList();
        return bookmarkRepository.findByUser_Id(userId);
    }

    @GetMapping("/search")
    public List<Bookmark> searchBookmarks(@RequestParam(required = true) String query, HttpSession session) {
        Long userId = (Long) session.getAttribute("user_id");
        if (userId == null) return Collections.emptyList();
        return bookmarkRepository.findByUser_IdAndTitleContainingIgnoreCase(userId, query);
    }
}
