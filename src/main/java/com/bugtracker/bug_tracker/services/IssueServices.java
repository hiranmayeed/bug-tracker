package com.bugtracker.bug_tracker.services;

import com.bugtracker.bug_tracker.models.Issue;
import com.bugtracker.bug_tracker.repository.IssueRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class IssueServices {
    private final IssueRepository issueRepository;

    public IssueServices(IssueRepository issueRepository) {
        this.issueRepository = issueRepository;
    }

    public Issue createIssue(Issue issue) {
        issue.setStatus("Requested");
        issue.setCreatedAt(java.time.LocalDate.now()); // Auto-set creation date
        
        if (issue.getPriority() != null) {
            issue.setPriority(issue.getPriority().toUpperCase());
        } else {
            issue.setPriority("LOW"); 
        }
        return issueRepository.save(issue);
    }

    public List<Issue> getAllIssues() {
        return issueRepository.findAll();
    }

    public Issue getIssueById(Long id) {
        return issueRepository.findById(id).orElse(null);
    }

    public Issue updateIssue(Long id, Issue updatedIssue) {
        Issue existingIssue = issueRepository.findById(id).orElse(null);
        if (existingIssue != null) {
            // Check if the frontend is trying to change the status
            if (updatedIssue.getStatus() != null && !existingIssue.getStatus().equals(updatedIssue.getStatus())) {
                if (!isValidTransition(existingIssue.getStatus(), updatedIssue.getStatus())) {
                    return null;
                }
                // NEW: If moving to Done or Archived, mark the completion date
                if ("Done".equals(updatedIssue.getStatus()) || "Archived".equals(updatedIssue.getStatus())) {
                    if (existingIssue.getCompletedAt() == null) {
                        existingIssue.setCompletedAt(java.time.LocalDate.now());
                    }
                }
                existingIssue.setStatus(updatedIssue.getStatus());
            }
            if (updatedIssue.getTitle() != null) existingIssue.setTitle(updatedIssue.getTitle());
            if (updatedIssue.getDescription() != null) existingIssue.setDescription(updatedIssue.getDescription());
            if(updatedIssue.getRemarks() != null) existingIssue.setRemarks(updatedIssue.getRemarks());
            if (updatedIssue.getPriority() != null) existingIssue.setPriority(updatedIssue.getPriority());
            return issueRepository.save(existingIssue);
        }
        return null;
    }

    public void deleteIssue(Long id) {
        issueRepository.deleteById(id);
    }

    private boolean isValidTransition(String currentStatus, String newStatus) {
        if ("Requested".equals(currentStatus) && "In Progress".equals(newStatus)) return true;
        else if ("In Progress".equals(currentStatus) && "Done".equals(newStatus)) return true;
        else if ("Done".equals(currentStatus) && "Archived".equals(newStatus)) return true;
        if("In Progress".equals(currentStatus) && "Requested".equals(newStatus)) return true;
        if("Done".equals(currentStatus) && "In Progress".equals(newStatus)) return true;
        return false;
    }
}