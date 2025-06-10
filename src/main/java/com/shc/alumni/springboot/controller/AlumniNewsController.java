package com.shc.alumni.springboot.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.shc.alumni.springboot.entity.Comment;
import com.shc.alumni.springboot.entity.News;
import com.shc.alumni.springboot.service.CommentService;
import com.shc.alumni.springboot.service.FunFactService;
import com.shc.alumni.springboot.service.NewsService;

@Controller
public class AlumniNewsController {
    
    @Autowired
    private NewsService newsService;
    
    @Autowired
    private CommentService commentService;
    
    @Autowired
    private FunFactService funFactService;
    
    @GetMapping("/news")
    public String getAllNews(Model model, HttpSession session) {
        // Fetch all news records
        List<News> newsList = newsService.findAll();

        // Sort the list based on createdAt in descending order (newest first)
        newsList.sort((news1, news2) -> news2.getCreatedAt().compareTo(news1.getCreatedAt()));

        // Categorize news by category
        Map<String, List<News>> categorizedNews = new LinkedHashMap<>();
        for (News news : newsList) {
            categorizedNews.computeIfAbsent(news.getCategory(), k -> new ArrayList<>()).add(news);
        }

        // Add sorted and categorized news to the model
        model.addAttribute("newsList", newsList);
        model.addAttribute("categorizedNews", categorizedNews);

        return "alumninews";
    }
    
    @GetMapping("/news/{id}")
    public String viewNews(@PathVariable("id") int id, Model model) {
        News news = newsService.getNewsById(id);
        if (news == null) {
            return "redirect:/404";
        }

        List<Comment> comments = commentService.getCommentsByNewsId(id);

        model.addAttribute("news", news);
        model.addAttribute("comments", comments);
        
        // Add a fun fact to the model
        String funFact = funFactService.getFunFactForToday();
        model.addAttribute("funFact", funFact);    
        return "newsexpand";
    }
    
    @GetMapping("/media/{filename}")
    public ResponseEntity<Resource> serveMedia(@PathVariable String filename) {
        try {
            Path file = Paths.get("C:\\Users\\Mohammed Salman\\alumni-app\\src\\main\\webapp\\news_folder" + filename);
            Resource resource = new UrlResource(file.toUri());
            
            System.out.println("Alumni - Serving media: " + file.toString()); // Debug log
            
            if (resource.exists() && resource.isReadable()) {
                String contentType = Files.probeContentType(file);
                if (contentType == null) {
                    if (filename.toLowerCase().endsWith(".jpg") || filename.toLowerCase().endsWith(".jpeg")) {
                        contentType = "image/jpeg";
                    } else if (filename.toLowerCase().endsWith(".png")) {
                        contentType = "image/png";
                    } else if (filename.toLowerCase().endsWith(".mp4")) {
                        contentType = "video/mp4";
                    } else {
                        contentType = "application/octet-stream";
                    }
                }
                System.out.println("Alumni - Content-Type: " + contentType); // Debug log
                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(contentType))
                        .body(resource);
            } else {
                System.out.println("Alumni - File not found or unreadable: " + filename); // Debug log
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            System.out.println("Alumni - Error serving media: " + e.getMessage()); // Debug log
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping("/news/{id}/comment")
    public String addComment(@PathVariable("id") int newsId, 
                            @RequestParam("commentText") String commentText) {
        if (commentText != null && !commentText.trim().isEmpty()) {
            Comment comment = new Comment();
            comment.setNewsId(newsId);
            comment.setText(commentText);
            commentService.addComment(comment);
        }
        return "redirect:/news/" + newsId;
    }
}