package com.shc.alumni.springboot.service;

import java.util.List;

import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;

import com.shc.alumni.springboot.entity.CompanyEntity;
//import com.shc.alumni.springboot.entity.StoryEntity;
import com.shc.alumni.springboot.repository.CompanyRepository;

//import java.io.IOException;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

   
    public void saveCompanyEntity(CompanyEntity company) {
        companyRepository.save(company); // Save the entity to the database
    }
    
    public List<CompanyEntity> findAll() {
        return companyRepository.findAll();
    }
    
    public CompanyEntity getCompanyById(Long id) {
        return companyRepository.findById(id).orElse(null);
    }
    
    


}
