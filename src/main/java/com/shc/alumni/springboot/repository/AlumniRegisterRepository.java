package com.shc.alumni.springboot.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
//import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.query.Param;

import com.shc.alumni.springboot.entity.AlumniRegisterEntity;

public interface AlumniRegisterRepository extends JpaRepository<AlumniRegisterEntity, Integer> {

    // Find alumni by email address and date of birth
    AlumniRegisterEntity findByEmailaddressAndDob(String emailaddress, LocalDate dob);

    // Check if email address or full name exists in the database
    boolean existsByEmailaddressOrFullname(String emailaddress, String fullname);
    

    // Find alumni by email address
    AlumniRegisterEntity findByEmailaddress(String emailaddress);
    AlumniRegisterEntity findByPhoneno(String phoneno);
    List<AlumniRegisterEntity> findAllByPhoneno(String phoneno); // Added to handle duplicates
    AlumniRegisterEntity findByFullname(String fullname);
    
    List<AlumniRegisterEntity> findAll();

    //  Fixed method name: More meaningful and readable
    @Query("SELECT a FROM AlumniRegisterEntity a WHERE a.shcStayTo = :year")
    List<AlumniRegisterEntity> findByShcStayTo(@Param("year") String year);
    
    AlumniRegisterEntity findByEmailaddressOrPhonenoAndDob(String emailaddresorphoneno , String emailaddressOrphoneno, LocalDate dob);

}
