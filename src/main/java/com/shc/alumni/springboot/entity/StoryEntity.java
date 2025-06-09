package com.shc.alumni.springboot.entity;

import java.time.LocalDateTime;

import javax.persistence.*;

@Entity
@Table(name = "stories")
public class StoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content; // Your Story

    @Column(name = "linkedin_profile")
    private String linkedinProfile;

    @Column(name = "consent_to_publish")
    private boolean consentToPublish;

    @Column(nullable = false)
    private String department;

    @Column(name = "organization")
    private String organization;

    @Column(name = "story_image_path")
    private String storyImagePath;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    
    // Default constructor
    public StoryEntity() {}

    // Updated constructor with language
    public StoryEntity(String title, String content, String linkedinProfile, boolean consentToPublish,
                       String department, String organization, LocalDateTime createdAt) {
        this.title = title;
        this.content = content;
        this.linkedinProfile = linkedinProfile;
        this.consentToPublish = consentToPublish;
        this.department = department;
        this.organization = organization;
        this.createdAt = createdAt;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLinkedinProfile() {
        return linkedinProfile;
    }

    public void setLinkedinProfile(String linkedinProfile) {
        this.linkedinProfile = linkedinProfile;
    }

    public boolean isConsentToPublish() {
        return consentToPublish;
    }

    public void setConsentToPublish(boolean consentToPublish) {
        this.consentToPublish = consentToPublish;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getStoryImagePath() {
        return storyImagePath;
    }

    public void setStoryImagePath(String storyImagePath) {
        this.storyImagePath = storyImagePath;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

}