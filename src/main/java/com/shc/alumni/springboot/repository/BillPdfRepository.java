package com.shc.alumni.springboot.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shc.alumni.springboot.entity.BillPdfEntity;

public interface BillPdfRepository extends JpaRepository<BillPdfEntity, String> {
	Optional<BillPdfEntity> findByPhoneNumber(String phoneNumber);
}
