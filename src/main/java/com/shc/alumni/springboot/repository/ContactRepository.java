package com.shc.alumni.springboot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shc.alumni.springboot.entity.ContactEntity;

public interface ContactRepository extends JpaRepository<ContactEntity, Long> {
	List<ContactEntity> findBySkippedFalse();

}
