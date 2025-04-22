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

import javax.servlet.http.HttpSession;

//import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    // Show Registration Form
    @GetMapping("/register")
    public String showRegisterForm() {
        return "register"; // Points to adminregister.jsp
    }
    
    @GetMapping("/addusers")
    public String showAddUserForm(HttpSession session , Model model) {
    	// Retrieve the logged-in admin from session
        AdminEntity loggedInAdmin = (AdminEntity) session.getAttribute("loggedInUser"); // FIXED session key
        if (loggedInAdmin == null) {
            return "redirect:/"; // Ensure it redirects to the correct login page
        }

        // Encode admin image if available
        String base64Image = "";
        if (loggedInAdmin.getImage() != null && loggedInAdmin.getImage().length > 0) {
            base64Image = Base64.getEncoder().encodeToString(loggedInAdmin.getImage());
        }
		
        // Add admin details to the model
        model.addAttribute("base64Image", base64Image);
        model.addAttribute("admin", loggedInAdmin);
        return "addusers"; // Points to adminregister.jsp
    }
    
    
    
    @PostMapping("/adduser")
    public String addUser(
            @RequestParam("fullName") String fullName,
            @RequestParam("emailAddress") String emailAddress,
            @RequestParam("dob") String dob,
            @RequestParam(value = "phoneNo", required = false) String phoneNo,
            @RequestParam(value = "password", required = false) String password,
            @RequestParam(value = "profileImage", required = false) MultipartFile profileImage,
            HttpSession session,Model model) {

        try {
            // Use DOB as password if no password is provided
            if (password == null || password.isEmpty()) {
                password = dob;
            }
            
         // Retrieve the logged-in admin from session
            AdminEntity loggedInAdmin = (AdminEntity) session.getAttribute("loggedInUser"); // FIXED session key
            if (loggedInAdmin == null) {
                return "redirect:/"; // Ensure it redirects to the correct login page
            }

            // Encode admin image if available
            String base64Image = "";
            if (loggedInAdmin.getImage() != null && loggedInAdmin.getImage().length > 0) {
                base64Image = Base64.getEncoder().encodeToString(loggedInAdmin.getImage());
            }
    		
            // Add admin details to the model
            model.addAttribute("base64Image", base64Image);
            model.addAttribute("admin", loggedInAdmin);

            // Check if the email already exists
            if (adminService.emailExists(emailAddress)) {
                model.addAttribute("errorMessage", "Email address already exists!");
                return "addusers"; // Stay on the add user form
            }

            byte[] imageBytes = null;
            if (profileImage != null && !profileImage.isEmpty()) {
                // Convert the profile image to byte array
                imageBytes = profileImage.getBytes();
            }

            // Call the service to save the new user (admin) data in the database
            adminService.registerAdmin(fullName, emailAddress, password, dob, phoneNo, imageBytes);

            // Add a success message
            model.addAttribute("successMessage", "User added successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "Error adding user: " + e.getMessage());
        }

        return "addusers"; // Return back to the add users page
    }
    
   

    // Show AdminEntity Home Page


    @GetMapping("/getalluser")
    public String getAllUsers(HttpSession session,Model model) {
        // Fetch all AdminEntity objects
        List<AdminEntity> allEntityUsers = adminRepository.findAll();
     // Retrieve the logged-in admin from session
        AdminEntity loggedInAdmin = (AdminEntity) session.getAttribute("loggedInUser"); // FIXED session key
        if (loggedInAdmin == null) {
            return "redirect:/"; // Ensure it redirects to the correct login page
        }

        // Encode admin image if available
        String base64Image = "";
        if (loggedInAdmin.getImage() != null && loggedInAdmin.getImage().length > 0) {
            base64Image = Base64.getEncoder().encodeToString(loggedInAdmin.getImage());
        }
		
        
        
        // Convert AdminEntity to Admin (model class)
        List<Admin> allUsers = allEntityUsers.stream()
                                             .map(entity -> {
                                                 Admin admin = new Admin();
                                                 admin.setFullName(entity.getFullName());
                                                 admin.setEmailAddress(entity.getEmailAddress());
                                                 admin.setDob(entity.getDob());
                                                 admin.setPhoneNo(entity.getPhoneNo());

                                                 // Set the image (byte array)
                                                 if (entity.getImage() != null) {
                                                     admin.setImage(entity.getImage());  // Set byte[] image data
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




    // Show AdminEntity Profile
    @GetMapping("/adminprofile")
    public String showAdminProfilePage(HttpSession session, Model model) {
        // Retrieve the logged-in admin from the session
    	   AdminEntity loggedInAdmin = (AdminEntity) session.getAttribute("loggedInUser"); // FIXED session key
           if (loggedInAdmin == null) {
           
           }
        if (loggedInAdmin != null) {
            String base64Image = null;

            // Only encode the image if it exists and has data
            if (loggedInAdmin.getImage() != null && loggedInAdmin.getImage().length > 0) {
                base64Image = Base64.getEncoder().encodeToString(loggedInAdmin.getImage());
            }

            // Ensure base64Image does NOT contain "data:image/jpeg;base64," prefix in Java
            model.addAttribute("base64Image", base64Image);
            model.addAttribute("admin", loggedInAdmin);
            return "adminprofile"; // Points to adminprofile.jsp
        } else {
            return "redirect:/"; // Redirect if no admin is logged in
        }
    }

    

    @GetMapping("/widget")
    public String showWidgetPage(HttpSession session, 
            Model model) {
        // Retrieve the logged-in admin from session
        AdminEntity loggedInAdmin = (AdminEntity) session.getAttribute("loggedInUser"); // FIXED session key
        if (loggedInAdmin == null) {
            return "redirect:/"; // Ensure it redirects to the correct login page
        
        }
        // Encode admin image if available
        String base64Image = "";
        if (loggedInAdmin.getImage() != null && loggedInAdmin.getImage().length > 0) {
            base64Image = Base64.getEncoder().encodeToString(loggedInAdmin.getImage());
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
        AdminEntity loggedInAdmin = (AdminEntity) session.getAttribute("loggedInUser"); // FIXED session key
        if (loggedInAdmin == null) {
            return "redirect:/"; // Ensure it redirects to the correct login page
        }

        // Encode admin image if available
        String base64Image = "";
        if (loggedInAdmin.getImage() != null && loggedInAdmin.getImage().length > 0) {
            base64Image = Base64.getEncoder().encodeToString(loggedInAdmin.getImage());
        }

        // Retrieve unsolved messages
        List<ContactEntity> messages = contactRepository.findBySkippedFalse();
        model.addAttribute("messages", messages);

        // Add admin details to the model
        model.addAttribute("base64Image", base64Image);
        model.addAttribute("admin", loggedInAdmin);

        return "adminhome"; // Renders adminhome.jsp
    }


    // Register AdminEntity
    @PostMapping("/register")
    public String registerAdmin(@RequestParam("fullName") String fullName,
                                @RequestParam("emailAddress") String emailAddress,
                                @RequestParam("password") String password,
                                @RequestParam("dob") String dob,
                                @RequestParam("phoneNo") String phoneNo,
                                @RequestParam("image") MultipartFile image,
                                Model model) {
        try {
            // Check if the email already exists
            if (adminService.emailExists(emailAddress)) {
                model.addAttribute("error", "Email address already exists!");
                return "register";
            }

            // Convert image to byte array
            byte[] imageBytes = image.getBytes();

            // Call service to save the admin details
            adminService.registerAdmin(fullName, emailAddress, password, dob, phoneNo, imageBytes);

            model.addAttribute("success", "Registration successful! Please login.");
            return "adminlogin";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "An error occurred during registration. Please try again.");
            return "register";
        }
    }
    // Handle Logout

    
    
}
