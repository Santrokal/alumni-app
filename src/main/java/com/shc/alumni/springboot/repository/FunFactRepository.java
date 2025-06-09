package com.shc.alumni.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shc.alumni.springboot.entity.FunFact;

public interface FunFactRepository extends JpaRepository<FunFact, Long> {

}
