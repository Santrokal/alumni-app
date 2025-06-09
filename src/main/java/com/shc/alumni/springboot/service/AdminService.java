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
    public void registerAdmin(String fullName, String emailAddress, String password,
            String dob, String phoneNo, String imagePath) {
AdminEntity admin = new AdminEntity();
admin.setFullName(fullName);
admin.setEmailAddress(emailAddress);
admin.setPassword(password);
admin.setDob(LocalDate.parse(dob));
admin.setPhoneNo(phoneNo);
admin.setImagePath("photograph/" + imagePath);

adminRepository.save(admin);
}

}
