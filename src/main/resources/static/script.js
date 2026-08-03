const API_URL = '/api/issues';

// Apply dark mode on page load if previously saved
if (localStorage.getItem('theme') === 'dark') {
    document.body.classList.add('dark-mode');
}

function togglebg() {
    document.body.classList.toggle('dark-mode');
    
    // Save the new state to local storage
    if (document.body.classList.contains('dark-mode')) {
        localStorage.setItem('theme', 'dark');
    } else {
        localStorage.setItem('theme', 'light');
    }
}

// Fetch all issues on page load
document.addEventListener('DOMContentLoaded', fetchAndRenderIssues);

async function fetchAndRenderIssues() {
    try {
        const response = await fetch(API_URL);
        // ... (inside fetchAndRenderIssues, after fetching the JSON) ...
        const issues = await response.json();
        
        document.getElementById('requested-list').innerHTML = '';
        document.getElementById('in-progress-list').innerHTML = '';
        document.getElementById('done-list').innerHTML = '';

        // NEW: Get the current filter value
        const filterValue = document.getElementById('priority-filter').value;

        // NEW: Filter the array before looping
        const filteredIssues = issues.filter(issue => {
            if (filterValue === 'ALL') return true;
            return issue.priority === filterValue;
        });

        // UPDATE: Loop through filteredIssues instead of issues
        filteredIssues.forEach(issue => {
            const card = document.createElement('div');
            // ... (rest of your card generation logic remains exactly the same)
            // Dynamically assign your CSS class based on status
            card.className = `card ${issue.status.toLowerCase().replace(' ', '-')}`;
            
            // Build the card HTML
            card.innerHTML = `
                <div class="card-content">
                    <h4>${issue.title}</h4>
                    <p>${issue.description}</p>
                    <small><strong>Priority:</strong> ${issue.priority}</small>
                    ${issue.remarks ? `<br><small><strong>Remarks:</strong> ${issue.remarks}</small>` : ''}
                </div>
                <div class="card-actions" style="margin-top: 10px;">
                    ${getNextButtonHtml(issue)}
                </div>
            `;

            // Append to the correct column
            if (issue.status === 'Requested') {
                document.getElementById('requested-list').appendChild(card);
            } else if (issue.status === 'In Progress') {
                document.getElementById('in-progress-list').appendChild(card);
            } else if (issue.status === 'Done') {
                document.getElementById('done-list').appendChild(card);
            }
        });
    } catch (error) {
        console.error("Error fetching issues:", error);
    }
}

// Determine button text and target status
function getNextButtonHtml(issue) {
    if (issue.status === 'Requested') {
        return `<button onclick="moveIssue(${issue.id}, 'In Progress')">Next</button>`;
    } else if (issue.status === 'In Progress') {
        return `<button onclick="moveIssue(${issue.id}, 'Done')">Next</button>`;
    } else if (issue.status === 'Done') {
        return `<button onclick="moveIssue(${issue.id}, 'Archived')">Archive</button>`;
    }
    return ''; // Archived items get no button
}

// Fire the PUT request to update the status
async function moveIssue(id, newStatus) {
    try {
        // 1. Fetch the current issue data first so we don't overwrite other fields with nulls
        const getResponse = await fetch(`${API_URL}/${id}`);
        const issue = await getResponse.json();

        // 2. Update the status field
        issue.status = newStatus;

        // 3. Send the updated object back to the server
        const putResponse = await fetch(`${API_URL}/${id}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(issue)
        });

        if (putResponse.ok) {
            // Re-render the board to show the moved card
            fetchAndRenderIssues();
        } else {
            console.error("Failed to move issue. Server rejected the transition.");
        }
    } catch (error) {
        console.error("Error moving issue:", error);
    }
}

// --- MODAL LOGIC --- //
const modal = document.getElementById('create-modal');

function openModal() {
    modal.style.display = 'block';
}

function closeModal() {
    modal.style.display = 'none';
}

// Close modal if user clicks outside of the content box
window.onclick = function(event) {
    if (event.target == modal) {
        closeModal();
    }
}

// --- CREATE LOGIC --- //
document.getElementById('create-issue-form').addEventListener('submit', async function(event) {
    event.preventDefault(); 

    const newIssue = {
        title: document.getElementById('issue-title').value,
        description: document.getElementById('issue-desc').value,
        priority: document.getElementById('issue-priority').value,
        remarks: document.getElementById('issue-remarks').value
    };

    try {
        const response = await fetch(API_URL, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(newIssue)
        });

        if (response.ok) {
            event.target.reset(); // Clear the inputs
            closeModal();         // Hide the modal
            fetchAndRenderIssues(); // Refresh the board
        } else {
            console.error("Failed to create issue.");
        }
    } catch (error) {
        console.error("Error creating issue:", error);
    }
});