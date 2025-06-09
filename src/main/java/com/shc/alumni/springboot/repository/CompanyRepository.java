package com.shc.alumni.springboot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shc.alumni.springboot.entity.CompanyEntity;

public interface CompanyRepository extends JpaRepository<CompanyEntity, Long> {

	
	List<CompanyEntity> findAll();
}
