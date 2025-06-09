package com.shc.alumni.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shc.alumni.springboot.entity.AlumniRegisterEntity;
import com.shc.alumni.springboot.service.AlumniRegisterService;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
public class AlumniDirectoryController {

    @Autowired
    private AlumniRegisterService alumniRegisterService;

    private static final Logger logger = LoggerFactory.getLogger(AlumniDirectoryController.class);

    @GetMapping("/directory")
    public String getAlumniDirectory(Model model) {
        List<AlumniRegisterEntity> alumniList = alumniRegisterService.findAll();
        model.addAttribute("alumniList", alumniList);

        alumniList.forEach(alumni -> logger.info("Final FilePath: " + alumni.getFilePath()));
        return "directory"; // Returns the JSP view name
    }

    @GetMapping("/directory/image/{emailaddress}")
    public ResponseEntity<Resource> getAlumniImage(@PathVariable String emailaddress) {
        AlumniRegisterEntity alumni = alumniRegisterService.findByEmail(emailaddress);
        if (alumni == null || alumni.getFilePath() == null || alumni.getFilePath().isEmpty()) {
            logger.error("Image not found for email: " + emailaddress);
            return ResponseEntity.notFound().build();
        }

        try {
            Path filePath = Paths.get(alumni.getFilePath()).toAbsolutePath();
            logger.info("Fetching image from path: " + filePath.toString());

            if (!Files.exists(filePath) || !Files.isReadable(filePath)) {
                logger.error("Image not found or not readable: " + filePath.toString());
                return ResponseEntity.notFound().build();
            }

            Resource resource = new UrlResource(filePath.toUri());
            String mimeType = Files.probeContentType(filePath);
            if (mimeType == null) {
                mimeType = "application/octet-stream";
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(mimeType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filePath.getFileName() + "\"")
                    .body(resource);
        } catch (Exception e) {
            logger.error("Error fetching image for email: " + emailaddress, e);
            return ResponseEntity.badRequest().build();
        }
    }
}
