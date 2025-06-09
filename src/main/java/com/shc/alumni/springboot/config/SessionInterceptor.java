package com.shc.alumni.springboot.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class SessionInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        String method = request.getMethod();

        // Allow all OPTIONS requests for CORS pre-flight
        if ("OPTIONS".equalsIgnoreCase(method)) {
            response.setStatus(HttpServletResponse.SC_OK);
            return true;
        }

        // Allow login, registration, payment endpoints & static resources
        if (requestURI.equals("/") || 
            requestURI.equals("/register") ||
            requestURI.equals("/") ||
            requestURI.equals("/membership") || 
            requestURI.equals("/verifyMembership") || 
            requestURI.equals("/createMembership") || 
            requestURI.equals("/paymentSuccess") || 
            requestURI.equals("/paymentFailure") || 
            requestURI.equals("/success") || 
            requestURI.startsWith("/css") || 
            requestURI.startsWith("/js") || 
            requestURI.startsWith("/images") ||
            requestURI.startsWith("/admin/register") ||
            requestURI.startsWith("/assets")) {
            return true;
        }

        // Redirect if user is not logged in
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loggedInUser") == null) {
            response.sendRedirect("/");
            return false;
        }

        return true;
    }
}