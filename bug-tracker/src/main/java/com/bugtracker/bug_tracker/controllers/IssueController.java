package com.bugtracker.bug_tracker.controllers;

import com.bugtracker.bug_tracker.models.Issue;
import com.bugtracker.bug_tracker.repository.IssueRepository;
import com.bugtracker.bug_tracker.services.IssueServices;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.List;

@RestController
@RequestMapping("/api/issues")
@CrossOrigin(origins = "*")
public class IssueController {

    private IssueServices issueServices;

    // Controller methods for handling issue-related requests

    public IssueController(IssueServices issueServices) {
        this.issueServices = issueServices;
    }

    @GetMapping
    public List<Issue> getAllIssues() {
        return issueServices.getAllIssues();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Issue> getIssueById(@PathVariable Long id) {
        Issue issue = issueServices.getIssueById(id);
        if(issue != null) {
            return ResponseEntity.ok(issue);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public Issue createIssue(@RequestBody Issue issue) {
        return issueServices.createIssue(issue);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Issue> updateIssue(@PathVariable Long id, @RequestBody Issue issueDetails) {
        Issue updatedIssue = issueServices.updateIssue(id, issueDetails);
        if(updatedIssue != null) {
            return ResponseEntity.ok(updatedIssue);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIssue(@PathVariable Long id) {
        if (issueServices.getIssueById(id) != null) {
            issueServices.deleteIssue(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}