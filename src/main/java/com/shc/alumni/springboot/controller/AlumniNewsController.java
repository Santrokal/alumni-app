package com.shc.alumni.springboot.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
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
    
    @GetMapping("/news_folder/{filename}")
    public ResponseEntity<Resource> serveStoryMedia(@PathVariable String filename, HttpServletRequest request) {
        try {
            // Get the real path to /WEB-INF/story_folder
            String basePath = request.getServletContext().getRealPath("/WEB-INF/news_folder");
            Path filePath = Paths.get(basePath, filename).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            System.out.println("Serving file from: " + filePath);

            if (!resource.exists() || !resource.isReadable()) {
                System.out.println("File not found or unreadable: " + filePath);
                return ResponseEntity.notFound().build();
            }

            String contentType = Files.probeContentType(filePath);
            if (contentType == null) {
                contentType = "application/octet-stream";
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(resource);

        } catch (Exception e) {
            System.err.println("Error serving file: " + filename + ", " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
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