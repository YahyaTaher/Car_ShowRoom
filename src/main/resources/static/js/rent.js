/**
 * Car Sale Module (formerly Rent)
 * Features:
 * Buy Car Modal
 * Purchase Summary
 * Customer Form
 * Payment Method
 * Confirm Purchase
 * Success Invoice
 * Mark Car as Sold
 * Refresh Cars List
 */

function openPurchaseModal(carId, carData = {}) {
  const modal = document.getElementById('purchase-modal');
  if (!modal) return;

  modal.style.display = 'flex';
  modal.classList.add('fade-in');
  modal.setAttribute('data-car-id', carId);

  updatePurchaseSummary(carData);
}

function closePurchaseModal() {
  const modal = document.getElementById('purchase-modal');
  if (!modal) return;

  modal.classList.remove('fade-in');
  setTimeout(() => {
    modal.style.display = 'none';
  }, 250);
}

function updatePurchaseSummary(car = {}) {
  const box = document.getElementById('purchase-summary');
  if (!box) return;

  const price = Number(car.price || 0);
  const qty = typeof car.quantityAvailable === 'number' ? car.quantityAvailable : null;
  const colors = Array.isArray(car.colors) ? car.colors : [];
  const isAvailable = qty === null ? true : qty > 0;

  box.innerHTML = `
    <div class="summary-item"><strong>Car:</strong> ${car.brand || '-'} ${car.model || ''} (${car.year || ''})</div>
    <div class="summary-item"><strong>Price:</strong> $${price.toLocaleString()}</div>
    ${colors.length ? `<div class="summary-item"><strong>Colors:</strong> ${colors.join(', ')}</div>` : ''}
    ${qty !== null ? `<div class="summary-item"><strong>Available:</strong> ${qty}</div>` : ''}
    <div class="summary-item"><strong>Status:</strong> ${isAvailable ? 'Available' : 'Unavailable'}</div>
  `;
}

async function confirmPurchase() {
  const token = localStorage.getItem('car_showroom_token');
  if (!token || token === 'null' || token === 'undefined' || token.trim() === '') {
    Swal.fire({
      icon: 'warning',
      title: 'Login required',
      text: 'Please login before making a purchase.'
    }).then(() => {
      window.location.href = '/login';
    });
    return;
  }

  // Read carId from the form input (populated from URL ?carId=X)
  // Fall back to modal attribute if the input doesn't exist
  const carIdInput = document.getElementById('car-id');
  const rawCarId = carIdInput?.value?.trim()
    || document.getElementById('purchase-modal')?.getAttribute('data-car-id');

  if (!rawCarId) {
    Swal.fire({ icon: 'error', title: 'Error', text: 'Car ID is missing. Please go back and select a car.' });
    return;
  }

  const carId = parseInt(rawCarId, 10);
  if (isNaN(carId)) {
    Swal.fire({ icon: 'error', title: 'Error', text: 'Invalid car ID. Please go back and select a car.' });
    return;
  }

  const customerName = document.getElementById('customer-name')?.value.trim();
  const phone = document.getElementById('customer-phone')?.value.trim();
  const email = document.getElementById('customer-email')?.value.trim();
  const paymentMethod = document.getElementById('payment-method')?.value;

  if (!customerName || !phone || !paymentMethod) {
    Swal.fire({ icon: 'error', title: 'Missing Data', text: 'Please fill all required fields' });
    return;
  }

  const payload = {
    carId,          // FIX 2: now a number (Long), not a string
    customerName,
    phone,
    email,
    paymentMethod
  };

  const btn = document.getElementById('confirm-purchase-btn');

  try {
    if (btn) {
      btn.disabled = true;
      btn.innerHTML = '<span class="spinner-border spinner-border-sm"></span> Processing...';
    }

    Swal.fire({
      title: 'Creating Purchase Contract...',
      allowOutsideClick: false,
      didOpen: () => Swal.showLoading()
    });

    const response = await fetch(`${API_BASE_URL}/purchases`, {
      method: 'POST',
      // FIX 3: ensure Content-Type is always set so Spring can parse @RequestBody
      headers: {
        'Content-Type': 'application/json',
        ...getAuthHeaders()
      },
      body: JSON.stringify(payload)
    });

    // FIX 4: check HTTP status before parsing JSON to get a clear error message
    if (!response.ok) {
      const errBody = await response.json().catch(() => ({}));
      throw new Error(errBody.message || `Server error: ${response.status}`);
    }

    const data = await response.json();
    if (!data.success) throw new Error(data.message || 'Purchase failed');

    Swal.fire({
      icon: 'success',
      title: 'Purchase Submitted',
      text: 'Your purchase contract is pending admin approval.'
    });

    document.getElementById('purchase-form')?.reset();
    closePurchaseModal();

    if (typeof searchCars === 'function') searchCars({});

  } catch (error) {
    Swal.fire({ icon: 'error', title: 'Purchase Failed', text: error.message });
  } finally {
    if (btn) {
      btn.disabled = false;
      btn.innerHTML = 'Confirm Purchase';
    }
  }
}