package com.hyunan.bookmarkmanager.controller;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hyunan.bookmarkmanager.entity.Bookmark;
import com.hyunan.bookmarkmanager.entity.User;
import com.hyunan.bookmarkmanager.repository.BookmarkRepository;
import com.hyunan.bookmarkmanager.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/bookmarks")
public class BookmarkController {
    private final BookmarkRepository bookmarkRepository;
    private final UserRepository userRepository;

    public BookmarkController(BookmarkRepository bookmarkRepository, UserRepository userRepository) {
        this.bookmarkRepository = bookmarkRepository;
        this.userRepository = userRepository;
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

    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file, HttpSession session) {
        System.out.println("UPLOAD WAS RUN");
        Long userId = (Long) session.getAttribute("user_id");
        System.out.println("UserID is: " + userId);
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        try {
            Document doc = Jsoup.parse(file.getInputStream(), "UTF-8", "");
            Elements links = doc.select("DT > A");
            for (var link : links) {
                String href = link.attr("HREF");
                String title = link.text();
                if (title.isEmpty())
                    title = "unnamed title";
                bookmarkRepository.save(new Bookmark(title, href, user));
            }
            return ResponseEntity.ok(Map.of("response", "HTML file was read and parsed"));
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", "File could not be read"));
        }
    }
}
