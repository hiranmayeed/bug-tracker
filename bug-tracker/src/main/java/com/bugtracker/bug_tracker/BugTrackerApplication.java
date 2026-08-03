package com.bugtracker.bug_tracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.bugtracker.bug_tracker.models.Issue;
import com.bugtracker.bug_tracker.services.IssueServices;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BugTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(BugTrackerApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(IssueServices issueService) {
		return (args) -> {
			// Create some sample issues
			if(issueService.getAllIssues().isEmpty()) {
				Issue issue1 = new Issue();
				issue1.setTitle("Bug in login feature");
				issue1.setDescription("Users are unable to log in with valid credentials.");
				issue1.setPriority("High");
				Issue savedIssue1 = issueService.createIssue(issue1);

				// Save the issues to the database
				Issue issue2 = new Issue();
				issue2.setTitle("UI glitch on dashboard");
				issue2.setDescription("The dashboard layout breaks on smaller screens.");
				issue2.setPriority("Medium");
				Issue savedIssue2 = issueService.createIssue(issue2);
				savedIssue2.setStatus("In Progress");
				issueService.updateIssue(savedIssue2.getId(), savedIssue2);

				Issue issue3 = new Issue();
				issue3.setTitle("Performance issue");
				issue3.setDescription("The application takes too long to load data.");
				issue3.setPriority("Low");
				Issue savedIssue3 = issueService.createIssue(issue3);
			}
		};
	}
}
