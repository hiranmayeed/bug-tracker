package com.bugtracker.bug_tracker.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Issue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String title;
    private String description;
    private String priority;
    private String status;
    private String remarks;
    private java.time.LocalDate createdAt;
    private java.time.LocalDate completedAt;
    
    // Default Constructor (Required by JPA)
    public Issue() {}

    // Getters and Setters (Required for Spring to read/write the JSON)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }

    // ADDED: Getters and Setters for dates
    public java.time.LocalDate getCreatedAt() { return createdAt; }
    public void setCreatedAt(java.time.LocalDate createdAt) { this.createdAt = createdAt; }

    public java.time.LocalDate getCompletedAt() { return completedAt; }
    public void setCompletedAt(java.time.LocalDate completedAt) { this.completedAt = completedAt; }
}