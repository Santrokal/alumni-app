package com.shc.alumni.springboot.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shc.alumni.springboot.entity.AdminEntity;

public interface AdminRepository extends JpaRepository<AdminEntity, Long> {
	
	Optional<AdminEntity> findByEmailAddress(String emailAddress);
	


}
