package com.shc.alumni.springboot.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shc.alumni.springboot.entity.MembershipEntity;

@Repository
public interface MembershipRepository extends JpaRepository<MembershipEntity, Long> {
    Optional<MembershipEntity> findByPaymentId(String paymentId);  // Use 'paymentId' here
    Optional<MembershipEntity> findByPhoneNumber(String phoneNumber);
    boolean existsByPhoneNumber(String phoneNumber);
    //MembershipEntity findByPhoneNumber(String phoneNumber);
}
