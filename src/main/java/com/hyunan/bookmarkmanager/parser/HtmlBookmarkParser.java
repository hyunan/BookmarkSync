package com.hyunan.bookmarkmanager.parser;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class HtmlBookmarkParser {
    public void parseFile() {
        try {
            File input = new File(getClass().getClassLoader().getResource("test.html").getFile());
            Document doc = Jsoup.parse(input, "UTF-8");
            Elements links = doc.select("DT > A");
            List<Map.Entry<String, String>> bookmarks = new ArrayList<>();
            
            for (var link : links) {
                String href = link.attr("HREF");
                String title = link.text();
                if (title.isEmpty())
                    title = "unnamed title";
                bookmarks.add(Map.entry(href, title));
            }
            for (var pair : bookmarks) {
                System.out.println(pair.getValue());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
