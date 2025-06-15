package com.shc.alumni.springboot.controller;

import com.shc.alumni.springboot.entity.AdminEntity;
import com.shc.alumni.springboot.service.FormService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class FormController {

    @Autowired
    private FormService formService;
    @Autowired
    private ServletContext servletContext;

    // Search fields endpoint - Changed to GET for better REST practice
    @GetMapping(value = "/search", produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String searchFields(@RequestParam("term") String searchTerm) {
        return formService.searchFields(searchTerm);
    }

    // Create form endpoint
    @PostMapping("/create")
    public String createForm(@RequestParam("fieldsData") String fieldsData, 
                           RedirectAttributes redirectAttributes) {
        try {
            formService.createForm(fieldsData);
            redirectAttributes.addFlashAttribute("message", "Form created successfully!");
            return "redirect:/admin/adminhome";
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Error creating form: " + e.getMessage());
            return "redirect:/admin/error";
        }
    }

    // Show create form page
    @GetMapping("/create-form")
    public String showCreateForm(Model model, HttpSession session) {
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
        return "createForm"; // Ensure this matches your JSP file name (createForm.jsp)
    }
}