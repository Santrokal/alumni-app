package com.shc.alumni.springboot.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartFile;

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

   
    @Transactional
    public void saveCompanyEntity(CompanyEntity company, MultipartFile fileUpload, HttpServletRequest request) throws IOException {
        if (fileUpload != null && !fileUpload.isEmpty()) {
            String basePath = request.getServletContext().getRealPath("/WEB-INF/company_folder");
            Path folderPath = Paths.get(basePath);

            if (!Files.exists(folderPath)) {
                Files.createDirectories(folderPath);
            }

            String fileName = System.currentTimeMillis() + "_" + fileUpload.getOriginalFilename();
            Path filePath = folderPath.resolve(fileName);
            Files.write(filePath, fileUpload.getBytes());

            company.setFilePath(fileName); // just filename
            company.setFileData(fileUpload.getBytes()); // optional, in case you still need blob
        } else {
            company.setFilePath(null);
        }

        company.setCreatedAt(LocalDateTime.now());
        companyRepository.save(company);
    }


    
    public List<CompanyEntity> findAll() {
        return companyRepository.findAll();
    }
    
    public CompanyEntity getCompanyById(Long id) {
        return companyRepository.findById(id).orElse(null);
    }
    
    


}
