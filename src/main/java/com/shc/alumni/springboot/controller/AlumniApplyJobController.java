package com.shc.alumni.springboot.controller;

import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.shc.alumni.springboot.entity.CompanyEntity;
//import com.shc.alumni.springboot.entity.StoryEntity;
import com.shc.alumni.springboot.service.CompanyService;

@Controller
public class AlumniApplyJobController {
	
	@Autowired
	private CompanyService companyService;
	
	
	@GetMapping("/applyjob")
	public String getAllJob(Model model) {
	    // Fetch all job records
	    List<CompanyEntity> companiesList = companyService.findAll();

	    // Print out the createdAt for debugging (before sorting)
	    System.out.println("Before sorting:");
	    for (CompanyEntity company : companiesList) {
	        System.out.println(company.getPosition() + " - " + company.getCreatedAt());
	    }

	    // Sort the list based on createdAt in descending order (newest first)
	    companiesList.sort((company1, company2) -> {
	        if (company1.getCreatedAt() == null && company2.getCreatedAt() == null) {
	            return 0;
	        }
	        if (company1.getCreatedAt() == null) {
	            return 1; // Nulls are considered older
	        }
	        if (company2.getCreatedAt() == null) {
	            return -1; // Nulls are considered older
	        }
	        return company2.getCreatedAt().compareTo(company1.getCreatedAt());
	    });

	    // Print out the createdAt after sorting for debugging
	    System.out.println("After sorting:");
	    for (CompanyEntity company : companiesList) {
	        System.out.println(company.getPosition() + " - " + company.getCreatedAt());
	    }

	    // Convert images to Base64 if available
	    for (CompanyEntity company : companiesList) {
	        if (company.getImage() != null) {
	            String base64Image = Base64.getEncoder().encodeToString(company.getImage());
	            company.setImageBase64(base64Image); // Setting Base64 string for JSP use
	        }
	    }

	    // Add the company list to the model
	    model.addAttribute("companiesList", companiesList);

	    return "alumniapplyjoblist"; // Returns the JSP view name
	}

	
	

}
