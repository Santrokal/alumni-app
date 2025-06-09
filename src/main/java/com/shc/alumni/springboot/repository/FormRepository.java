package com.shc.alumni.springboot.repository;

import com.shc.alumni.springboot.entity.FormField;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormRepository extends JpaRepository<FormField, Long> {
}