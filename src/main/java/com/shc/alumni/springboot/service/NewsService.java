package com.shc.alumni.springboot.service;

import com.shc.alumni.springboot.entity.News;
import com.shc.alumni.springboot.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class NewsService {

    @Autowired
    private NewsRepository newsRepository;

    private static final String UPLOAD_DIR = "C:\\Users\\Mohammed Salman\\alumni-app\\news_folder\\";

    public NewsService() {
        File directory = new File(UPLOAD_DIR);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    public News getNewsById(int id) {
        return newsRepository.findById((long) id).orElse(null);
    }

    public List<News> findAll() {
        return newsRepository.findAll();
    }

    public List<News> findTop5ByOrderByCreatedAtDesc() {
        return newsRepository.findTop5ByOrderByCreatedAtDesc();
    }

    public void saveNews(News news) {
        System.out.println("Saving news...");
        try {
            newsRepository.save(news);
            System.out.println("News saved successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error saving news: " + e.getMessage());
        }
    }


    public void deleteById(Long id) {
        News news = newsRepository.findById(id).orElse(null);
        if (news != null) {
            List<String> mediaPaths = news.getMediaPaths();  // This should work with the getter above
            if (mediaPaths != null && !mediaPaths.isEmpty()) {
                for (String path : mediaPaths) {
                    try {
                        Files.deleteIfExists(Paths.get(path));
                    } catch (IOException e) {
                        System.err.println("Failed to delete file: " + path);
                    }
                }
            }
            newsRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("News with ID " + id + " does not exist.");
        }
    }

}