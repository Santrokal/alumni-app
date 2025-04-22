package com.shc.alumni.springboot.service;

import com.shc.alumni.springboot.entity.AdminEntity;
import com.shc.alumni.springboot.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    public AdminEntity registerAdmin(String emailAddress, String password) {
        AdminEntity adminEntity = new AdminEntity();
        adminEntity.setEmailAddress(emailAddress);
        adminEntity.setPassword(password); // Store the password as plain text
        return adminRepository.save(adminEntity);
    }

    public boolean emailExists(String emailAddress) {
        return adminRepository.findByEmailAddress(emailAddress).isPresent();
    }

    
    // Register AdminEntity with all details
    public void registerAdmin(String fullName, String emailAddress, String password, String dob, String phoneNo, byte[] image) {
        AdminEntity adminEntity = new AdminEntity();
        adminEntity.setFullName(fullName);
        adminEntity.setEmailAddress(emailAddress);
        adminEntity.setPassword(password); // Store password securely in a real-world application
        adminEntity.setDob(LocalDate.parse(dob)); // Assuming dob format is 'yyyy-MM-dd'
        adminEntity.setPhoneNo(phoneNo);
        adminEntity.setImage(image); // Storing image as byte array
        adminRepository.save(adminEntity);
    }
}
