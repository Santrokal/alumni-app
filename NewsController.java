package com.shc.alumni.springboot.controller;

import com.shc.alumni.springboot.entity.News;
import com.shc.alumni.springboot.service.NewsService;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class NewsController {

    @Autowired
    private NewsService newsService;

    @PostMapping("/addnews")
    public ResponseEntity<Object> addNews(
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam("category") String category,
            @RequestParam("language") String language,
            @RequestParam(value = "mediaFiles", required = false) MultipartFile[] mediaFiles) {

        System.out.println("Received title: " + title);
        System.out.println("Received content: " + content);
        System.out.println("Received category: " + category);
        System.out.println("Received files: " + (mediaFiles != null ? mediaFiles.length : "0"));

        try {
            News news = new News();
            news.setTitle(title);
            news.setContent(content);
            news.setCategory(category);
            news.setLanguage(language);
            news.setCreatedAt(LocalDateTime.now());
            List<String> mediaPaths = new ArrayList<>();
            String uploadDir = "C:\\Users\\Mohammed Salman\\alumni-app\\news_folder\\"; // Define UPLOAD_DIR explicitly

            if (mediaFiles != null && mediaFiles.length > 0) {
                if (mediaFiles.length > 5) {
                    throw new IllegalArgumentException("Maximum 5 files allowed");
                }
                for (MultipartFile file : mediaFiles) {
                    if (!file.isEmpty()) {
                        String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                        Path path = Paths.get(uploadDir + filename);
                        file.transferTo(path);
                        mediaPaths.add(filename); // Store only filename
                        System.out.println("Saved media file: " + path.toString());
                    }
                }
            }
            news.setMediaPaths(mediaPaths);
            newsService.saveNews(news); // Call the updated method with News object
            return ResponseEntity.status(HttpStatus.CREATED).body("{\"message\": \"News added successfully!\"}");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"Error saving news: " + e.getMessage() + "\"}");
        }
    }

    @GetMapping("/addnews")
    public String showAddNewsPage() {
        return "addnewsalumni";
    }
}