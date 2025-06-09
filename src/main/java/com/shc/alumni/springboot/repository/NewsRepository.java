package com.shc.alumni.springboot.repository;


import com.shc.alumni.springboot.entity.News;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
	List<News> findAll();
    List<News> findTop5ByOrderByCreatedAtDesc();
    
    

}

