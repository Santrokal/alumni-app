package com.shc.alumni.springboot.controller;

import com.shc.alumni.springboot.entity.News;
import com.shc.alumni.springboot.service.NewsService;

import java.io.IOException;
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

        try {
            newsService.saveNews(title, content, category, mediaFiles, request);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("{\"message\": \"News added successfully!\"}");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\": \"" + e.getMessage() + "\"}");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"Error saving news: " + e.getMessage() + "\"}");
        }
    }





    @GetMapping("/addnews")
    public String showAddNewsPage() {
        return "addnewsalumni";
    }
}