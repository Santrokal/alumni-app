package com.shc.alumni.springboot.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
//import java.util.ArrayList;
import java.util.Base64;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

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
import org.springframework.web.multipart.MultipartFile;

import com.shc.alumni.springboot.entity.AdminEntity;
import com.shc.alumni.springboot.entity.Comment;
//import com.shc.alumni.springboot.entity.ContactEntity;
import com.shc.alumni.springboot.entity.News;
import com.shc.alumni.springboot.entity.StoryEntity;
import com.shc.alumni.springboot.repository.ContactRepository;
import com.shc.alumni.springboot.service.ContactService;
import com.shc.alumni.springboot.service.EmailService;
import com.shc.alumni.springboot.service.NewsService;
import com.shc.alumni.springboot.service.StoryService;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class AdminNewsController {
	
	@Autowired
	private NewsService newsService;
	@Autowired
	private StoryService storyService;
	@Autowired
	ContactService contactService;
	@Autowired
	ContactRepository contactRepository;
	@Autowired
	EmailService emailService;
	@Autowired
	private ServletContext servletContext;
	
	
	 @GetMapping("/admin/managenews")
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
	        // Retrieve the logged-in admin from session
            AdminEntity loggedInAdmin = (AdminEntity) session.getAttribute("loggedInUser");
            if (loggedInAdmin == null) {
                return "redirect:/";
            }
	        
            String base64Image = "";
            try {
                String imagePathInDb = loggedInAdmin.getImagePath();

                if (imagePathInDb != null && !imagePathInDb.trim().isEmpty()) {
                    // Strip any folder prefix like "photograph/"
                    String cleanFileName = Paths.get(imagePathInDb).getFileName().toString();

                    // Get path to /WEB-INF/adminphotograph/
                    String appRoot = servletContext.getRealPath("/");
                    if (appRoot == null) {
                        appRoot = System.getProperty("user.dir") + "/webapp/";
                    }

                    // Final image path
                    Path imagePath = Paths.get(appRoot, "WEB-INF", "adminphotograph", cleanFileName);

                    if (Files.exists(imagePath)) {
                        byte[] imageBytes = Files.readAllBytes(imagePath);
                        base64Image = Base64.getEncoder().encodeToString(imageBytes);
                    } else {
                        System.out.println("⚠ Image not found at: " + imagePath.toString());
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

			
	        // Add admin details to the model
	        model.addAttribute("base64Image", base64Image);

	        // Add sorted and categorized news to the model
	        model.addAttribute("newsList", newsList);
	        model.addAttribute("categorizedNews", categorizedNews);

	        return "adminnews";
	    }
	 
	 @GetMapping("/admin/managenews/{id}")
	    public String viewNews(@PathVariable("id") int id, Model model) {
	        News news = newsService.getNewsById(id);
	        if (news == null) {
	            return "redirect:/404";
	        }

	        //List<Comment> comments = commentService.getCommentsByNewsId(id);

	        model.addAttribute("news", news);
	        //model.addAttribute("comments", comments);
	        
	        // Add a fun fact to the model
	        //String funFact = funFactService.getFunFactForToday();
	        //model.addAttribute("funFact", funFact);    
	        return "adminnews";
	    }
	    
	    @PostMapping("/admin/addnews")
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


	 
	 
	    @GetMapping("/admin/addnews")
	    public String showAddNewsPage(HttpSession session , Model model) {
	    	
	    	// Retrieve the logged-in admin from session
	        AdminEntity loggedInAdmin = (AdminEntity) session.getAttribute("loggedInUser"); // FIXED session key
	        if (loggedInAdmin == null) {
	            return "redirect:/"; // Ensure it redirects to the correct login page
	        }
	        
	        String base64Image = "";
	        try {
	            String imagePathInDb = loggedInAdmin.getImagePath();

	            if (imagePathInDb != null && !imagePathInDb.trim().isEmpty()) {
	                // Strip any folder prefix like "photograph/"
	                String cleanFileName = Paths.get(imagePathInDb).getFileName().toString();

	                // Get path to /WEB-INF/adminphotograph/
	                String appRoot = servletContext.getRealPath("/");
	                if (appRoot == null) {
	                    appRoot = System.getProperty("user.dir") + "/webapp/";
	                }

	                // Final image path
	                Path imagePath = Paths.get(appRoot, "WEB-INF", "adminphotograph", cleanFileName);

	                if (Files.exists(imagePath)) {
	                    byte[] imageBytes = Files.readAllBytes(imagePath);
	                    base64Image = Base64.getEncoder().encodeToString(imageBytes);
	                } else {
	                    System.out.println("⚠ Image not found at: " + imagePath.toString());
	                }
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }

	        
			
	        // Add admin details to the model
	        model.addAttribute("base64Image",base64Image);
	        model.addAttribute("admin", loggedInAdmin);
	        return "addnewsadmini"; // Corresponds to addnews.html or addnews.jsp
	    }

	 @GetMapping("/admin/deleteNews")
	 public String deleteNews(@RequestParam("id") Long id) {
	     newsService.deleteById(id);
	     return "redirect:/admin/managenews"; // Redirect back to the news management page
	 }
	 
	 
	 @GetMapping("/admin/stories")
	    public String getAllStory(Model model, HttpSession session) {
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
	        // Admin check
	        AdminEntity loggedInAdmin = (AdminEntity) session.getAttribute("loggedInUser");
	        if (loggedInAdmin == null) {
	            return "redirect:/";
	        }

	        // Encode admin image from file path
	        String base64Image = "";
	        try {
	            String imagePathInDb = loggedInAdmin.getImagePath();

	            if (imagePathInDb != null && !imagePathInDb.trim().isEmpty()) {
	                // Strip any folder prefix like "photograph/"
	                String cleanFileName = Paths.get(imagePathInDb).getFileName().toString();

	                // Get path to /WEB-INF/adminphotograph/
	                String appRoot = servletContext.getRealPath("/");
	                if (appRoot == null) {
	                    appRoot = System.getProperty("user.dir") + "/webapp/";
	                }

	                // Final image path
	                Path imagePath = Paths.get(appRoot, "WEB-INF", "adminphotograph", cleanFileName);

	                if (Files.exists(imagePath)) {
	                    byte[] imageBytes = Files.readAllBytes(imagePath);
	                    base64Image = Base64.getEncoder().encodeToString(imageBytes);
	                } else {
	                    System.out.println("⚠ Image not found at: " + imagePath.toString());
	                }
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }

			
	        model.addAttribute("base64Image", base64Image);
	     model.addAttribute("storiesList", storiesList);
	        return "adminstories";
	    }


	    @GetMapping("/admin/deleteStories")
	    public String deleteStories(@RequestParam("id") Long id) {
	        storyService.deleteById(id);
	        return "redirect:/admin/stories"; // Redirect back to the stories management page
	    }

	    @PostMapping("/admin/addstories")
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
	    
	    @GetMapping("/admin/addstories")
	    public String showAddStoryPage(HttpSession session, Model model) {
	        // Retrieve the logged-in admin from session
	        AdminEntity loggedInAdmin = (AdminEntity) session.getAttribute("loggedInUser");
	        if (loggedInAdmin == null) {
	            return "redirect:/";
	        }

	        // Encode admin image from file path
	        String base64Image = "";
	        try {
	            String imagePathInDb = loggedInAdmin.getImagePath();

	            if (imagePathInDb != null && !imagePathInDb.trim().isEmpty()) {
	                // Strip any folder prefix like "photograph/"
	                String cleanFileName = Paths.get(imagePathInDb).getFileName().toString();

	                // Get path to /WEB-INF/adminphotograph/
	                String appRoot = servletContext.getRealPath("/");
	                if (appRoot == null) {
	                    appRoot = System.getProperty("user.dir") + "/webapp/";
	                }

	                // Final image path
	                Path imagePath = Paths.get(appRoot, "WEB-INF", "adminphotograph", cleanFileName);

	                if (Files.exists(imagePath)) {
	                    byte[] imageBytes = Files.readAllBytes(imagePath);
	                    base64Image = Base64.getEncoder().encodeToString(imageBytes);
	                } else {
	                    System.out.println("⚠ Image not found at: " + imagePath.toString());
	                }
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }

			

	        model.addAttribute("base64Image", base64Image);
	        model.addAttribute("admin", loggedInAdmin);
	        return "adminaddalumnistories"; // Corresponds to adminaddalumnistories.jsp
	    }

	    private static <T> java.util.function.Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
	        java.util.Map<Object, Boolean> seen = new java.util.concurrent.ConcurrentHashMap<>();
	        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
	    }
}
