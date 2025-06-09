package com.shc.alumni.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shc.alumni.springboot.entity.AlumniJobApplications;

public interface AlumniJobApplicationsRepository extends JpaRepository<AlumniJobApplications, Long> {

}
