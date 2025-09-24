package com.hyunan.bookmarkmanager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hyunan.bookmarkmanager.entity.Bookmark;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    List<Bookmark> findByUser_Id(Long user_id);
    List<Bookmark> findByUser_IdAndTitleContainingIgnoreCase(Long userId, String title);
}
