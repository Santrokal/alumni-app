package com.shc.alumni.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.shc.alumni.springboot.entity.AdminEntity;
import com.shc.alumni.springboot.entity.AlumniRegisterEntity;
import com.shc.alumni.springboot.entity.ContactEntity;
import com.shc.alumni.springboot.repository.ContactRepository;
import com.shc.alumni.springboot.service.AlumniRegisterService;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;

@Controller
public class AlumniDetailsController {

    @Autowired
    private AlumniRegisterService alumniRegisterService;
    @Autowired
    private ContactRepository contactRepository;
    @Autowired
    private ServletContext servletContext;
    
    private static final Logger logger = LoggerFactory.getLogger(AlumniDetailsController.class);


    @GetMapping("/admin/alumnidetails")
    public String getAllAlumniDetails( HttpSession session, 
            Model model) {
        List<AlumniRegisterEntity> alumniList = alumniRegisterService.findAll();
        model.addAttribute("alumniList", alumniList);
        
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

		

        // Get messages
        List<ContactEntity> messages = contactRepository.findBySkippedFalse();
        model.addAttribute("messages", messages);

        // Add admin details
        model.addAttribute("base64Image", base64Image);
        model.addAttribute("admin", loggedInAdmin);


        alumniList.forEach(alumni -> logger.info("Final FilePath: " + alumni.getFilePath()));
        // Return the JSP view name to render the data
        return "getallalumnidetails";  // Make sure this matches your JSP file name
    }
    
    @GetMapping("/admin/alumnidetails/image/{emailaddress}")
    public ResponseEntity<Resource> getAlumniImage(@PathVariable String emailaddress) {
        AlumniRegisterEntity alumni = alumniRegisterService.findByEmail(emailaddress);
        if (alumni == null || alumni.getFilePath() == null || alumni.getFilePath().isEmpty()) {
            logger.error("Image not found for email: " + emailaddress);
            return ResponseEntity.notFound().build();
        }

        try {
            Path filePath = Paths.get(alumni.getFilePath()).toAbsolutePath();
            logger.info("Fetching image from path: " + filePath.toString());

            if (!Files.exists(filePath) || !Files.isReadable(filePath)) {
                logger.error("Image not found or not readable: " + filePath.toString());
                return ResponseEntity.notFound().build();
            }

            Resource resource = new UrlResource(filePath.toUri());
            String mimeType = Files.probeContentType(filePath);
            if (mimeType == null) {
                mimeType = "application/octet-stream";
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(mimeType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filePath.getFileName() + "\"")
                    .body(resource);
        } catch (Exception e) {
            logger.error("Error fetching image for email: " + emailaddress, e);
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/admin/alumnifilter")
    public String searchAlumni(@RequestParam(name = "year", required = false) Integer year,
                               Model model, HttpSession session) {
        if (year == null) {
            model.addAttribute("error", "Year is required.");
            return "adminalumnidetailsfilter";
        }
        
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

		
		 List<ContactEntity> messages = contactRepository.findBySkippedFalse();
        model.addAttribute("messages", messages);
        
        // Add admin details
        model.addAttribute("base64Image", base64Image);
        model.addAttribute("admin", loggedInAdmin);

        List<AlumniRegisterEntity> alumniList = alumniRegisterService.getAlumniByYear(year);
        model.addAttribute("alumniList", alumniList);
        

        if (alumniList.isEmpty()) {
            model.addAttribute("message", "No alumni records found for the given year.");
        }

        return "adminalumnidetailsfilter";
    }




}
