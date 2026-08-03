# Bug Tracker

A full-stack Kanban issue management system designed to track feature requests, monitor active development, and archive resolved bugs. 

**Live Demo:** https://bug-tracker-92hh.onrender.com

This project demonstrates client-server architecture, utilizing a RESTful API built with Spring Boot to manage state transitions, and a Vanilla JavaScript frontend that asynchronously updates the DOM without page reloads.

## Tech Stack
* **Backend:** Java 21, Spring Boot, Spring Web, Spring Data JPA, Spring Boot Actuator
* **Database:** H2 In-Memory Database
* **Frontend:** HTML5, CSS3, Vanilla JavaScript
* **Build/Deployment:** Maven, Docker, Render

## Key Features
* **Kanban Workflow:** Enforced state transitions (Requested -> In Progress -> Done -> Archived).
* **RESTful CRUD Operations:** Full API integration for creating, reading, updating, and permanently deleting issues.
* **Dynamic Board:** Asynchronous DOM manipulation using `fetch` to render cards and update statuses in real-time.
* **Priority Filtering:** Client-side filtering mechanism (High, Medium, Low).
* **Persistent UI:** Dark mode preferences saved via browser `localStorage`.
* **Production-Ready Docker:** Multi-stage build utilizing a non-root user and automated actuator health checks for secure, reliable deployment.

## 📡 API Endpoints
* `GET /api/issues` - Retrieve all issues
* `POST /api/issues` - Create a new issue
* `PUT /api/issues/{id}` - Update an existing issue (status, title, etc.)
* `DELETE /api/issues/{id}` - Permanently delete an issue
* `GET /actuator/health` - Application health check endpoint