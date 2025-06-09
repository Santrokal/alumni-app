package com.shc.alumni.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shc.alumni.springboot.model.Contact;
import com.shc.alumni.springboot.service.ContactService;

@Controller
@RequestMapping("/") // Optional, adds a base path for all endpoints in this controller
public class ContactController {

    @Autowired
    private ContactService contactService;

    /**
     * Endpoint to handle contact form submissions
     */
    @GetMapping("/contactus")
    public String showContactForm() {
        return "alumnicontactus"; // Returns alumniregister.jsp
    }
    
    @PostMapping("/contactus")
    public ResponseEntity<Object> addContact(@RequestBody String rawRequest) {
        System.out.println("Raw Request: " + rawRequest);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Contact contact = objectMapper.readValue(rawRequest, Contact.class);

            System.out.println("Received fullname: " + contact.getFullName());
            System.out.println("Received phoneNo: " + contact.getPhoneNo());
            System.out.println("Received emailAddress: " + contact.getEmailAddress());
            System.out.println("Received addinfo: " + contact.getAddinfo());

            contactService.saveContact(contact.getFullName(), contact.getPhoneNo(), contact.getEmailAddress(), contact.getAddinfo());
            return ResponseEntity.status(HttpStatus.CREATED).body("{\"message\": \"Thank you! Your message has been received!\"}");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"Error: Unable to submit your message. Please try again. " + e.getMessage() + "\"}");
        }
    }


}
