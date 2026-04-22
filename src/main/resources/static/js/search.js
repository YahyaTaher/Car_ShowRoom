document.addEventListener('DOMContentLoaded', async () => {
    await loadSearchOptions();
});

async function loadSearchOptions() {
    try {
        const options = await fetchDynamicOptions();
        fillSelect(
            document.getElementById('filter-company'),
            options.companies || [],
            (c) => c,
            (c) => c,
            'All Companies'
        );
        fillSelect(
            document.getElementById('filter-branch'),
            options.branches || [],
            (b) => b.name,
            (b) => `${b.name}${b.city ? `, ${b.city}` : ''}`,
            'All Locations'
        );
    } catch (error) {
        console.error('Failed to load search options:', error);
    }
}

async function searchCars(filters) {
    const loading = document.getElementById('loading');
    const results = document.getElementById('car-results');
    const searchBtn = document.getElementById('search-btn');

    // Show enhanced loading
    Swal.fire({
        title: 'Searching cars...',
        allowOutsideClick: false,
        allowEscapeKey: false,
        showConfirmButton: false,
        didOpen: () => {
            Swal.showLoading();
        }
    });

    try {
        const queryParams = new URLSearchParams(filters).toString();

        const response = await fetch(
            `${API_BASE_URL}/cars?${queryParams}`,
            { headers: getAuthHeaders() }
        );

        const data = await response.json();

        if (data.success && data.data) {
            renderCars(data.data);
            Swal.fire({
                icon: 'success',
                title: 'Success!',
                text: `${data.data.length} car(s) found`,
                toast: true,
                position: 'top-end',
                timer: 3000,
                showConfirmButton: false
            });
        } else {
            throw new Error(data.message || 'No cars found');
        }

    } catch (error) {
        console.error('Search error:', error);
        Swal.fire({
            icon: 'error',
            title: 'Search Failed',
            text: error.message
        });

    }
}

function renderCars(cars) {
    const results = document.getElementById('car-results');
    if (!results) return;

    if (cars.length === 0) {
        results.innerHTML = `
            <div class="empty-state">
                <h3>No Cars Found 🚗</h3>
                <p>Try changing filters or dates.</p>
            </div>
        `;
        return;
    }

    results.innerHTML = cars.map(car => `
        <div class="col-lg-4 col-md-6">
            <div class="card h-100 shadow-sm border-0 hover-lift">
                ${car.imageUrls && car.imageUrls.length > 0 ? `
                    <img src="${car.imageUrls[0]}" class="card-img-top" alt="${car.brand} ${car.model}" style="height: 220px; object-fit: cover;">
                ` : `
                    <div class="text-center mt-4 mb-2">
                        <i class="fas fa-car-side fa-3x text-primary mb-2"></i>
                    </div>
                `}
                <div class="card-body d-flex flex-column">
                    <h5 class="card-title">${car.brand} ${car.model}</h5>
                    <p class="card-text text-muted mb-1">Year: ${car.year}</p>
                    <p class="card-text text-muted mb-3">📍 ${car.branchName || 'Main Branch'}</p>
                    ${car.colors && car.colors.length > 0 ? `<p class="card-text text-muted mb-1">Colors: ${car.colors.join(', ')}</p>` : ''}
                    ${typeof car.quantityAvailable === 'number' ? `<p class="card-text text-muted mb-3">Available: ${car.quantityAvailable}</p>` : ''}
                    <p class="fs-3 fw-bold text-success mb-4">$${Number(car.price || 0).toLocaleString()}</p>
                    <a class="btn btn-primary w-100 mt-auto" href="/car-details?carId=${car.id}">
                        <i class="fas fa-circle-info me-2"></i>View Details
                    </a>
                </div>
            </div>
        </div>
    `).join('');
}

function showToast(type, message) {
    Swal.fire({
        toast: true,
        position: 'top-end',
        icon: type,
        title: message,
        showConfirmButton: false,
        timer: 3000,
        timerProgressBar: true,
        customClass: {
            popup: 'swal-toast'
        }
    });
}
