package com.shc.alumni.springboot.service;

import com.shc.alumni.springboot.entity.ContactEntity;
import com.shc.alumni.springboot.repository.ContactRepository;

//import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;
    
    @Autowired
    EmailService emailService;

    /**
     * Save contact details into the database.
     */
    // Save a new contact message
    public void saveContact(String fullName, String phoneNo, String emailAddress, String addinfo) {
        ContactEntity contact = new ContactEntity();
        contact.setFullName(fullName);
        contact.setPhoneNo(phoneNo);
        contact.setEmailAddress(emailAddress);
        contact.setAddinfo(addinfo);
        contact.setSkipped(false); // Default to false
        contactRepository.save(contact);
    }
    
 // Skip a message
    public void skipMessage(Long id) {
        ContactEntity contact = contactRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Message not found"));
        contactRepository.delete(contact);
    }

    public void respondToMessage(Long id, String responseText) {
        ContactEntity contact = contactRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Message not found"));

        emailService.sendEmail(contact.getEmailAddress(), "Response to Your Message", responseText);
    }

    // Fetch all messages that are NOT skipped
    public List<ContactEntity> getAllMessages() {
        List<ContactEntity> messages = contactRepository.findBySkippedFalse();
        System.out.println("Fetched Messages: " + messages); // Add this line to check the output
        return messages;
    }


    // Find a message by ID
    public Optional<ContactEntity> getMessageById(Long id) {
        return contactRepository.findById(id);
    }

}
