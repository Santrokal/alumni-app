package com.shc.alumni.springboot.controller;

import com.shc.alumni.springboot.entity.AlumniRegisterEntity;
import com.shc.alumni.springboot.entity.FormField;
import com.shc.alumni.springboot.service.AgmMembershipService;
import com.shc.alumni.springboot.service.FormService;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
public class AlumniController {

    private static final Logger logger = LoggerFactory.getLogger(AlumniController.class);

    @Autowired
    private FormService formService;

    @Autowired
    private AgmMembershipService agmMembershipService;

    @GetMapping("/agm-form")
    public String showAgmForm(Model model, HttpSession session) {
        AlumniRegisterEntity alumni = (AlumniRegisterEntity) session.getAttribute("loggedInUser");
        if (alumni == null || !"alumni".equals(session.getAttribute("role"))) {
            logger.warn("No valid alumni session found. Redirecting to login.");
            return "redirect:/";
        }

        String phoneNumber = alumni.getPhoneno();
        logger.debug("Processing AGM form request for phoneNumber: {}", phoneNumber);

        if (!formService.isAgmFormActive()) {
            logger.info("AGM form is not active.");
            model.addAttribute("error", "AGM form is not currently available.");
            return "agmerror";
        }

        try {
            boolean hasResponded = formService.hasResponse(phoneNumber);
            model.addAttribute("hasResponded", hasResponded);
            if (!hasResponded) {
                List<FormField> fields = formService.getFormFields();
                if (fields == null || fields.isEmpty()) {
                    logger.warn("No form fields found for AGM form.");
                    model.addAttribute("error", "AGM form is not properly configured. No fields available.");
                    return "agmerror";
                }
                model.addAttribute("fields", fields);
            }
            model.addAttribute("phoneNumber", phoneNumber);
            return "agmForm";
        } catch (Exception e) {
            logger.error("Error processing AGM form for phoneNumber: {}", phoneNumber, e);
            model.addAttribute("error", "An error occurred while loading the AGM form: " + e.getMessage());
            return "agmerror";
        }
    }

    @PostMapping("/agm-form")
    public String submitAgmForm(@RequestParam String formData, HttpSession session, Model model) throws Exception {
        AlumniRegisterEntity alumni = (AlumniRegisterEntity) session.getAttribute("loggedInUser");
        if (alumni == null || !"alumni".equals(session.getAttribute("role"))) {
            logger.warn("No valid alumni session for form submission. Redirecting to login.");
            return "redirect:/";
        }

        String phoneNumber = alumni.getPhoneno();
        if (formService.hasResponse(phoneNumber)) {
            logger.info("Duplicate submission attempt for phoneNumber: {}. Redirecting to success.", phoneNumber);
            return "agmsuccess";
        }

        JSONObject responseData = new JSONObject(formData);
        String fullName = alumni.getFullname();

        if (agmMembershipService.hasMembership(phoneNumber)) {
            logger.info("Member submitting AGM form for free: {}", phoneNumber);
            formService.saveResponse(phoneNumber, responseData);
            return "agmsuccess";
        } else {
            logger.info("Non-member initiating payment for AGM form: {}", phoneNumber);
            Map<String, String> paymentParams = agmMembershipService.initiateAgmPayment(150, fullName, phoneNumber);
            session.setAttribute("pendingResponse", responseData.toString());
            session.setAttribute("phoneNumber", phoneNumber);
            model.addAttribute("paymentParams", paymentParams);
            model.addAttribute("payuUrl", "https://test.payu.in/_payment");
            return "payment";
        }
    }

    @PostMapping("/payment-success")
    public String handlePaymentSuccess(@RequestParam Map<String, String> params, HttpSession session) {
        AlumniRegisterEntity alumni = (AlumniRegisterEntity) session.getAttribute("loggedInUser");
        if (alumni == null || !"alumni".equals(session.getAttribute("role"))) {
            logger.warn("No valid alumni session for payment success. Redirecting to login.");
            return "redirect:/";
        }

        String phoneNumber = (String) session.getAttribute("phoneNumber");
        if (agmMembershipService.verifyPayment(params)) {
            String pendingResponse = (String) session.getAttribute("pendingResponse");
            if (pendingResponse != null) {
                JSONObject responseData = new JSONObject(pendingResponse);
                formService.saveResponse(phoneNumber, responseData);

                String fullName = responseData.optString("Name", alumni.getFullname());
                String paymentId = params.get("txnid");
                agmMembershipService.saveAgmPaymentDetails(fullName, phoneNumber, paymentId);

                session.removeAttribute("pendingResponse");
                session.removeAttribute("phoneNumber");
                logger.info("Payment successful and response saved for phoneNumber: {}", phoneNumber);
            }
            return "agmsuccess";
        }
        logger.error("Payment verification failed for phoneNumber: {}", phoneNumber);
        return "agmerror";
    }

    @PostMapping("/payment-failure")
    public String handlePaymentFailure() {
        logger.warn("Payment failed.");
        return "agmerror";
    }

    @GetMapping("/admin/view-agm-responses")
    public String viewAgmResponses(Model model, HttpSession session) {
        if (!"admin".equals(session.getAttribute("role"))) {
            logger.warn("Unauthorized access to view AGM responses. Redirecting to login.");
            return "redirect:/";
        }

        try {
            List<FormField> fields = formService.getFormFields();
            if (fields == null || fields.isEmpty()) {
                model.addAttribute("error", "No form fields defined. Please create the AGM form first.");
                return "adminViewAgmResponses";
            }

            List<Object[]> responses = formService.getAgmResponses(fields);
            boolean isFormActive = formService.isAgmFormActive();
            model.addAttribute("isFormActive", isFormActive);
            model.addAttribute("fields", fields);
            model.addAttribute("responses", responses);
            return "adminViewAgmResponses";
        } catch (Exception e) {
            logger.error("Error fetching AGM responses", e);
            model.addAttribute("error", "An error occurred while fetching AGM responses: " + e.getMessage());
            return "adminViewAgmResponses";
        }
    }

    @GetMapping("/agm-responses")
    public String showAgmResponses(HttpSession session, Model model) {
        if (!"admin".equals(session.getAttribute("role"))) {
            logger.warn("Unauthorized attempt to access AGM responses. Redirecting to login.");
            return "redirect:/";
        }

        try {
            // Fetch data from FormService
            List<FormField> fields = formService.getFormFields(); // Assume this method exists
            List<Object[]> responses = formService.getAgmResponses(fields); // Assume this method exists
            Boolean isFormActive = formService.isAgmFormActive(); // Assume this method exists

            // Add data to the model for JSP
            model.addAttribute("fields", fields);
            model.addAttribute("responses", responses);
            model.addAttribute("isFormActive", isFormActive);

            // Store in session for export functionality (optional, depending on your design)
            session.setAttribute("fields", fields);
            session.setAttribute("responses", responses);

            logger.info("AGM responses page loaded successfully.");
            return "agm-responses"; // JSP name without .jsp extension
        } catch (Exception e) {
            logger.error("Error loading AGM responses", e);
            model.addAttribute("error", "An error occurred while loading AGM responses: " + e.getMessage());
            return "agm-responses"; // Still return the page, but with an error message
        }
    }

    @GetMapping("/admin/activate-agm-form")
    public String handleGetActivateAgmForm(Model model) {
        model.addAttribute("error", "Please use the form to activate the AGM form.");
        return "adminViewAgmResponses";
    }

}