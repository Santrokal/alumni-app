package com.shc.alumni.springboot.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.shc.alumni.springboot.entity.CompanyEntity;
import com.shc.alumni.springboot.service.CompanyService;

//import jakarta.annotation.Resource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.multipart.MultipartFile;

//import java.io.IOException;

import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;

//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;



@Controller
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }
    
    private static final String UPLOAD_DIR = "companyuploads/";

    // GET mapping to display the form
    @GetMapping("/carrer")
    public String showCompanyForm() {
        return "alumnicompony"; // The name of your JSP file (without .jsp extension)
    }
   

    // POST mapping to handle form submission
    @PostMapping("/submitCompany")
    public String submitCompany(
            @RequestParam(value = "position", required = false) String position,
            @RequestParam(value = "location", required = false) String location,
            @RequestParam(value = "role", required = false) String role,
            @RequestParam(value = "about", required = false) String about,
            @RequestParam(value = "jobdetails", required = false) String jobdetails,
            @RequestParam(value = "skills", required = false) String skills,
            @RequestParam(value = "companyemailid", required = false) String companyemailid,
            @RequestParam(value = "applications", required = false) String applications,
            @RequestParam(value = "image", required = false) MultipartFile image,
            @RequestParam(value = "fileUpload", required = false) MultipartFile fileUpload,
            Model model) {
        try {
            CompanyEntity company = new CompanyEntity();

            if (position != null && !position.isEmpty()) {
                company.setPosition(position);
            }
            if (location != null && !location.isEmpty()) {
                company.setLocation(location);
            }
            if (role != null && !role.isEmpty()) {
                company.setRole(role);
            }
            if (about != null && !about.isEmpty()) {
                company.setAbout(about);
            }
            if (companyemailid != null && !companyemailid.isEmpty()) {
                company.setCompanyemailid(companyemailid);
            }
            if (skills != null && !skills.isEmpty()) {
                company.setSkills(skills);
            }
            if (jobdetails != null && !jobdetails.isEmpty()) {
                company.setJobDetails(jobdetails);
            }

            // Handle image upload
            if (image != null && !image.isEmpty()) {
                company.setImage(image.getBytes());
            }

            // Handle file upload and store file path
            if (fileUpload != null && !fileUpload.isEmpty()) {
                String fileName = System.currentTimeMillis() + "_" + fileUpload.getOriginalFilename();
                Path uploadPath = Paths.get(UPLOAD_DIR + fileName);
                Files.createDirectories(uploadPath.getParent());
                Files.write(uploadPath, fileUpload.getBytes());

                company.setFileData(fileUpload.getBytes());  // Store file data
                company.setFilePath(uploadPath.toString()); // Store file path
            } else {
                company.setFilePath(""); // Avoid null value if fileUpload is empty
            }

            // Save the entity using the service
            companyService.saveCompanyEntity(company);
            model.addAttribute("successMessage", "Company details saved successfully!");
        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "Failed to save company details. Please try again.");
        }
        return "redirect:/applyjob";
    }

    @GetMapping("/job-details/{id}")
    public ModelAndView getJobDetails(@PathVariable Long id) {
        CompanyEntity company = companyService.getCompanyById(id);
        ModelAndView modelAndView = new ModelAndView("alumniapplyjobdetails"); // JSP page for job details
        modelAndView.addObject("company", company);
        return modelAndView;
    }
    


    
    @GetMapping("/downloadFile/{id}")
    public ResponseEntity<UrlResource> downloadFile(@PathVariable Long id) {
        CompanyEntity company = companyService.getCompanyById(id);

        if (company == null || company.getFilePath() == null || company.getFilePath().isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        try {
            Path filePath = Paths.get(company.getFilePath());
            UrlResource resource = new UrlResource(filePath.toUri());

            if (resource.exists() || resource.isReadable()) {
                String contentType = Files.probeContentType(filePath);

                // Set Content-Disposition to 'inline' for displayable content (like PDF)
                if (contentType != null && contentType.startsWith("application/pdf")) {
                    return ResponseEntity.ok()
                            .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filePath.getFileName().toString() + "\"")
                            .contentType(MediaType.parseMediaType(contentType))
                            .body(resource);
                } else {
                    // For other file types (image, docx, etc.), force download
                    return ResponseEntity.ok()
                            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filePath.getFileName().toString() + "\"")
                            .contentType(MediaType.parseMediaType(contentType))
                            .body(resource);
                }
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }



}

