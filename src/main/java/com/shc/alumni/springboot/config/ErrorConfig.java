package com.shc.alumni.springboot.config;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ErrorConfig implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
        String requestUri = (String) request.getAttribute("javax.servlet.error.request_uri");
        if (requestUri != null && requestUri.startsWith("/admin")) {
            return "forward:/admin/error";
        }
        return "forward:/alumni/error";
    }
}
