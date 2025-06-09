package com.shc.alumni.springboot.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.shc.alumni.springboot.entity.AlumniJobApplications;
import com.shc.alumni.springboot.entity.CompanyEntity;
import com.shc.alumni.springboot.repository.AlumniJobApplicationsRepository;
import com.shc.alumni.springboot.repository.CompanyRepository;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Controller
public class JobApplicationController {

    @Autowired
    private AlumniJobApplicationsRepository jobApplicationsRepository;

    @Autowired
    private CompanyRepository companyRepository; // Repository to fetch company details

    @Autowired
    private JavaMailSender mailSender;

    // Path to store uploaded files
    private static final String UPLOAD_DIR = "uploads/";
    
    @GetMapping("/alumniapplytojob")
    public String showApplicationForm(@RequestParam("companyId") Long companyId, Model model) {
        model.addAttribute("companyId", companyId);
        return "alumniapplytojob"; // Navigate to the application form JSP
    }


    @PostMapping("/submitJobApplication")
    public String submitJobApplication(
            @RequestParam("companyId") Long companyId,
            @RequestParam("fullname") String fullname,
            @RequestParam("phonenumber") String phonenumber,
            @RequestParam("emailaddress") String emailaddress,
            @RequestParam("resume") MultipartFile resume,
            Model model) {

        try {
            // Save the uploaded file
            String fileName = System.currentTimeMillis() + "_" + resume.getOriginalFilename();
            Path uploadPath = Paths.get(UPLOAD_DIR + fileName);
            Files.createDirectories(uploadPath.getParent());
            Files.write(uploadPath, resume.getBytes());

            // Create and save application data
            AlumniJobApplications application = new AlumniJobApplications();
            application.setFullname(fullname);
            application.setPhonenumber(phonenumber);
            application.setEmailaddress(emailaddress);

            // Fetch company email from the database
            Optional<CompanyEntity> companyOptional = companyRepository.findById(companyId);
            if (companyOptional.isPresent()) {
                String companyEmail = companyOptional.get().getCompanyemailid();
                application.setSelectcompany(companyOptional.get().getPosition());
                
                // Save the file path
                application.setFilePath(uploadPath.toString());
                jobApplicationsRepository.save(application);

                // Send email to both the company and admin email
                sendJobApplicationEmail(companyEmail, fullname, emailaddress, phonenumber, uploadPath);
                sendJobApplicationEmail("msalman90826@gmail.com", fullname, emailaddress, phonenumber, uploadPath);

                model.addAttribute("successMessage", "Your application has been submitted successfully!");
                return "success";
            } else {
                model.addAttribute("errorMessage", "Company not found.");
                return "error";
            }

        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "Error occurred while saving your application. Please try again.");
            return "error";
        }
    }
    
    // Success page
    @GetMapping("/jobsuccess")
    public String showJobSuccessPage() {
        return "success"; // Returns success.jsp
    }


    private void sendJobApplicationEmail(String recipientEmail, String applicantName, String applicantEmail, String applicantPhone, Path resumePath) {
        try {
            // Create a MimeMessage
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            // Set email details
            helper.setTo(recipientEmail);
            helper.setSubject("New Job Application Received");
            helper.setText("Dear Hiring Manager,"
                    + "\n\nA new job application has been received. Please find the details below:"
                    + "\n\nApplicant Name: " + applicantName
                    + "\nEmail: " + applicantEmail
                    + "\nPhone: " + applicantPhone
                    + "\n\nThe resume is attached for your reference."
                    + "\n\nBest Regards,"
                    + "\nYour Application System");

            // Attach the file (PDF resume)
            File resumeFile = resumePath.toFile();
            helper.addAttachment(resumeFile.getName(), resumeFile);

            // Send the email
            mailSender.send(message);

            System.out.println("Email with attachment sent successfully!");

        } catch (MessagingException e) {
            e.printStackTrace();
            System.err.println("Failed to send email: " + e.getMessage());
        }
    }

}
