package com.shc.alumni.springboot.controller;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.shc.alumni.springboot.entity.AdminEntity;
import com.shc.alumni.springboot.entity.AlumniRegisterEntity;
import com.shc.alumni.springboot.repository.AdminRepository;
import com.shc.alumni.springboot.service.AlumniRegisterService;

@Controller
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private AlumniRegisterService alumniRegisterService;

    @Autowired
    private AdminRepository adminRepository;

    @GetMapping("/")
    public String showAddUserForm() {
        return "alumnilogin";
    }

    @PostMapping("/")
    public ResponseEntity<?> login(@RequestBody Map<String, String> payload, HttpSession session, HttpServletRequest request) {
        logger.debug("Payload received: {}", payload);

        String role = payload.get("role");
        String emailOrPhone = payload.get("emailaddressOrphoneno");

        if (role == null || emailOrPhone == null || emailOrPhone.trim().isEmpty()) {
            logger.warn("Missing role or email/phone: role={}, emailOrPhone={}", role, emailOrPhone);
            return ResponseEntity.badRequest()
                    .body(Map.of("success", false, "message", "Role and Email/Phone are required"));
        }

        try {
            String contextPath = request.getContextPath();
            if ("admin".equalsIgnoreCase(role)) {
                return handleAdminLogin(payload, session, emailOrPhone, contextPath);
            } else if ("alumni".equalsIgnoreCase(role)) {
                return handleAlumniLogin(payload, session, emailOrPhone, contextPath);
            } else {
                logger.warn("Invalid role: {}", role);
                return ResponseEntity.badRequest()
                        .body(Map.of("success", false, "message", "Invalid role selected"));
            }
        } catch (Exception e) {
            logger.error("Login error for role {} and email/phone {}: {}", role, emailOrPhone, e.getMessage(), e);
            return ResponseEntity.internalServerError()
                    .body(Map.of("success", false, "message", "An error occurred: " + e.getMessage()));
        }
    }

    private ResponseEntity<?> handleAdminLogin(Map<String, String> payload, HttpSession session, String emailAddress, String contextPath) {
        String password = payload.get("password");
        if (password == null || password.trim().isEmpty()) {
            logger.warn("Password missing for admin login: email={}", emailAddress);
            return ResponseEntity.badRequest()
                    .body(Map.of("success", false, "message", "Password is required for admin login"));
        }

        AdminEntity adminEntity = adminRepository.findByEmailAddress(emailAddress).orElse(null);
        if (adminEntity != null && password.equals(adminEntity.getPassword())) {
            session.setAttribute("loggedInUser", adminEntity);
            session.setAttribute("role", "admin");
            logger.info("Admin login successful: email={}", emailAddress);
            return ResponseEntity.ok(Map.of("success", true, "redirectUrl", contextPath + "/admin/adminhome"));
        } else {
            logger.warn("Invalid admin credentials: email={}", emailAddress);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("success", false, "message", "Invalid admin credentials"));
        }
    }

    private ResponseEntity<?> handleAlumniLogin(Map<String, String> payload, HttpSession session, String emailOrPhone, String contextPath) {
        String dob = payload.get("dob");
        if (dob == null || dob.trim().isEmpty()) {
            logger.warn("DOB missing for alumni login: emailOrPhone={}", emailOrPhone);
            return ResponseEntity.badRequest()
                    .body(Map.of("success", false, "message", "Date of Birth is required for alumni login"));
        }

        try {
            LocalDate parsedDob = LocalDate.parse(dob);
            AlumniRegisterEntity alumni = alumniRegisterService.findByEmailaddressOrPhonenoAndDob(emailOrPhone, parsedDob);

            if (alumni != null) {
                session.setAttribute("loggedInUser", alumni);
                session.setAttribute("role", "alumni");
                logger.info("Alumni login successful: emailOrPhone={}", emailOrPhone);
                return ResponseEntity.ok(Map.of("success", true, "redirectUrl", contextPath + "/home"));
            } else {
                logger.warn("No alumni found for credentials: emailOrPhone={}, dob={}", emailOrPhone, dob);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("success", false, "message", "Invalid credentials. Check phone number or email and DOB."));
            }
        } catch (DateTimeParseException e) {
            logger.warn("Invalid date format for DOB: dob={}, emailOrPhone={}", dob, emailOrPhone);
            return ResponseEntity.badRequest()
                    .body(Map.of("success", false, "message", "Invalid date format. Use YYYY-MM-DD"));
        }
    }
}