package com.bugtracker.bug_tracker.repository;

import com.bugtracker.bug_tracker.models.Issue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Long> {
}