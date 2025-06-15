package com.shc.alumni.springboot.service;

import com.shc.alumni.springboot.entity.MembershipEntity;
import com.shc.alumni.springboot.repository.MembershipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Year;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;


@Service
public class MembershipService {

    private static final Logger logger = LoggerFactory.getLogger(MembershipService.class);

    @Autowired
    private MembershipRepository membershipRepository;

    private final String merchantKey = "ycVsG4"; // Test key
    private final String salt = "j8sqTS5T54oQ4cv6bFdGShLwDGbSqPnX";

    @Value("${payu.test_url}")
    private String payuTestUrl = "https://test.payu.in/_payment";

    /**
     * Generate a member ID in format: YYYY_FULLNAME
     */
    public String generateMemberId(String fullName) {
        String year = String.valueOf(Year.now().getValue());
        String namePart = fullName.replaceAll("\\s+", "_").toUpperCase();
        return year + "_" + namePart;
    }

    /**
     * Initiate a PayU test mode payment
     */
    public Map<String, String> initiatePayment(int amount, String fullName, String phoneNumber) throws Exception {
        String transactionId = "txn_" + UUID.randomUUID().toString().replaceAll("-", "");

        Map<String, String> params = new HashMap<>();
        params.put("key", merchantKey);
        params.put("txnid", transactionId);
        params.put("amount", String.valueOf(amount));
        params.put("productinfo", "Membership Payment");
        params.put("firstname", fullName);
        params.put("email", "test@example.com");
        params.put("phone", phoneNumber);
        params.put("surl", "https://d7ca-2401-4900-1f2c-6615-74b5-998-9e8d-bb.ngrok-free.app/paymentSuccess");
        params.put("furl", "https://d7ca-2401-4900-1f2c-6615-74b5-998-9e8d-bb.ngrok-free.app/paymentFailure");

        String hash = generatePayUHash(merchantKey, transactionId, String.valueOf(amount), "Membership Payment",
                                       fullName, "test@example.com");
        params.put("hash", hash);

        logger.info("Generated PayU Params: {}", params);
        return params;
    }

    /**
     * Generate PayU hash for secure transaction
     */
    private String generatePayUHash(String key, String txnid, String amount, String productinfo, 
                                    String firstname, String email) throws Exception {
        String hashString = key + "|" + txnid + "|" + amount + "|" + productinfo + "|" + 
                           firstname + "|" + email + "|||||||||||" + salt;
        logger.info("Hash String for initiation: {}", hashString);
        MessageDigest digest = MessageDigest.getInstance("SHA-512");
        byte[] hashBytes = digest.digest(hashString.getBytes(StandardCharsets.UTF_8));
        return bytesToHex(hashBytes).toLowerCase();
    }

    /**
     * Verify PayU payment response (bypassed in test mode).
     */
    public boolean verifyPayment(Map<String, String> params) {
        logger.info("Bypassing payment verification in test mode: {}", params);
        return true; // Trust status=success in test mode
    }

    /**
     * Save membership details after successful payment
     */
    public MembershipEntity saveMembershipDetails(String fullName, String phoneNumber, String paymentId, String memberId) {
        MembershipEntity membership = new MembershipEntity();
        membership.setMember_id(memberId);
        membership.setPaymentId(paymentId);
        membership.setPhoneNumber(phoneNumber);
        membership.setFullname(fullName);
        membership.setCreatedAt(LocalDateTime.now());

        return membershipRepository.save(membership);
    }

    /**
     * Fetch membership by payment ID
     */
    public Optional<MembershipEntity> getMembershipByPaymentId(String paymentId) {
        return membershipRepository.findByPaymentId(paymentId);
    }

    private String generateSha512Hash(String input) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-512");
        byte[] hashBytes = digest.digest(input.getBytes(StandardCharsets.UTF_8));
        return bytesToHex(hashBytes).toLowerCase();
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}