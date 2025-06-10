package com.shc.alumni.springboot.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

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
		}

		storyRepository.save(story);

	}

	private String saveFileToFolder(MultipartFile file, HttpServletRequest request) throws IOException {
		// Get dynamic webapp root path
		ServletContext servletContext = request.getServletContext();
		String appRoot = servletContext.getRealPath("/");
		if (appRoot == null) {
			appRoot = System.getProperty("user.dir") + "/webapp/";
		}

		// Define folder name under app root
		String folderName = "story_folder";
		Path folderPath = Paths.get(appRoot, folderName);

		// Create folder if it doesn't exist
		if (!Files.exists(folderPath)) {
			Files.createDirectories(folderPath);
			System.out.println("Created directory: " + folderPath);
		}

		// Generate unique filename and save the file
		String originalFileName = file.getOriginalFilename();
		String uniqueFilename = System.currentTimeMillis() + "_" + originalFileName;
		Path filePath = folderPath.resolve(uniqueFilename);

		file.transferTo(filePath.toFile());
		System.out.println("Saved file to: " + filePath);

		return uniqueFilename; 
	}

}