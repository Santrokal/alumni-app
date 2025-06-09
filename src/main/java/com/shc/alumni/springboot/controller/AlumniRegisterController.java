package com.shc.alumni.springboot.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import org.springframework.http.MediaType;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
//import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Base64;
import java.util.Collections;
import java.util.Comparator;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

import org.apache.commons.io.FilenameUtils;
import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import java.net.MalformedURLException;


import com.shc.alumni.springboot.entity.AlumniRegisterEntity;
import com.shc.alumni.springboot.entity.CompanyEntity;
import com.shc.alumni.springboot.entity.MembershipEntity;
import com.shc.alumni.springboot.entity.News;
import com.shc.alumni.springboot.entity.StoryEntity;
import com.shc.alumni.springboot.repository.MembershipRepository;
import com.shc.alumni.springboot.service.AlumniRegisterService;
import com.shc.alumni.springboot.service.CompanyService;
import com.shc.alumni.springboot.service.MembershipService;
//import com.shc.alumni.springboot.service.MembershipService;
import com.shc.alumni.springboot.service.NewsService;
import com.shc.alumni.springboot.service.StoryService;


//@RestController
@Controller
public class AlumniRegisterController {


	private static final String UPLOAD_DIR = "photograph";
	
    @Autowired
    private MembershipRepository membershipRepository;
    
    @Autowired
    private StoryService storyService;
    
    @Autowired
    private AlumniRegisterService alumniRegisterService;
    
    
    @Autowired
    private ServletContext servletContext; // Inject at class level
    
    @Autowired
    private NewsService newsService;
    
    @Autowired
    private CompanyService companyService;
    
    
    public AlumniRegisterController(StoryService storyService, CompanyService companyService) {
        this.storyService = storyService;
        this.companyService = companyService;
    }

    
    @GetMapping("/profile")
    public String getProfile(Model model, HttpSession session) {
        // Retrieve logged-in user details from session
        AlumniRegisterEntity alumni = (AlumniRegisterEntity) session.getAttribute("loggedInUser");
        String role = (String) session.getAttribute("role"); // Check if it's an admin or alumni

        if (alumni == null) {
            return "redirect:/"; // Redirect to login page if no user is logged in
        }

        if ("admin".equalsIgnoreCase(role)) {
            return "redirect:/admin/profile"; // Redirect to admin profile if logged in as admin
        }

        // Set profile image path
        String profileImagePath = "/profile/image?email=" + URLEncoder.encode(alumni.getEmailaddress(), StandardCharsets.UTF_8);
        System.out.println("Image Path: " + alumni.getFilePath());

        // Fetch member_id from memberships table using phone number
        Optional<MembershipEntity> membershipOpt = membershipRepository.findByPhoneNumber(alumni.getPhoneno());
        String memberId = membershipOpt.map(MembershipEntity::getMember_id).orElse(null);

        model.addAttribute("alumni", alumni);
        model.addAttribute("image", profileImagePath);
        model.addAttribute("memberId", memberId); // Pass memberId to JSP

        return "profile"; // Render profile.jsp
    }

    @GetMapping("/profile/image")
    public ResponseEntity<Resource> getProfileImage(@RequestParam String email) {
        AlumniRegisterEntity alumni = alumniRegisterService.findByEmail(email);
        
        if (alumni == null || alumni.getFilePath() == null) {
            return ResponseEntity.notFound().build();
        }

        try {
            // Normalize file path (convert \ to / for compatibility)
            Path filePath = Paths.get(alumni.getFilePath().replace("\\", "/"));

            if (!Files.exists(filePath) || !Files.isReadable(filePath)) {
                return ResponseEntity.notFound().build();
            }

            Resource resource = new UrlResource(filePath.toUri());

            // Detect MIME type dynamically
            String contentType = Files.probeContentType(filePath);
            if (contentType == null) {
                contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE; // Fallback
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filePath.getFileName() + "\"")
                    .body(resource);
        } catch (MalformedURLException e) {
            return ResponseEntity.badRequest().build();
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }


    @PostMapping("/updateprofile")
    public String updateProfile(
        @RequestParam("fullname") String fullname,
        @RequestParam("fathersname") String fathersname,
        @RequestParam("nationality") String nationality,
        @RequestParam("dob") String dob,
        @RequestParam("gender") String gender,
        @RequestParam("house_flat_number") String house_flat_number,
        @RequestParam("street_name") String street_name,
        @RequestParam("city") String city,
        @RequestParam("state") String state,
        @RequestParam("postal_code") String postal_code,
        @RequestParam("landmark") String landmark,
        @RequestParam("area") String area,
        @RequestParam("address_type") String address_type,
        @RequestParam("shift") String shift,
        @RequestParam("department") String department,
        @RequestParam("degree_obtained") String degree_obtained,
        @RequestParam("shc_stay_from") String shc_stay_from,
        @RequestParam("shc_stay_to") String shc_stay_to,
        @RequestParam("phoneno") String phoneno,
        @RequestParam("whatsappno") String whatsappno,
        @RequestParam("marital_status") String marital_status,
        @RequestParam(value = "anniversary_year", required = false) String anniversary_year,
        @RequestParam("empstatus") String empstatus,
        @RequestParam("jobdesig") String jobdesig,
        @RequestParam("officephoneno") String officephoneno,
        @RequestParam("officeemail") String officeemail,
        @RequestParam("fieldofexpert") String fieldofexpert,
        @RequestParam(value = "profileImage", required = false) MultipartFile profileImage,
        HttpSession session
    ) {
        // Retrieve the logged-in user from session
        AlumniRegisterEntity alumni = (AlumniRegisterEntity) session.getAttribute("loggedInUser");

        if (alumni == null) {
            return "redirect:/"; // Redirect to login if session is invalid
        }

        try {
            // Update alumni personal information
            alumni.setFullname(fullname);
            alumni.setFathersname(fathersname);
            alumni.setNationality(nationality);
            alumni.setDob(LocalDate.parse(dob));
            alumni.setGender(gender);
            alumni.setHouse_flat_number(house_flat_number);
            alumni.setStreet_name(street_name);
            alumni.setCity(city);
            alumni.setState(state);
            alumni.setPostal_code(postal_code);
            alumni.setLandmark(landmark);
            alumni.setArea(area);
            alumni.setAddress_type(address_type);
            alumni.setShift(shift);
            alumni.setDepartment(department);
            alumni.setDegree_obtained(degree_obtained);
            alumni.setShcStayFrom(shc_stay_from);
            alumni.setShcStayTo(shc_stay_to);
            alumni.setPhoneno(phoneno);
            alumni.setWhatsappno(whatsappno);
            alumni.setMarital_status(marital_status);
            if (anniversary_year != null && !anniversary_year.isEmpty()) {
                alumni.setAnniversary_year(LocalDate.parse(anniversary_year));
            }

            // Update alumni work information
            alumni.setEmpstatus(empstatus);
            alumni.setJobdesig(jobdesig);
            alumni.setOfficephoneno(officephoneno);
            alumni.setOfficeemail(officeemail);
            alumni.setFieldofexpert(fieldofexpert);

            System.out.println("Received update profile request");

            // Handle profile image upload
            if (profileImage != null && !profileImage.isEmpty()) {
                String fileName = fullname + "_" + profileImage.getOriginalFilename();
                Path uploadPath = Paths.get(UPLOAD_DIR);

                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                Path filePath = uploadPath.resolve(fileName);
                Files.copy(profileImage.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
                alumni.setFilePath(filePath.toString());
            }

            // Save the updated alumni details
            alumniRegisterService.saveAlumni(alumni);
            
            // Update session data
            session.setAttribute("loggedInUser", alumni);

            return "redirect:/profile"; // Redirect to the updated profile page
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/profile?error=An error occurred while updating the profile.";
        }
    }
    
    
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Completely clear session

        // Redirect based on user type
        return "redirect:/"; // Change if needed
    }


    



    // Show the alumni registration form
    @GetMapping("/register")
    public String showRegistrationForm() {
        return "alumniregister"; // Returns alumniregister.jsp
    }
    
    // Show the alumni registration form

    
    @GetMapping("/about")
    public String showaboutForm() {
        return "aboutus"; // Returns .jsp
    }
    
    @GetMapping("/ourrecut")
    public String showrecuritsForm() {
        return "ourrecurits"; // Returns .jsp
    }

    
    @GetMapping("/editprofile")
    public String editProfile(Map<String, Object> model, HttpSession session) {
        AlumniRegisterEntity loggedInUser = (AlumniRegisterEntity) session.getAttribute("loggedInUser");

        if (loggedInUser == null) {
            return "redirect:/";
        }

        model.put("alumni", loggedInUser);
        return "editprofile";
    }



    @GetMapping("/home")
    public String showHomePage(Model model,HttpServletRequest request) {
    	List<StoryEntity> rawStories = storyService.findAll();
    	System.out.println("Raw Stories from DB (Count: " + (rawStories != null ? rawStories.size() : 0) + "):");
    	if (rawStories != null) {
    	    rawStories.forEach(story -> System.out.println("ID: " + story.getId() + ", Title: " + story.getTitle()));
    	} else {
    	    System.out.println("No stories retrieved from the database.");
    	}

    	// Ensure uniqueness and sort
    	List<StoryEntity> storiesList = rawStories != null ? rawStories.stream()
    	        .filter(distinctByKey(story -> story.getId() + "-" + story.getTitle())) // Unique by ID + Title
    	        .sorted(Comparator.comparing(StoryEntity::getCreatedAt, Comparator.nullsLast(Comparator.reverseOrder()))) // Sort latest first, nulls last
    	        .collect(Collectors.toList()) : null;

    	// Debug logs
    	System.out.println("Final Stories List (Count: " + (storiesList != null ? storiesList.size() : 0) + ")");
    	if (storiesList != null) {
    	    storiesList.forEach(story -> 
    	            System.out.println("ID: " + story.getId() + ", Title: " + (story.getTitle() != null ? story.getTitle() : "Untitled")));
    	}
        // Fetch distinct company records and sort by creation date (latest first)
        List<CompanyEntity> allCareers = companyService.findAll().stream()
                .filter(distinctByKey(CompanyEntity::getId))
                .sorted((c1, c2) -> c2.getCreatedAt().compareTo(c1.getCreatedAt()))
                .collect(Collectors.toList());

        // Convert company images to Base64
        List<CompanyEntity> companiesListWithBase64 = allCareers.stream()
                .map(company -> {
                    if (company.getImage() != null) {
                        String base64Image = Base64.getEncoder().encodeToString(company.getImage());
                        company.setImageBase64(base64Image);
                    }
                    return company;
                })
                .collect(Collectors.toList());

        // Debug: Print company IDs
        System.out.println("Final Companies List (Count: " + companiesListWithBase64.size() + "):");
        companiesListWithBase64.forEach(company ->
                System.out.println("ID: " + company.getId() + ", Image: " + company.getImage()));

        // Add processed lists to the model
        model.addAttribute("storiesList", storiesList);
        model.addAttribute("companiesList", companiesListWithBase64);

        return "alumnihome";
    }

    // Helper method to filter distinct objects by a key
    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }
    
    
    
    @GetMapping("/expandstory")
    public String showexpandpage() {
        return "storyexpand"; // Returns alumnilogin.jsp
    }

    @PostMapping("/register")
    public ResponseEntity<?> saveAlumni(
            @RequestParam("fullname") String fullname,
            @RequestParam("fathersname") String fathersname,
            @RequestParam("nationality") String nationality,
            @RequestParam("dob") String dob,
            @RequestParam("gender") String gender,
            @RequestParam("house_flat_number") String house_flat_number,
            @RequestParam("street_name") String street_name,
            @RequestParam("city") String city,
            @RequestParam("state") String state,
            @RequestParam("postal_code") String postal_code,
            @RequestParam("landmark") String landmark,
            @RequestParam("area") String area,
            @RequestParam("address_type") String address_type,
            @RequestParam("shift") String shift,
            @RequestParam("department") String department,
            @RequestParam(value = "degree_obtained", required = false) String[] degree_obtained,
            @RequestParam("shc_stay_from") String shc_stay_from,
            @RequestParam("shc_stay_to") String shc_stay_to,
            @RequestParam("marital_status") String marital_status,
            @RequestParam(value = "anniversary_year", required = false) String anniversary_year,
            @RequestParam("whatsappno") String whatsappno,
            @RequestParam("phoneno") String phoneno,
            @RequestParam("emailaddress") String emailaddress,
            @RequestParam("empstatus") String empstatus,
            @RequestParam("jobdesig") String jobdesig,
            @RequestParam("officephoneno") String officephoneno,
            @RequestParam("officeemail") String officeemail,
            @RequestParam("fieldofexpert") String fieldofexpert,
            @RequestPart(value = "profileImage", required = false) MultipartFile[] profileImage,
            HttpSession session) {

        // Validate required fields
        if (fullname == null || fullname.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Fullname is required.");
        }

        // Check if email or fullname is already taken
        if (alumniRegisterService.isEmailaddressOrFullnameTaken(emailaddress, fullname)) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Collections.singletonMap("error", "Email or Full Name already taken"));
        }

        // Create and populate AlumniRegisterEntity object
        AlumniRegisterEntity alumni = new AlumniRegisterEntity();
        alumni.setFullname(fullname);
        alumni.setFathersname(fathersname);
        alumni.setNationality(nationality);
        try {
            alumni.setDob(LocalDate.parse(dob));
        } catch (DateTimeParseException e) {
            return ResponseEntity.badRequest().body("Invalid date of birth format. Use YYYY-MM-DD.");
        }
        alumni.setGender(gender);
        alumni.setHouse_flat_number(house_flat_number);
        alumni.setStreet_name(street_name);
        alumni.setCity(city);
        alumni.setState(state);
        alumni.setPostal_code(postal_code);
        alumni.setLandmark(landmark);
        alumni.setArea(area);
        alumni.setAddress_type(address_type);
        alumni.setShift(shift);
        alumni.setDepartment(department);
        alumni.setDegree_obtained(degree_obtained != null ? String.join(", ", degree_obtained) : null);
        alumni.setShcStayFrom(shc_stay_from);
        alumni.setShcStayTo(shc_stay_to);
        alumni.setMarital_status(marital_status);

        if (anniversary_year != null && !anniversary_year.isEmpty()) {
            try {
                alumni.setAnniversary_year(LocalDate.parse(anniversary_year));
            } catch (DateTimeParseException e) {
                return ResponseEntity.badRequest().body("Invalid anniversary year format. Use YYYY-MM-DD.");
            }
        }

        alumni.setWhatsappno(whatsappno);
        alumni.setPhoneno(phoneno);
        alumni.setEmailaddress(emailaddress);
        alumni.setEmpstatus(empstatus);
        alumni.setJobdesig(jobdesig);
        alumni.setOfficephoneno(officephoneno);
        alumni.setOfficeemail(officeemail);
        alumni.setFieldofexpert(fieldofexpert);

        // Handle profile image upload (supporting only one image for simplicity)
        if (profileImage != null && profileImage.length > 0 && !profileImage[0].isEmpty()) {
            MultipartFile file = profileImage[0]; // Take the first file
            try {
                String originalFileName = file.getOriginalFilename();
                System.out.println("File received: " + originalFileName);

                // Define the upload directory relative to the webapp root
                String appRoot = servletContext.getRealPath("/");
                if (appRoot == null) {
                    System.out.println("ServletContext.getRealPath('/') returned null. Falling back to system property.");
                    appRoot = System.getProperty("user.dir") + "/webapp/";
                }
                Path uploadPath = Paths.get(appRoot, "photograph");

                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                    System.out.println("Created upload directory: " + uploadPath);
                }

                // Generate a unique filename
                String sanitizedFullname = fullname.trim().toLowerCase().replaceAll("[^a-zA-Z0-9]", "_");
                String extension = originalFileName != null && originalFileName.contains(".")
                        ? originalFileName.substring(originalFileName.lastIndexOf("."))
                        : ".png"; // Default to .png if no extension
                String fileName = sanitizedFullname + "_" + System.currentTimeMillis() + extension;
                Path filePath = uploadPath.resolve(fileName);

                // Save the file
                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("File successfully saved: " + filePath);

                // Store only the filename in the entity
                alumni.setFilePath(fileName);
            } catch (IOException e) {
                System.out.println("Error saving file: " + e.getMessage());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Error uploading file: " + e.getMessage());
            }
        } else {
            System.out.println("No profile image uploaded.");
        }

        // Save the alumni entity
        try {
            alumniRegisterService.saveAlumni(alumni);
        } catch (Exception e) {
            System.out.println("Error saving alumni: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error saving alumni data: " + e.getMessage());
        }

        // Optionally set the user in session if registration logs them in
        // session.setAttribute("loggedInUser", alumni);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Collections.singletonMap("message", "Alumni registered successfully"));
    }


        
    // Success page
    @GetMapping("/success")
    public String showSuccessPage() {
        return "alumniregistersuccess"; // Returns alumniregistersuccess.jsp
    }

    // Retrieve all alumni records
    // @GetMapping("/all")
    //public ResponseEntity<List<AlumniRegisterEntity>> getAllAlumni() {
    //  List<AlumniRegisterEntity> alumniList = alumniRegisterService.getAllAlumni();
    //  return new ResponseEntity<>(alumniList, HttpStatus.OK);
    //}

    // Retrieve an alumni record by ID

}
