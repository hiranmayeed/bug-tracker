const API_URL = '/api/issues';

// Apply dark mode on page load if previously saved
if (localStorage.getItem('theme') === 'dark') {
    document.body.classList.add('dark-mode');
}

document.addEventListener('DOMContentLoaded', fetchAndRenderArchived);

async function fetchAndRenderArchived() {
    try {
        const response = await fetch(API_URL);
        const issues = await response.json();

        const archivedList = document.getElementById('archived-list');
        archivedList.innerHTML = '';

        const archivedIssues = issues.filter(issue => issue.status === 'Archived');

        if (archivedIssues.length === 0) {
            archivedList.innerHTML = '<p style="text-align: center; margin-top: 20px;">No archived issues found.</p>';
            return;
        }

        archivedIssues.forEach(issue => {
            const card = document.createElement('div');
            card.className = 'card done';

            const createdStr = issue.createdAt ? issue.createdAt : 'N/A';
            const completedStr = issue.completedAt ? issue.completedAt : 'N/A';

            // ... inside the forEach loop in archive.js ...
            card.innerHTML = `
                <div class="card-content">
                    <h4>${issue.title}</h4>
                    <p>${issue.description}</p>
                    <small><strong>Priority:</strong> ${issue.priority}</small><br>
                    ${issue.remarks ? `<small><strong>Remarks:</strong> ${issue.remarks}</small><br>` : ''}
                    
                    <hr style="margin: 10px 0; border: 0; border-top: 1px solid rgba(0,0,0,0.2);">
                    
                    <small><strong>Created:</strong> ${createdStr}</small><br>
                    <small><strong>Completed:</strong> ${completedStr}</small>
                </div>
                <!-- NEW: Delete Button -->
                <div class="card-actions" style="margin-top: 10px; text-align: right;">
                    <button onclick="deleteIssue(${issue.id})" style="background-color: #c0392b; color: white; border: none; padding: 5px 10px; border-radius: 5px; cursor: pointer;">Delete Permanently</button>
                </div>
            `;
            
            archivedList.appendChild(card);
        });
    } catch (error) {
        console.error("Error fetching archived issues:", error);
    }
}

// NEW: Delete API Call
async function deleteIssue(id) {
    if (confirm("Are you sure you want to permanently delete this issue?")) {
        try {
            const response = await fetch(`${API_URL}/${id}`, {
                method: 'DELETE'
            });

            if (response.ok) {
                fetchAndRenderArchived(); // Refresh the archive list
            } else {
                console.error("Failed to delete issue.");
            }
        } catch (error) {
            console.error("Error deleting issue:", error);
        }
    }
}