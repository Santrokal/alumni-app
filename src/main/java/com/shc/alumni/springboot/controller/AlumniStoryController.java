package com.shc.alumni.springboot.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.shc.alumni.springboot.entity.StoryComment;
import com.shc.alumni.springboot.entity.StoryEntity;
import com.shc.alumni.springboot.entity.AlumniRegisterEntity;
import com.shc.alumni.springboot.service.FunFactService;
import com.shc.alumni.springboot.service.StoryCommentService;
import com.shc.alumni.springboot.service.StoryService;
import com.shc.alumni.springboot.repository.AlumniRegisterRepository;

@Controller
public class AlumniStoryController {

    @Autowired
    private StoryService storyService;

    @Autowired
    private FunFactService funFactService;

    @Autowired
    private StoryCommentService storyCommentService;

    @Autowired
    private AlumniRegisterRepository alumniRegisterRepository;

    @GetMapping("/stories")
    public String getAllStory(Model model) {
        List<StoryEntity> rawStories = storyService.findAll();
        System.out.println("Raw Stories from DB (Count: " + (rawStories != null ? rawStories.size() : 0) + "):");
        if (rawStories != null) {
            rawStories.forEach(story -> System.out.println("ID: " + story.getId() + ", Title: " + story.getTitle() + ", Image Path: " + story.getStoryImagePath()));
        } else {
            System.out.println("No stories retrieved from the database.");
        }

        List<StoryEntity> storiesList = rawStories != null ? rawStories.stream()
                .filter(distinctByKey(story -> story.getId() + "-" + story.getTitle()))
                .sorted(Comparator.comparing(StoryEntity::getCreatedAt, Comparator.nullsLast(Comparator.reverseOrder())))
                .collect(Collectors.toList()) : null;

        System.out.println("Final Stories List (Count: " + (storiesList != null ? storiesList.size() : 0) + ")");
        if (storiesList != null) {
            storiesList.forEach(story -> 
                    System.out.println("ID: " + story.getId() + ", Title: " + (story.getTitle() != null ? story.getTitle() : "Untitled") + ", Image Path: " + story.getStoryImagePath()));
        }
        model.addAttribute("storiesList", storiesList);
        return "alumnistories";
    }
    
    @GetMapping("/story_media/{filename}")
    public ResponseEntity<Resource> serveStoryMedia(@PathVariable String filename) {
        try {
            Path file = Paths.get("C:/Users/Mohammed Salman/alumni-app/story_folder/" + filename);
            Resource resource = new UrlResource(file.toUri());
            
            System.out.println("Serving story media: " + file.toString());
            
            if (resource.exists() && resource.isReadable()) {
                String contentType = Files.probeContentType(file);
                if (contentType == null) {
                    if (filename.toLowerCase().endsWith(".jpg") || filename.toLowerCase().endsWith(".jpeg")) {
                        contentType = "image/jpeg";
                    } else if (filename.toLowerCase().endsWith(".png")) {
                        contentType = "image/png";
                    } else {
                        contentType = "application/octet-stream";
                    }
                }
                System.out.println("Content-Type: " + contentType);
                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(contentType))
                        .body(resource);
            } else {
                System.out.println("File not found or unreadable: " + filename);
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            System.out.println("Error serving story media: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    private static <T> java.util.function.Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        java.util.Map<Object, Boolean> seen = new java.util.concurrent.ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }
    @GetMapping("/stories/{id}")
    public String viewStories(@PathVariable("id") int id, Model model) {
        StoryEntity story = storyService.getStoryById(id);
        if (story == null) {
            return "redirect:/404";
        }
        System.out.println("Title: " + story.getTitle());
        System.out.println("Content: " + story.getContent());
        List<StoryComment> storyComments = storyCommentService.getCommentsByStoryId(id);
        String funFact = funFactService.getFunFactForToday();
        model.addAttribute("stories", story);
        model.addAttribute("comments", storyComments);
        model.addAttribute("funFact", funFact);
        return "storyexpand";
    }

    @PostMapping("/stories/{id}/storycomment")
    public String addComment(@PathVariable("id") int storyId,
                             @RequestParam("storycommentText") String storycommentText) {
        if (storycommentText != null && !storycommentText.trim().isEmpty()) {
            StoryComment storyComment = new StoryComment();
            storyComment.setStoryId(storyId);
            storyComment.setText(storycommentText);
            storyCommentService.addComment(storyComment);
        }
        return "redirect:/stories/" + storyId;
    }

    @PostMapping("/addstories")
    public ResponseEntity<Object> addStory(
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam(value = "linkedinProfile", required = false) String linkedinProfile,
            @RequestParam(value = "consentToPublish", defaultValue = "false") boolean consentToPublish,
            @RequestParam("department") String department,
            @RequestParam(value = "organization", required = false) String organization,
            @RequestParam(required = false) MultipartFile storyImage,
            HttpServletRequest request) {
        try {
            // Now includes request parameter to support dynamic upload path
            storyService.saveStory(title, content, linkedinProfile, consentToPublish, department, organization, storyImage, request);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("{\"message\": \"Story added successfully!\"}");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"Error saving story: " + e.getMessage() + "\"}");
        }
    }


    @GetMapping("/addstories")
    public String showAddStoryPage() {
        return "addalumnistories"; // Corresponds to addalumnistories.jsp
    }

}