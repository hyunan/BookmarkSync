package com.hyunan.bookmarkmanager.controller;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/bookmarks")
public class BookmarkController {
    private final BookmarkRepository bookmarkRepository;
    private final UserRepository userRepository;

    public BookmarkController(BookmarkRepository bookmarkRepository, UserRepository userRepository) {
        this.bookmarkRepository = bookmarkRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/view")
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

    @GetMapping("/download")
    public ResponseEntity<?> download(HttpSession session) {
        Long userId = (Long) session.getAttribute("user_id");
        if (userId == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "User not authorized"));
        List<Bookmark> bookmarks = bookmarkRepository.findByUser_Id(userId);
        
        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE NETSCAPE-Bookmark-file-1>\n");
        html.append("<META HTTP-EQUIV=\"Content-Type\" CONTENT=\"text/html; charset=UTF-8\">\n");
        html.append("<TITLE>Bookmarks</TITLE>\n");
        html.append("<H1>Bookmarks</H1>\n");
        html.append("<DL><p>\n");
        for (Bookmark b : bookmarks) {
            html.append("\t<DT><A HREF=").append("\"").append(b.getUrl()).append("\"").append(">").append(b.getTitle()).append("</A>\n");
        }
        html.append("</DL><p>\n");
        return ResponseEntity.ok()
            .contentType(MediaType.TEXT_HTML)
            .body(html.toString());
    }

    @Transactional
    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file, HttpSession session) {
        Long userId = (Long) session.getAttribute("user_id");
        if (userId == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "User not authorized"));
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        try {
            Document doc = Jsoup.parse(file.getInputStream(), "UTF-8", "");
            Elements links = doc.select("DT > A");
            bookmarkRepository.deleteByUser_Id(userId);
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
