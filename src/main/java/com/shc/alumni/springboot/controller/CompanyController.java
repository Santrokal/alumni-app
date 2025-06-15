package com.shc.alumni.springboot.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
//import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.shc.alumni.springboot.entity.CompanyEntity;
import com.shc.alumni.springboot.repository.CompanyRepository;
import com.shc.alumni.springboot.service.CompanyService;

//import jakarta.annotation.Resource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;

//import java.io.IOException;

import org.springframework.core.io.UrlResource;
//import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;



@Controller
public class CompanyController {

    private final CompanyService companyService;
    
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }
    
    @Autowired
    private  CompanyRepository companyRepository;

    
    //private static final String UPLOAD_DIR = "companyuploads/";

    // GET mapping to display the form
    @GetMapping("/carrer")
    public String showCompanyForm() {
        return "alumnicompony"; // The name of your JSP file (without .jsp extension)
    }
   

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
            HttpServletRequest request,
            Model model) {

        try {
            CompanyEntity company = new CompanyEntity();
            company.setPosition(position);
            company.setLocation(location);
            company.setRole(role);
            company.setAbout(about);
            company.setJobDetails(jobdetails);
            company.setSkills(skills);
            company.setCompanyemailid(companyemailid);
            company.setApplications(applications);

            if (image != null && !image.isEmpty()) {
                company.setImage(image.getBytes());
            }

            companyService.saveCompanyEntity(company, fileUpload, request);
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
    


    
    @GetMapping("/downloadFile/{companyId}")
    public ResponseEntity<Resource> serveCompanyFile(@PathVariable Long companyId, HttpServletRequest request) {
        try {
            Optional<CompanyEntity> optionalCompany = companyRepository.findById(companyId);
            if (!optionalCompany.isPresent()) {
                return ResponseEntity.notFound().build();
            }

            CompanyEntity company = optionalCompany.get();
            String fileName = company.getFilePath();

            if (fileName == null || fileName.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            String basePath = request.getServletContext().getRealPath("/WEB-INF/company_folder");
            Path filePath = Paths.get(basePath, fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (!resource.exists() || !resource.isReadable()) {
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
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }





}

