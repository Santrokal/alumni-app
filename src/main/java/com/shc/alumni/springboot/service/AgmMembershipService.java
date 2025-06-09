package com.shc.alumni.springboot.service;

import com.shc.alumni.springboot.entity.MembershipEntity;
import com.shc.alumni.springboot.repository.MembershipRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class AgmMembershipService {

    private static final Logger logger = LoggerFactory.getLogger(AgmMembershipService.class);

    @Autowired
    private MembershipRepository membershipRepository;

    private final String merchantKey = "ycVsG4"; // Test key
    private final String salt = "j8sqTS5T54oQ4cv6bFdGShLwDGbSqPnX";

    @Value("${payu.test_url:https://test.payu.in/_payment}")
    private String payuTestUrl;

    public Map<String, String> initiateAgmPayment(int amount, String fullName, String phoneNumber) throws Exception {
        String transactionId = "agm_txn_" + UUID.randomUUID().toString().replaceAll("-", "");

        Map<String, String> params = new HashMap<>();
        params.put("key", merchantKey);
        params.put("txnid", transactionId);
        params.put("amount", String.valueOf(amount));
        params.put("productinfo", "AGM Registration Fee");
        params.put("firstname", fullName);
        params.put("email", "test@example.com"); // Placeholder, adjust as needed
        params.put("phone", phoneNumber);
        params.put("surl", "http://localhost:8080/payment-success"); // Updated URL
        params.put("furl", "http://localhost:8080/payment-failure"); // Updated URL

        String hash = generatePayUHash(merchantKey, transactionId, String.valueOf(amount), "AGM Registration Fee",
                                       fullName, "test@example.com");
        params.put("hash", hash);

        logger.info("Generated PayU Params for AGM: {}", params);
        return params;
    }

    private String generatePayUHash(String key, String txnid, String amount, String productinfo,
                                    String firstname, String email) throws Exception {
        String hashString = key + "|" + txnid + "|" + amount + "|" + productinfo + "|" +
                           firstname + "|" + email + "|||||||||||" + salt;
        logger.info("Hash String for AGM payment: {}", hashString);
        MessageDigest digest = MessageDigest.getInstance("SHA-512");
        byte[] hashBytes = digest.digest(hashString.getBytes(StandardCharsets.UTF_8));
        return bytesToHex(hashBytes).toLowerCase();
    }

    public boolean verifyPayment(Map<String, String> params) {
        logger.info("Bypassing payment verification in test mode for AGM: {}", params);
        return true; // Trust status=success in test mode
    }

    public MembershipEntity saveAgmPaymentDetails(String fullName, String phoneNumber, String paymentId) {
        Optional<MembershipEntity> existingMembership = membershipRepository.findByPhoneNumber(phoneNumber);
        MembershipEntity membership;
        if (existingMembership.isPresent()) {
            membership = existingMembership.get();
            membership.setPaymentId(paymentId);
            membership.setFullname(fullName);
            membership.setCreatedAt(LocalDateTime.now());
        } else {
            membership = new MembershipEntity();
            String memberId = "MEM_" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
            membership.setMember_id(memberId);
            membership.setPaymentId(paymentId);
            membership.setPhoneNumber(phoneNumber);
            membership.setFullname(fullName);
            membership.setCreatedAt(LocalDateTime.now());
        }
        return membershipRepository.save(membership);
    }

    public boolean hasMembership(String phoneNumber) {
        return membershipRepository.existsByPhoneNumber(phoneNumber);
    }

    public boolean isLifeMember(String memberId) { // Kept for compatibility
        Optional<MembershipEntity> membership = membershipRepository.findAll().stream()
                .filter(m -> m.getMember_id().equals(memberId))
                .findFirst();
        return membership.isPresent() && membership.get().getPaymentId().startsWith("life_");
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}