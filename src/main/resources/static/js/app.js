document.addEventListener('DOMContentLoaded', function() {
  // Initialize the application
  initTicketBuilder();
  initEventListeners();
});

function initTicketBuilder() {
  // Ticket builder functionality
  console.log("Ticket builder initialized");
}

function initEventListeners() {
  // All event listeners for the application
  document.getElementById('generate-ticket-btn').addEventListener('click', generateTicket);
}

function generateTicket() {
  const type = document.getElementById('ticket-type').value;
  fetch(`/api/tickets/generate?type=${type}`, {
      method: 'POST',
      headers: {
          'Content-Type': 'application/json'
      }
  })
  .then(response => response.json())
  .then(ticket => {
      renderTicket(ticket);
  })
  .catch(error => console.error('Error:', error));
}

function renderTicket(ticket) {
  const container = document.getElementById('tickets-container');
  const ticketElement = document.createElement('div');
  ticketElement.className = 'ticket-card';
  ticketElement.innerHTML = `
      <div class="ticket-header">
          <h3>${ticket.name}</h3>
          <span class="odds-badge">${ticket.totalOdds.toFixed(2)}</span>
      </div>
      <div class="ticket-body">
          ${ticket.selections.map(selection => `
              <div class="selection-row">
                  <span>${selection.homeTeam} vs ${selection.awayTeam}</span>
                  <span>${selection.marketType}: ${selection.prediction} @${selection.odds.toFixed(2)}</span>
              </div>
          `).join('')}
      </div>
  `;
  container.appendChild(ticketElement);
}