// /frontend/js/app.js

async function generateTickets() {
  const response = await fetch("http://localhost:8080/api/tickets"); // your backend endpoint
  const tickets = await response.json();

  const ticketsDiv = document.getElementById("tickets");
  ticketsDiv.innerHTML = "";

  tickets.forEach((ticket, index) => {
    const div = document.createElement("div");
    div.className = "ticket";

    div.innerHTML = `<h3>🎯 Ticket ${index + 1}</h3><ul>` +
      ticket.items.map(item => `
        <li><strong>${item.market}</strong>: ${item.homeTeam} vs ${item.awayTeam} → ${item.prediction}</li>
      `).join("") + "</ul>";

    ticketsDiv.appendChild(div);
  });
}
