package com.shc.alumni.springboot.service;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.shc.alumni.springboot.entity.AlumniRegisterEntity;
import com.shc.alumni.springboot.entity.StoryEntity;
import com.shc.alumni.springboot.repository.StoryRepository;

@Service
public class StoryService {

	@Autowired
	private StoryRepository storyRepository;

	public List<StoryEntity> findAll() {
		return storyRepository.findAll();
	}

	public List<StoryEntity> findTop5ByOrderByCreatedAtDesc() {
		return storyRepository.findTop5ByOrderByCreatedAtDesc();
	}

	public StoryEntity getStoryById(int id) {
		return storyRepository.findById((long) id).orElse(null);
	}

	public void deleteById(Long id) {
		if (storyRepository.existsById(id)) {
			storyRepository.deleteById(id);
		} else {
			throw new IllegalArgumentException("Story with ID " + id + " does not exist.");
		}
	}



	@Transactional
    public void saveStory(String title, String content, String linkedinProfile, boolean consentToPublish,
            String department, String organization, MultipartFile storyImage, HttpServletRequest request)
            throws IOException {

        StoryEntity story = new StoryEntity();
        story.setTitle(title);
        story.setContent(content);
        story.setLinkedinProfile(linkedinProfile);
        story.setConsentToPublish(consentToPublish);
        story.setDepartment(department);
        story.setOrganization(organization);
        story.setCreatedAt(LocalDateTime.now());

        if (storyImage != null && !storyImage.isEmpty()) {
            String filename = saveFileToFolder(storyImage, request);
            story.setStoryImagePath(filename);
        } else {
            System.out.println("No story image provided");
            story.setStoryImagePath(null);
        }

        try {
            storyRepository.save(story);
        } catch (Exception e) {
            throw new IOException("Failed to save story to database: " + e.getMessage(), e);
        }
    }

    private String saveFileToFolder(MultipartFile file, HttpServletRequest request) throws IOException {
        String basePath = request.getServletContext().getRealPath("/WEB-INF/story_folder");
        Path folderPath = Paths.get(basePath);

        try {
            if (!Files.exists(folderPath)) {
                Files.createDirectories(folderPath);
                System.out.println("Created directory: " + folderPath);
            }

            String originalFileName = file.getOriginalFilename();
            String uniqueFilename = System.currentTimeMillis() + "_" + originalFileName;
            Path filePath = folderPath.resolve(uniqueFilename);

            file.transferTo(filePath.toFile());
            System.out.println("Saved file to: " + filePath);
            return uniqueFilename;
        } catch (IOException e) {
            System.err.println("Failed to save file: " + e.getMessage());
            throw new IOException("Error saving file to " + folderPath + ": " + e.getMessage(), e);
        }
    }



}