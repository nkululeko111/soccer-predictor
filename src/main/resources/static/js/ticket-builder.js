class TicketBuilder {
    constructor() {
        this.currentTicket = null;
        this.initElements();
        this.initEventListeners();
    }

    initElements() {
        this.elements = {
            ticketTypeSelect: document.getElementById('ticket-type'),
            generateBtn: document.getElementById('generate-btn'),
            ticketContainer: document.getElementById('ticket-container'),
            saveBtn: document.getElementById('save-ticket'),
            clearBtn: document.getElementById('clear-ticket'),
            stakeInput: document.getElementById('stake-input'),
            potentialWinDisplay: document.getElementById('potential-win')
        };
    }

    initEventListeners() {
        this.elements.generateBtn.addEventListener('click', () => this.generateTicket());
        this.elements.saveBtn.addEventListener('click', () => this.saveTicket());
        this.elements.clearBtn.addEventListener('click', () => this.clearTicket());
        this.elements.stakeInput.addEventListener('input', () => this.calculatePotentialWin());
    }

    async generateTicket() {
        const type = this.elements.ticketTypeSelect.value;
        try {
            const response = await fetch(`/api/tickets/generate?type=${type}`);
            this.currentTicket = await response.json();
            this.renderTicket();
        } catch (error) {
            console.error('Error generating ticket:', error);
            alert('Failed to generate ticket');
        }
    }

    renderTicket() {
        this.elements.ticketContainer.innerHTML = this.createTicketHTML();
        this.calculatePotentialWin();
    }

    createTicketHTML() {
        return `
            <div class="ticket-header">
                <h3>${this.currentTicket.name}</h3>
                <div class="ticket-meta">
                    <span class="odds-badge">${this.currentTicket.totalOdds.toFixed(2)}</span>
                    <span class="stake-input">
                        <input type="number" id="stake-input" placeholder="Enter stake" min="1">
                    </span>
                    <span class="potential-win" id="potential-win"></span>
                </div>
            </div>
            <div class="ticket-selections">
                ${this.currentTicket.selections.map(sel => `
                    <div class="selection" data-selection-id="${sel.id}">
                        <div class="teams">${sel.fixture.homeTeam} vs ${sel.fixture.awayTeam}</div>
                        <div class="market">
                            <span class="market-type">${sel.marketType}</span>
                            <span class="prediction">${sel.prediction}</span>
                            <span class="odds">@${sel.odds.toFixed(2)}</span>
                            <span class="confidence">(${(sel.confidence * 100).toFixed(0)}%)</span>
                        </div>
                        <button class="replace-btn">Replace</button>
                    </div>
                `).join('')}
            </div>
        `;
    }

    calculatePotentialWin() {
        if (!this.currentTicket) return;
        const stake = parseFloat(this.elements.stakeInput.value) || 0;
        const potentialWin = stake * this.currentTicket.totalOdds;
        this.elements.potentialWinDisplay.textContent = `Potential Win: $${potentialWin.toFixed(2)}`;
    }

    async saveTicket() {
        if (!this.currentTicket) return;
        
        try {
            const response = await fetch('/api/tickets', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    ...this.currentTicket,
                    stake: parseFloat(this.elements.stakeInput.value)
                })
            });
            
            if (response.ok) {
                alert('Ticket saved successfully!');
                this.clearTicket();
            }
        } catch (error) {
            console.error('Error saving ticket:', error);
            alert('Failed to save ticket');
        }
    }

    clearTicket() {
        this.currentTicket = null;
        this.elements.ticketContainer.innerHTML = '';
        this.elements.stakeInput.value = '';
        this.elements.potentialWinDisplay.textContent = '';
    }
}

// Initialize when DOM is loaded
document.addEventListener('DOMContentLoaded', () => {
    new TicketBuilder();
});