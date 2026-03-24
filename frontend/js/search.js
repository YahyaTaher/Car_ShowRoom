/**
 * Handle Car Search (UC-07)
 */
async function searchCars(filters) {
    const loading = document.getElementById('loading');
    const results = document.getElementById('car-results');
    
    if (loading) loading.style.display = 'block';
    if (results) results.style.opacity = '0.3';

    try {
        const queryParams = new URLSearchParams(filters).toString();
        const response = await fetch(`${API_BASE_URL}/cars?${queryParams}`);
        
        if (!response.ok) throw new Error('Failed to fetch cars');

        const cars = await response.json();
        renderCars(cars);
    } catch (error) {
        console.error('Search error:', error);
        // Fallback or mock data for demonstration
    } finally {
        if (loading) loading.style.display = 'none';
        if (results) results.style.opacity = '1';
    }
}

function renderCars(cars) {
    const results = document.getElementById('car-results');
    if (!results) return;

    results.innerHTML = cars.map(car => `
        <div class="card car-card">
            <h3>${car.companyName} ${car.modelName} (${car.year})</h3>
            <p class="price">$${car.price.toFixed(2)} / day</p>
            <p>Location: ${car.branchName}</p>
            <button class="btn btn-primary book-btn" data-car-id="${car.id}">Rent This Car</button>
        </div>
    `).join('');
}
