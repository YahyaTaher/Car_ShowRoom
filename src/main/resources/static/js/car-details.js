document.addEventListener('DOMContentLoaded', async () => {
    const params = new URLSearchParams(window.location.search);
    const carId = params.get('carId');

    if (!carId) {
        Swal.fire({ icon: 'error', title: 'Invalid car', text: 'Car ID is missing.' });
        return;
    }

    try {
        const response = await fetch(`${API_BASE_URL}/cars/${carId}`, { headers: getAuthHeaders() });
        const result = await response.json();
        if (!result.success || !result.data) {
            throw new Error(result.message || 'Failed to load car details');
        }

        const car = result.data;
        document.getElementById('car-title').textContent = `${car.brand} ${car.model}`;
        document.getElementById('car-branch').textContent = `Branch: ${car.branchName || 'N/A'}`;
        document.getElementById('car-year').textContent = `Year: ${car.year || '-'}`;
        document.getElementById('car-colors').textContent = `Colors: ${(car.colors || []).join(', ') || '-'}`;
        document.getElementById('car-qty').textContent = `Available Units: ${car.quantityAvailable ?? 0}`;
        document.getElementById('car-price').textContent = `$${Number(car.price || 0).toLocaleString()}`;
        document.getElementById('car-main-image').src = (car.imageUrls && car.imageUrls[0]) || 'https://via.placeholder.com/800x500?text=No+Image';

        const unavailable = car.status !== 'AVAILABLE' || (typeof car.quantityAvailable === 'number' && car.quantityAvailable <= 0);
        const proceedBtn = document.getElementById('proceed-btn');
        proceedBtn.disabled = unavailable;
        if (unavailable) {
            proceedBtn.textContent = 'Unavailable';
        } else {
            proceedBtn.addEventListener('click', () => {
                window.location.href = `/purchase?carId=${car.id}`;
            });
        }
    } catch (error) {
        Swal.fire({ icon: 'error', title: 'Load Failed', text: error.message });
    }
});

