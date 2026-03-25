/**
 * Handle Rental Contract Creation (UC-08)
 */
async function createRentalContract(contractData) {
    try {
        const response = await fetch(`${API_BASE_URL}/contracts`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(contractData)
        });

        if (!response.ok) throw new Error('Contract creation failed. Car may be out of stock.');

        showAlert('alert-success', 'Contract successfully created! (UC-08 Flow Complete)');
        const modal = document.getElementById('booking-modal');
        if (modal) modal.style.display = 'none';
    } catch (error) {
        showAlert('alert-error', error.message);
    }
}

/**
 * Handle Booking Modal UI
 */
function openBookingModal(carId) {
    const modal = document.getElementById('booking-modal');
    if (modal) {
        modal.style.display = 'flex';
        modal.setAttribute('data-car-id', carId);
    }
}

function closeBookingModal() {
    const modal = document.getElementById('booking-modal');
    if (modal) modal.style.display = 'none';
}
