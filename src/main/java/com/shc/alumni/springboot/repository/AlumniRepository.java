package com.shc.alumni.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shc.alumni.springboot.entity.Alumni;

public interface AlumniRepository extends JpaRepository<Alumni, String> {

}
