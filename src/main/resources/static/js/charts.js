class StatsCharts {
    constructor() {
        this.charts = {};
        this.initCharts();
    }

    initCharts() {
        // Initialize Chart.js instances for different stats
        this.initWinRateChart();
        this.initOddsDistributionChart();
        this.initPerformanceChart();
    }

    initWinRateChart() {
        const ctx = document.getElementById('win-rate-chart').getContext('2d');
        this.charts.winRate = new Chart(ctx, {
            type: 'doughnut',
            data: {
                labels: ['Won', 'Lost', 'Pending'],
                datasets: [{
                    data: [45, 30, 25],
                    backgroundColor: [
                        '#4CAF50',
                        '#F44336',
                        '#FFC107'
                    ]
                }]
            },
            options: {
                responsive: true,
                plugins: {
                    legend: {
                        position: 'bottom'
                    }
                }
            }
        });
    }

    initOddsDistributionChart() {
        const ctx = document.getElementById('odds-distribution-chart').getContext('2d');
        this.charts.oddsDistribution = new Chart(ctx, {
            type: 'bar',
            data: {
                labels: ['1-2', '2-3', '3-5', '5-10', '10+'],
                datasets: [{
                    label: 'Number of Bets',
                    data: [12, 19, 8, 5, 3],
                    backgroundColor: '#4CAF50'
                }]
            },
            options: {
                responsive: true,
                scales: {
                    y: {
                        beginAtZero: true
                    }
                }
            }
        });
    }

    initPerformanceChart() {
        const ctx = document.getElementById('performance-chart').getContext('2d');
        this.charts.performance = new Chart(ctx, {
            type: 'line',
            data: {
                labels: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun'],
                datasets: [{
                    label: 'Profit/Loss',
                    data: [100, 150, 200, 180, 250, 300],
                    borderColor: '#4CAF50',
                    backgroundColor: 'rgba(76, 175, 80, 0.1)',
                    fill: true
                }]
            },
            options: {
                responsive: true,
                scales: {
                    y: {
                        beginAtZero: false
                    }
                }
            }
        });
    }

    updateCharts(data) {
        // Method to update charts with new data
        this.charts.winRate.data.datasets[0].data = data.winRate;
        this.charts.oddsDistribution.data.datasets[0].data = data.oddsDistribution;
        this.charts.performance.data.datasets[0].data = data.performance;
        
        Object.values(this.charts).forEach(chart => chart.update());
    }
}

// Initialize when DOM is loaded
document.addEventListener('DOMContentLoaded', () => {
    if (document.getElementById('win-rate-chart')) {
        new StatsCharts();
    }
});