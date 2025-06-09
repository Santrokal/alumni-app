package com.shc.alumni.springboot.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/alumni")
public class AlumniErrorController {

    private static final Logger logger = LoggerFactory.getLogger(AlumniErrorController.class);

    @GetMapping("/error")
    public String handleAlumniError(HttpServletRequest request, Model model) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        String errorMessage = (String) request.getAttribute("javax.servlet.error.message");
        String requestUri = (String) request.getAttribute("javax.servlet.error.request_uri");

        if (statusCode == null) {
            statusCode = 500;
        }
        if (errorMessage == null || errorMessage.isEmpty()) {
            errorMessage = statusCode == 404 ? 
                "The requested alumni page was not found." : 
                "An unexpected error occurred in alumni portal.";
        }
        if (requestUri == null) {
            requestUri = "Unknown";
        }

        logger.error("Alumni Error: Status={}, Message={}, URI={}", statusCode, errorMessage, requestUri);

        model.addAttribute("statusCode", statusCode);
        model.addAttribute("errorMessage", errorMessage);
        model.addAttribute("requestUri", requestUri);
        model.addAttribute("timestamp", new java.util.Date());

        return "alumni/error";
    }
}
