package com.shc.alumni.springboot.controller;

import com.shc.alumni.springboot.entity.BillPdfEntity;
import com.shc.alumni.springboot.entity.MembershipEntity;
import com.shc.alumni.springboot.repository.BillPdfRepository;
import com.shc.alumni.springboot.repository.MembershipRepository;
import com.shc.alumni.springboot.service.BillingService;
import com.shc.alumni.springboot.service.MembershipService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Controller

public class MembershipController {

    private static final Logger logger = LoggerFactory.getLogger(MembershipController.class);

    @Autowired
    private MembershipService membershipService;

    private final BillingService billingService;

    @Autowired
    public MembershipController(BillingService billingService) {
        this.billingService = billingService;
    }

    @Autowired
    private MembershipRepository membershipRepository;

    @Autowired
    private BillPdfRepository billPdfRepository;

    /**
     * Display the membership page.
     */
    @GetMapping("/membership")
    public String showMembershipPage() {
        return "membershipFlow";
    }
    
    @GetMapping("/paymentSuccess")
    public String showPaymentsuccessPage() {
        return "paymentsuccess";
    }
    
    @GetMapping("/paymentFailure")
    public String showPaymentfailurePage() {
        return "paymentfailure";
    }

    /**
     * Handle membership creation and initiate PayU payment.
     */
   
   
    
    @PostMapping("/createMembership")
    public String createMembership(
            @RequestParam String fullName,
            @RequestParam String phoneNumber,
            Model model) {
        try {
            String memberId = membershipService.generateMemberId(fullName);
            if (memberId == null || memberId.isEmpty()) {
                logger.error("Generated Member ID is null or empty for fullName: {}", fullName);
                model.addAttribute("errorMessage", "Failed to generate Member ID.");
                return "membershipFlow";
            }

            Map<String, String> payuParams = membershipService.initiatePayment(1500, fullName, phoneNumber);
            model.addAllAttributes(payuParams);
            model.addAttribute("merchantKey", payuParams.get("key"));

            logger.info("PayU Payment Params: {}", payuParams);
            return "payuCheckout";
        } catch (Exception e) {
            logger.error("Error during membership creation: ", e);
            model.addAttribute("errorMessage", "Error during membership creation: " + e.getMessage());
            return "membershipFlow";
        }
    }

    /**
     * Handle payment success callback from PayU.
     */
    

    @PostMapping("/paymentSuccess")
    public String paymentSuccess(
            @RequestParam(required = false) String mihpayid,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String firstname,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String hash,
            @RequestParam(required = false) String txnid,
            @RequestParam Map<String, String> allParams,
            Model model) {
        logger.info("Received paymentSuccess callback with params: {}", allParams);
        try {
            // For local testing, you might want to relax this check
            // since PayU won't actually callback to localhost
            if (mihpayid == null || mihpayid.trim().isEmpty() || !"success".equalsIgnoreCase(status)) {
                logger.error("Invalid payment response: mihpayid={}, status={}", mihpayid, status);
                model.addAttribute("errorMessage", "Payment failed or invalid response from PayU.");
                return "paymentfailure";
            }

            if (firstname == null || firstname.trim().isEmpty() || phone == null || phone.trim().isEmpty()) {
                logger.error("Missing user details: firstname={}, phone={}", firstname, phone);
                model.addAttribute("errorMessage", "Invalid user details.");
                return "paymentfailure";
            }

            boolean isPaymentValid = membershipService.verifyPayment(allParams);
            if (!isPaymentValid) {
                logger.error("Payment verification failed for Payment ID: {}", mihpayid);
                model.addAttribute("errorMessage", "Payment verification failed.");
                return "paymentfailure";
            }

            String memberId = membershipService.generateMemberId(firstname);
            if (memberId == null || memberId.isEmpty()) {
                logger.error("Failed to generate memberId for firstname: {}", firstname);
                model.addAttribute("errorMessage", "Error generating membership ID.");
                return "paymentfailure";
            }
            logger.info("Generated Member ID: {}", memberId);

            MembershipEntity membership = membershipService.saveMembershipDetails(firstname, phone, mihpayid, memberId);
            if (membership == null) {
                logger.error("Failed to save MembershipEntity for memberId: {}", memberId);
                model.addAttribute("errorMessage", "Failed to save membership details.");
                return "paymentfailure";
            }
            logger.info("Membership details saved: {}", membership);

            byte[] billPdf = billingService.generateBillPdf(memberId, firstname, phone, mihpayid);
            if (billPdf == null || billPdf.length == 0) {
                logger.error("Failed to generate Bill PDF for memberId: {}", memberId);
                model.addAttribute("errorMessage", "Error generating the bill PDF.");
                return "paymentfailure";
            }

            String directoryPath = "membership";
            Path path = Paths.get(directoryPath);
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }
            String filePath = directoryPath + "/" + memberId + "_bill.pdf";
            try (FileOutputStream fos = new FileOutputStream(filePath)) {
                fos.write(billPdf);
                logger.info("Bill PDF saved to disk: {}", filePath);
            }

            BillPdfEntity billPdfEntity = new BillPdfEntity();
            billPdfEntity.setId(UUID.randomUUID().toString());
            billPdfEntity.setMemberId(memberId);
            billPdfEntity.setFullName(firstname);
            billPdfEntity.setPhoneNumber(phone);
            billPdfEntity.setPaymentId(mihpayid);
            billPdfEntity.setStatus("PAID");
            billPdfEntity.setPdfData(billPdf);
            billPdfEntity.setDate(new Date());

            billPdfRepository.save(billPdfEntity);
            logger.info("Bill PDF stored in database: {}", billPdfEntity);

            model.addAttribute("memberId", memberId);
            model.addAttribute("fullName", firstname);
            model.addAttribute("phoneNumber", phone);
            model.addAttribute("paymentId", mihpayid);

            logger.info("Payment success completed, returning paymentsuccess");
            return "paymentsuccess";
        } catch (Exception e) {
            logger.error("Error in paymentSuccess: ", e);
            model.addAttribute("errorMessage", "Payment processing failed: " + e.getMessage());
            return "paymentfailure";
        }
    }

    @PostMapping("/paymentFailure")
    public String paymentFailure(
            @RequestParam(required = false) String mihpayid,
            @RequestParam(required = false) String status,
            @RequestParam Map<String, String> allParams,
            Model model) {
        logger.info("Payment Failure Response: {}", allParams);
        logger.error("Payment failed - Payment ID: {}, Status: {}", mihpayid, status);
        model.addAttribute("errorMessage", "Payment failed. Please try again or contact support.");
        return "paymentfailure";
    }
    /**
     * Verify if a membership exists for the given phone number.
     */
    @PostMapping("/verifyMembership")
    public String verifyMembership(@RequestParam String phoneNumber, Model model) {
        try {
            Optional<MembershipEntity> membership = membershipRepository.findByPhoneNumber(phoneNumber);

            if (membership.isPresent()) {
                MembershipEntity member = membership.get();
                model.addAttribute("message", "Membership found!");
                model.addAttribute("fullName", member.getFullname());
                model.addAttribute("memberId", member.getMember_id());
                model.addAttribute("emailAddress", member.getPhoneNumber());
                model.addAttribute("createdAt", member.getCreatedAt());

                return "membershipDetails"; // A view to show membership details
            } else {
                model.addAttribute("errorMessage", "No membership found for the given Phone Number.");
                return "membershipFlow"; // Redirect back to the membership form
            }
        } catch (Exception e) {
            logger.error("Error during membership verification: ", e);
            model.addAttribute("errorMessage", "Error during membership verification.");
            return "membershipFlow";
        }
    }

    /**
     * Fetch all membership details and display on JSP.
     */
    @GetMapping("/membership-details")
    public String showMembershipDetails(HttpServletRequest request, Model model) {
        List<BillPdfEntity> bills = billPdfRepository.findAll();

        System.out.println("✅ Found " + bills.size() + " membership records.");
        for (BillPdfEntity bill : bills) {
            System.out.println("📝 Member ID: " + bill.getMemberId() + ", Status: " + bill.getStatus());
        }

        request.setAttribute("bills", bills); // Add to request scope for JSP
        model.addAttribute("bills", bills);   // Add to model (optional)

        return "membershipDetails"; // Ensure this matches the JSP filename
    }

    /**
     * Serve the PDF for download.
     */
    @GetMapping("/download-bill/{id}")
    public void downloadBill(@PathVariable String id, HttpServletResponse response) throws IOException {
        BillPdfEntity bill = billPdfRepository.findById(id).orElse(null);
        if (bill != null && bill.getPdfData() != null) {
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=" + bill.getMemberId() + "_bill.pdf");
            response.getOutputStream().write(bill.getPdfData());
            response.getOutputStream().flush();
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Bill not found");
        }
    }
}