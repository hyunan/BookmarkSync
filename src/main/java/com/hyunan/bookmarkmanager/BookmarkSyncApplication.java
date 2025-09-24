package com.hyunan.bookmarkmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.hyunan.bookmarkmanager.parser.HtmlBookmarkParser;

@SpringBootApplication
public class BookmarkSyncApplication {

	public static void main(String[] args) {
		//SpringApplication.run(BookmarkSyncApplication.class, args);
		var parser = new HtmlBookmarkParser();
		parser.parseFile();
	}

}
