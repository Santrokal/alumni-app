package com.shc.alumni.springboot.controller;

import com.shc.alumni.springboot.entity.AdminEntity;
import com.shc.alumni.springboot.entity.BillPdfEntity;
import com.shc.alumni.springboot.entity.ContactEntity;
//import com.shc.alumni.springboot.entity.AlumniRegisterEntity;
//import com.shc.alumni.springboot.entity.ContactEntity;
import com.shc.alumni.springboot.model.Admin;
import com.shc.alumni.springboot.repository.AdminRepository;
import com.shc.alumni.springboot.repository.BillPdfRepository;
import com.shc.alumni.springboot.repository.ContactRepository;
import com.shc.alumni.springboot.service.AdminService;
//import com.shc.alumni.springboot.service.ContactService;
//import com.shc.alumni.springboot.service.AlumniRegisterService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
//import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;
    
    @Autowired
    private AdminRepository adminRepository;
    
    @Autowired
    private BillPdfRepository billPdfRepository;
    
    @Autowired
    private ContactRepository contactRepository;


    // Register AdminEntity
    private static final String UPLOAD_DIR = "photograph";

    @Autowired
    private ServletContext servletContext;
    
    // Show Registration Form
    @GetMapping("/register")
    public String showRegisterForm() {
        return "register"; // Points to adminregister.jsp
    }
    
    @GetMapping("/addusers")
    public String showAddUserForm(HttpSession session , Model model) {
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
		
        // Add admin details to the model
        model.addAttribute("base64Image", base64Image);
        model.addAttribute("admin", loggedInAdmin);
        return "addusers"; // Points to adminregister.jsp
    }
    
    
    @PostMapping("/adduser")
    public String addUser(@RequestParam("fullName") String fullName,
                                @RequestParam("emailAddress") String emailAddress,
                                @RequestParam("password") String password,
                                @RequestParam("dob") String dob,
                                @RequestParam("phoneNo") String phoneNo,
                                @RequestParam("image") MultipartFile image,
                                Model model, HttpSession session) {
        try {
            if (adminService.emailExists(emailAddress)) {
                model.addAttribute("error", "Email address already exists!");
                return "register";
            }
            
            
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


            // Save image to photograph folder
            String uploadDir = "photograph/";
            String originalFilename = image.getOriginalFilename();
            String imageFileName = System.currentTimeMillis() + "_" + originalFilename;
            File savePath = new File(uploadDir + imageFileName);
            savePath.getParentFile().mkdirs(); // Ensure directory exists
            image.transferTo(savePath);

            // Call service to save admin details with image path
            adminService.registerAdmin(fullName, emailAddress, password, dob, phoneNo, imageFileName);

            model.addAttribute("success", "User added successfully.");
            return "adminlogin";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "An error occurred during registration. Please try again.");
            return "addusers";
        }
    }
    
   

    // Show AdminEntity Home Page

    

    @GetMapping("/getalluser")
    public String getAllUsers(HttpSession session,Model model) {
        // Fetch all AdminEntity objects
        List<AdminEntity> allEntityUsers = adminRepository.findAll();
     // Retrieve the logged-in admin from session
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

        // Convert AdminEntity to Admin (model class)
        List<Admin> allUsers = allEntityUsers.stream()
        	    .map(entity -> {
        	        Admin admin = new Admin();
        	        admin.setFullName(entity.getFullName());
        	        admin.setEmailAddress(entity.getEmailAddress());
        	        admin.setDob(entity.getDob());
        	        admin.setPhoneNo(entity.getPhoneNo());

        	        // Set the image by reading bytes from the image path
        	        if (entity.getImagePath() != null) {
        	            try {
        	                File imageFile = new File(entity.getImagePath());
        	                if (imageFile.exists()) {
        	                    byte[] imageBytes = Files.readAllBytes(imageFile.toPath());
        	                    admin.setImage(imageBytes); // Assuming Admin has setImage(byte[])
        	                }
        	            } catch (IOException e) {
        	                e.printStackTrace(); // Optionally log or handle the error
        	            }
        	        }

        	        return admin;
        	    })
        	    .collect(Collectors.toList());
        
        // Add the list of Admin objects to the model
        model.addAttribute("allUsers", allUsers);
     // Add admin details to the model
        model.addAttribute("base64Image", base64Image);
        model.addAttribute("admin", loggedInAdmin);

        // Return the JSP page
        return "getalluser"; // This will render the getalluser.jsp page
    }

   /*@GetMapping("/updateprofile")
    public String showAdminUpdateProfileForm(HttpSession session, Model model) {
        AdminEntity admin = (AdminEntity) session.getAttribute("loggedInUser");

        if (admin == null) {
            return "redirect:/"; // Not logged in
        }

        model.addAttribute("admin", admin);
        return "adminupdateprofile"; 
    }

    @PostMapping("/updateprofile")
    public String updateAdminProfile(
            @RequestParam("fullName") String fullName,
            @RequestParam("emailAddress") String emailAddress,
            @RequestParam("password") String password,
            @RequestParam("dob") String dob,
            @RequestParam("phoneNo") String phoneNo,
            @RequestParam(value = "image", required = false) MultipartFile image,
            HttpSession session) {

        AdminEntity admin = (AdminEntity) session.getAttribute("loggedInUser");
        if (admin == null) {
            return "redirect:/"; // Not logged in
        }

        try {
            admin.setFullName(fullName);
            admin.setEmailAddress(emailAddress);
            admin.setPassword(password);
            admin.setDob(LocalDate.parse(dob));
            admin.setPhoneNo(phoneNo);

            if (image != null && !image.isEmpty()) {
                String originalFilename = image.getOriginalFilename();
                String sanitizedFilename = fullName.trim().toLowerCase().replaceAll("[^a-zA-Z0-9]", "_");
                String extension = originalFilename.substring(originalFilename.lastIndexOf('.'));
                String imageFileName = sanitizedFilename + "_" + System.currentTimeMillis() + extension;

                Path uploadPath = Paths.get(UPLOAD_DIR);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                Path filePath = uploadPath.resolve(imageFileName);
                Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
                admin.setImagePath(imageFileName);
            }

            adminService.saveAdmin(admin);
            session.setAttribute("loggedInUser", admin);

            return "redirect:/admin/adminprofile";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/admin/adminprofile?error=An error occurred while updating the profile.";
        }
    }
*/


    // Show AdminEntity Profile
	
	
	@GetMapping("/adminprofile")
    public String showAdminProfilePage(HttpSession session, Model model) {
        AdminEntity loggedInAdmin = (AdminEntity) session.getAttribute("loggedInUser");
        if (loggedInAdmin == null) {
            return "redirect:/"; // redirect to login
        }
        String base64Image = "";
        if (loggedInAdmin.getImagePath() != null) {
            try {
                byte[] imageBytes = Files.readAllBytes(new File(loggedInAdmin.getImagePath()).toPath());
                base64Image = Base64.getEncoder().encodeToString(imageBytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Add admin object to model to access in JSP
		model.addAttribute("base64Image", base64Image);
        model.addAttribute("admin", loggedInAdmin);
        return "adminprofile"; // JSP name (adminprofile.jsp)
    }



	@PostMapping("/register")
    public String registerAdmin(@RequestParam("fullName") String fullName,
                                @RequestParam("emailAddress") String emailAddress,
                                @RequestParam("password") String password,
                                @RequestParam("dob") String dob,
                                @RequestParam("phoneNo") String phoneNo,
                                @RequestParam("image") MultipartFile image,
                                Model model) {
        try {
            if (adminService.emailExists(emailAddress)) {
                model.addAttribute("error", "Email address already exists!");
                return "register";
            }

            String imageFileName = null;

            if (image != null && !image.isEmpty()) {
                String originalFileName = image.getOriginalFilename();
                System.out.println("Received image: " + originalFileName);

                // Fixed upload directory
                String uploadDir = "C:/Users/Mohammed Salman/alumni-app/photograph";
                Path uploadPath = Paths.get(uploadDir);

                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                    System.out.println("Created upload directory: " + uploadPath);
                }

                // Generate a unique file name
                String sanitizedFullName = fullName.trim().toLowerCase().replaceAll("[^a-zA-Z0-9]", "_");
                String extension = originalFileName != null && originalFileName.contains(".")
                        ? originalFileName.substring(originalFileName.lastIndexOf("."))
                        : ".png";
                imageFileName = sanitizedFullName + "_" + System.currentTimeMillis() + extension;

                // Save image file
                Path filePath = uploadPath.resolve(imageFileName);
                try (InputStream inputStream = image.getInputStream()) {
                    Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
                }

                System.out.println("Saved admin image to: " + filePath);
            } else {
                System.out.println("No image uploaded.");
            }

            // Save admin details with the image file name
            adminService.registerAdmin(fullName, emailAddress, password, dob, phoneNo, imageFileName);

            model.addAttribute("success", "Registration successful! Please login.");
            return "adminlogin";

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "An error occurred during registration. Please try again.");
            return "register";
        }
    }


    

    @GetMapping("/widget")
    public String showWidgetPage(HttpSession session, 
            Model model) {
        // Retrieve the logged-in admin from session
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


        // Get messages
        List<ContactEntity> messages = contactRepository.findBySkippedFalse();
        model.addAttribute("messages", messages);

        // Add admin details
        model.addAttribute("base64Image", base64Image);
        model.addAttribute("admin", loggedInAdmin);

        return "adminwidget";
    }
    


    @GetMapping("/adminhome")
    public String showAdminHomePage(
            @RequestParam(defaultValue = "1") int page, 
            HttpSession session, 
            Model model) {

        int recordsPerPage = 3;
        Pageable pageable = PageRequest.of(page - 1, recordsPerPage);
        Page<BillPdfEntity> billPage = billPdfRepository.findAll(pageable);

        // Add pagination data to model
        model.addAttribute("bills", billPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", billPage.getTotalPages());

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


        // Retrieve unsolved messages
        List<ContactEntity> messages = contactRepository.findBySkippedFalse();
        model.addAttribute("messages", messages);

        // Add admin details to the model
        model.addAttribute("base64Image", base64Image);
        model.addAttribute("admin", loggedInAdmin);

        return "adminhome"; // Renders adminhome.jsp
    }

 

    // Handle Logout 
    
}
