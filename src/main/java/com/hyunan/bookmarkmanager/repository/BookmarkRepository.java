package com.hyunan.bookmarkmanager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hyunan.bookmarkmanager.entity.Bookmark;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    List<Bookmark> findByUserId(Long user_id);
    List<Bookmark> findByUserIdAndTitleContainingIgnoreCase(Long userId, String title);
}
