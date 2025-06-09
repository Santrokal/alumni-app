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

	 
	 
	    @GetMapping("/admin/addnews")
	    public String showAddNewsPage(HttpSession session , Model model) {
	    	
	    	// Retrieve the logged-in admin from session
	        AdminEntity loggedInAdmin = (AdminEntity) session.getAttribute("loggedInUser"); // FIXED session key
	        if (loggedInAdmin == null) {
	            return "redirect:/"; // Ensure it redirects to the correct login page
	        }
			
	        // Add admin details to the model
	        model.addAttribute("admin", loggedInAdmin);
	        return "addnewsadmini"; // Corresponds to addnews.html or addnews.jsp
	    }

	 @GetMapping("/admin/deleteNews")
	 public String deleteNews(@RequestParam("id") Long id) {
	     newsService.deleteById(id);
	     return "redirect:/admin/managenews"; // Redirect back to the news management page
	 }
	 
	 
	 
	 
	 @GetMapping("/admin/stories")
	 public String getAllStory(Model model, HttpSession session, HttpServletRequest request) {
	     List<StoryEntity> rawStories = storyService.findAll();

	     List<StoryEntity> storiesList = rawStories != null ? rawStories.stream()
	             .filter(distinctByKey(story -> story.getId() + "-" + story.getTitle()))
	             .sorted(Comparator.comparing(StoryEntity::getCreatedAt, Comparator.nullsLast(Comparator.reverseOrder())))
	             .collect(Collectors.toList()) : null;

	     // Add image URL prefix for displaying
	     String baseUrl = request.getContextPath(); // returns something like "/alumni-app"
	     String imageBasePath = baseUrl + "/story_folder/";

	     // Append full image path to each story
	     if (storiesList != null) {
	         for (StoryEntity story : storiesList) {
	             if (story.getStoryImagePath() != null) {
	                 story.setStoryImagePath(imageBasePath + story.getStoryImagePath());
	             }
	         }
	     }

	     // Admin check
	        AdminEntity loggedInAdmin = (AdminEntity) session.getAttribute("loggedInUser");
	        if (loggedInAdmin == null) {
	            return "redirect:/";
	        }

	        // Encode admin image from file path
	        String base64Image = "";
	        if (loggedInAdmin.getImagePath() != null) {
	            try {
	                byte[] imageBytes = Files.readAllBytes(new File(loggedInAdmin.getImagePath()).toPath());
	                base64Image = Base64.getEncoder().encodeToString(imageBytes);
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
			

	     model.addAttribute("storiesList", storiesList);
	     model.addAttribute("admin", loggedInAdmin);
	     model.addAttribute("base64Image", base64Image);

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
	        if (loggedInAdmin.getImagePath() != null) {
	            try {
	                byte[] imageBytes = Files.readAllBytes(new File(loggedInAdmin.getImagePath()).toPath());
	                base64Image = Base64.getEncoder().encodeToString(imageBytes);
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
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
