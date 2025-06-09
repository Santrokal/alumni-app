package com.shc.alumni.springboot.controller;

import com.shc.alumni.springboot.entity.News;
import com.shc.alumni.springboot.service.NewsService;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

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
    @Autowired
    private ServletContext servletContext;


    @PostMapping("/addnews")
    public ResponseEntity<Object> addNews(
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam("category") String category,
            @RequestParam(value = "mediaFiles", required = false) MultipartFile[] mediaFiles,
            HttpServletRequest request) {

        System.out.println("Received title: " + title);
        System.out.println("Received content: " + content);
        System.out.println("Received category: " + category);
        System.out.println("Received files: " + (mediaFiles != null ? mediaFiles.length : "0"));

        try {
            News news = new News();
            news.setTitle(title);
            news.setContent(content);
            news.setCategory(category);
            news.setCreatedAt(LocalDateTime.now());

            List<String> mediaPaths = new ArrayList<>();

            // Get dynamic upload path
            ServletContext servletContext = request.getServletContext();
            String appRoot = servletContext.getRealPath("/");
            if (appRoot == null) {
                appRoot = System.getProperty("user.dir") + "/webapp/";
            }

            Path uploadPath = Paths.get(appRoot, "news_folder");

            // Create folder if it doesn't exist
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
                System.out.println("Created upload directory: " + uploadPath);
            }

            // Save media files if present
            if (mediaFiles != null && mediaFiles.length > 0) {
                if (mediaFiles.length > 5) {
                    throw new IllegalArgumentException("Maximum 5 files allowed");
                }

                for (MultipartFile file : mediaFiles) {
                    if (!file.isEmpty()) {
                        String originalFileName = file.getOriginalFilename();
                        String filename = System.currentTimeMillis() + "_" + originalFileName;
                        Path filePath = uploadPath.resolve(filename);
                        file.transferTo(filePath);
                        mediaPaths.add(filename); // Store only filename
                        System.out.println("Saved media file: " + filePath);
                    }
                }
            }

            news.setMediaPaths(mediaPaths);
            newsService.saveNews(news); // Save News entity to DB

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