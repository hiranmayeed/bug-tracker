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
            existingIssue.setTitle(updatedIssue.getTitle());
            existingIssue.setDescription(updatedIssue.getDescription());
            existingIssue.setStatus(updatedIssue.getStatus());
            existingIssue.setPriority(updatedIssue.getPriority());
            return issueRepository.save(existingIssue);
        }
        return null;
    }

    public void deleteIssue(Long id) {
        issueRepository.deleteById(id);
    }
}