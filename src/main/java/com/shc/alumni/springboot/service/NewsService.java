package com.shc.alumni.springboot.service;

import com.shc.alumni.springboot.entity.News;
import com.shc.alumni.springboot.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

@Service
public class NewsService {

	@Autowired
	private NewsRepository newsRepository;

	public News getNewsById(int id) {
		return newsRepository.findById((long) id).orElse(null);
	}

	public List<News> findAll() {
		return newsRepository.findAll();
	}

	public List<News> findTop5ByOrderByCreatedAtDesc() {
		return newsRepository.findTop5ByOrderByCreatedAtDesc();
	}

	public void saveNews(String title, String content, String category, MultipartFile[] mediaFiles,
			HttpServletRequest request) throws IOException {

		News news = new News();
		news.setTitle(title);
		news.setContent(content);
		news.setCategory(category);
		news.setCreatedAt(LocalDateTime.now());

		List<String> mediaPaths = new ArrayList<>();

		if (mediaFiles != null && mediaFiles.length > 0) {
			if (mediaFiles.length > 5) {
				throw new IllegalArgumentException("Maximum 5 media files allowed");
			}

			for (MultipartFile file : mediaFiles) {
				if (!file.isEmpty()) {
					String filename = saveMediaFileToFolder(file, request);
					mediaPaths.add(filename);
				}
			}
		}

		news.setMediaPaths(mediaPaths);
		newsRepository.save(news);
	}

	private String saveMediaFileToFolder(MultipartFile file, HttpServletRequest request) throws IOException {
		// Get dynamic webapp root path
		ServletContext servletContext = request.getServletContext();
		String appRoot = servletContext.getRealPath("/");
		if (appRoot == null) {
			appRoot = System.getProperty("user.dir") + "/webapp/";
		}

		// Define folder name under app root
		String folderName = "news_folder";
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
		System.out.println("Saved media file to: " + filePath);

		return uniqueFilename;
	}

	public void deleteById(Long id) {
		News news = newsRepository.findById(id).orElse(null);
		if (news != null) {
			List<String> mediaPaths = news.getMediaPaths(); // This should work with the getter above
			if (mediaPaths != null && !mediaPaths.isEmpty()) {
				for (String path : mediaPaths) {
					try {
						Files.deleteIfExists(Paths.get(path));
					} catch (IOException e) {
						System.err.println("Failed to delete file: " + path);
					}
				}
			}
			newsRepository.deleteById(id);
		} else {
			throw new IllegalArgumentException("News with ID " + id + " does not exist.");
		}
	}


}