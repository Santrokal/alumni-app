package com.shc.alumni.springboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.itextpdf.io.exceptions.IOException;
import com.shc.alumni.springboot.entity.BillPdfEntity;
import com.shc.alumni.springboot.repository.BillPdfRepository;

//import ch.qos.logback.core.model.Model;

import javax.servlet.http.HttpServletResponse;

@RestController
public class BillingController {

    @Autowired
    private BillPdfRepository billPdfRepository;

    // Endpoint to retrieve the PDF
    @GetMapping("/downloadBill")
    public ResponseEntity<byte[]> downloadBill(@RequestParam String phoneNumber) {
        // Fetch the BillPdfEntity from the database using emailAddress
        BillPdfEntity billPdfEntity = billPdfRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new RuntimeException("No bill found for Phone Number: " + phoneNumber));

        // Retrieve the PDF byte array
        byte[] billPdf = billPdfEntity.getPdfData();

        // Set response headers for downloading the PDF
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + billPdfEntity.getId() + ".pdf");
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE);

        // Return the PDF as a response entity
        return ResponseEntity.ok()
                .headers(headers)
                .body(billPdf);
    }
    
    
    
    

}

