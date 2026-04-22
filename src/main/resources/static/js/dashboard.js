// Dashboard JS - Inventory & Pricing
document.addEventListener('DOMContentLoaded', async () => {
    await loadDashboardOptions();
    // Load Stats
    loadDashboardStats();
    loadPendingContracts();
    loadDashboardAnalytics();

    // Inventory form
    const inventoryForm = document.getElementById('inventory-form');
    if (inventoryForm) {
        inventoryForm.addEventListener('submit', async (e) => {
            e.preventDefault();
            const imageUrls = document.getElementById('car-images').value
                .split(/\r?\n/)
                .map((url) => url.trim())
                .filter((url) => url.length > 0);
            const colors = document.getElementById('car-colors').value
                .split(/\r?\n/)
                .map((c) => c.trim())
                .filter((c) => c.length > 0);
            const formData = {
                brand: document.getElementById('car-company').options[document.getElementById('car-company').selectedIndex].text,
                model: document.getElementById('car-model').value,
                year: parseInt(document.getElementById('car-year').value),
                branchId: parseInt(document.getElementById('car-branch').value),
                price: parseFloat(document.getElementById('car-price').value),
                quantityAvailable: parseInt(document.getElementById('car-quantity').value, 10),
                colors,
                imageUrls
            };
            
            try {
                const response = await fetch(`${API_BASE_URL}/cars`, {
                    method: 'POST',
                    headers: getAuthHeaders(),
                    body: JSON.stringify(formData)
                });
                if (response.ok) {
                    alert('Car added to inventory!');
                    inventoryForm.reset();
                    loadDashboardStats(); // Refresh stats
                } else {
                    const errorData = await response.json();
                    alert('Failed to add car: ' + (errorData.message || response.statusText));
                }
            } catch (error) {
                alert('Failed to add car: ' + error.message);
            }
        });
    }

// Pricing form with loading
    const pricingForm = document.getElementById('pricing-form');
    if (pricingForm) {
        pricingForm.addEventListener('submit', async (e) => {
            e.preventDefault();
            const submitBtn = e.target.querySelector('button[type="submit"]');
            submitBtn.innerHTML = '<span class="spinner-border spinner-border-sm me-2"></span>Updating...';
            submitBtn.disabled = true;

            const carId = document.getElementById('pricing-car').value;
            const price = document.getElementById('new-price').value;
            
            try {
                const response = await fetch(`${API_BASE_URL}/cars/${carId}/price?price=${price}`, {
                    method: 'PUT',
                    headers: getAuthHeaders()
                });
                if (response.ok) {
                    Swal.fire({
                        icon: 'success',
                        title: 'Success!',
                        text: 'Price updated successfully',
                        timer: 2000
                    });
                    pricingForm.reset();
                    loadDashboardStats();
                } else {
                    const errorData = await response.json();
                    Swal.fire({
                        icon: 'error',
                        title: 'Update Failed',
                        text: errorData.message || response.statusText
                    });
                }
            } catch (error) {
                Swal.fire({
                    icon: 'error',
                    title: 'Update Failed',
                    text: error.message
                });
            } finally {
                submitBtn.innerHTML = 'Update Price';
                submitBtn.disabled = false;
            }
        });
    }

    // Cars table initialization
    if (typeof $('#carsTable').DataTable === 'function') {
        $('#carsTable').DataTable({
            ajax: {
                url: `${API_BASE_URL}/cars`,
                headers: getAuthHeaders()
            },
            columns: [
                { data: 'id' },
                { data: 'brand' },
                { data: 'model' },
                { data: 'year' },
                { data: 'branchName' },
                { data: 'price', render: (data) => '$' + Number(data).toLocaleString() },
                { data: 'status' },
                { 
                    data: null,
                    render: function (data) {
                        return `
                            <div class="btn-group" role="group">
                                <button class="btn btn-sm btn-warning" onclick="editCar(${data.id})"><i class="fas fa-edit"></i></button>
                                <button class="btn btn-sm btn-danger" onclick="deleteCar(${data.id})"><i class="fas fa-trash"></i></button>
                            </div>
                        `;
                    }
                }
            ],
            pageLength: 25,
            responsive: true,
            lengthMenu: [[10, 25, 50, -1], [10, 25, 50, "All"]],
            dom: 'Bfrtip',
            buttons: ['copy', 'csv', 'excel', 'pdf', 'print']
        });
    }
});

async function loadDashboardOptions() {
    try {
        const options = await fetchDynamicOptions();

        fillSelect(
            document.getElementById('car-company'),
            options.companies || [],
            (c) => c,
            (c) => c,
            options.companies && options.companies.length ? 'Select Company' : 'No Companies In Database'
        );

        fillSelect(
            document.getElementById('car-branch'),
            options.branches || [],
            (b) => b.id,
            (b) => `${b.name}${b.city ? `, ${b.city}` : ''}`,
            options.branches && options.branches.length ? 'Select Branch' : 'No Branches In Database'
        );
    } catch (error) {
        console.error('Failed to load dashboard options:', error);
    }
}

let salesStatusChart;
let availableUnitsChart;

async function loadDashboardAnalytics() {
    const salesEl = document.getElementById('salesStatusChart');
    const unitsEl = document.getElementById('availableUnitsChart');
    if (!salesEl || !unitsEl || typeof Chart === 'undefined') return;

    try {
        const response = await fetch(`${API_BASE_URL}/dashboard/analytics`, { headers: getAuthHeaders() });
        const payload = await response.json();
        if (!payload.success) throw new Error(payload.message || 'Failed to load analytics');

        const a = payload.data;

        document.getElementById('approved-sales-percent').textContent =
            `Approved sales: ${Number(a.approvedSalesPercent || 0).toFixed(1)}%`;
        document.getElementById('customers-purchased-count').textContent =
            `Customers who purchased: ${a.customersWhoPurchased || 0}`;
        document.getElementById('available-units-total').textContent =
            `Total units available: ${a.availableUnits || 0}`;

        const salesData = [a.pendingSales || 0, a.approvedSales || 0, a.cancelledSales || 0];
        const salesLabels = ['Pending', 'Approved', 'Cancelled'];

        if (salesStatusChart) salesStatusChart.destroy();
        salesStatusChart = new Chart(salesEl, {
            type: 'doughnut',
            data: {
                labels: salesLabels,
                datasets: [{ data: salesData, backgroundColor: ['#f59e0b', '#22c55e', '#ef4444'] }]
            },
            options: { responsive: true, plugins: { legend: { position: 'bottom' } } }
        });

        if (availableUnitsChart) availableUnitsChart.destroy();
        availableUnitsChart = new Chart(unitsEl, {
            type: 'bar',
            data: {
                labels: a.branchNames || [],
                datasets: [{
                    label: 'Available units',
                    data: a.availableUnitsByBranch || [],
                    backgroundColor: '#3b82f6'
                }]
            },
            options: {
                responsive: true,
                plugins: { legend: { display: false } },
                scales: { y: { beginAtZero: true } }
            }
        });
    } catch (error) {
        console.error('Failed to load analytics:', error);
    }
}

function loadCarsTable() {
    if (typeof $ !== 'function') return;
    const table = $('#carsTable').DataTable?.();
    if (table && typeof table.ajax?.reload === 'function') {
        table.ajax.reload(null, false);
    }
}

async function loadPendingContracts() {
    const tableBody = document.querySelector('#contractsTable tbody');
    if (!tableBody) return;

    try {
        const response = await fetch(`${API_BASE_URL}/admin/contracts/pending`, {
            headers: getAuthHeaders()
        });
        const data = await response.json();
        if (!data.success) throw new Error(data.message || 'Failed to load pending contracts');

        const rows = (data.data || []).map(c => {
            const carName = `${c.brand || ''} ${c.model || ''} (${c.year || ''})`.trim();
            return `
                <tr>
                    <td>${c.id}</td>
                    <td>${c.username || '-'}</td>
                    <td>${c.customerName || '-'}</td>
                    <td>${carName}</td>
                    <td>${c.branchName || '-'}</td>
                    <td>$${Number(c.totalPrice || 0).toLocaleString()}</td>
                    <td>${c.quantityAvailable ?? '-'}</td>
                    <td>
                        <div class="btn-group" role="group">
                            <button class="btn btn-sm btn-success" onclick="approveContract(${c.id})">Approve</button>
                            <button class="btn btn-sm btn-danger" onclick="rejectContract(${c.id})">Reject</button>
                        </div>
                    </td>
                </tr>
            `;
        }).join('');

        tableBody.innerHTML = rows || `<tr><td colspan="8" class="text-center text-muted">No pending contracts</td></tr>`;
    } catch (error) {
        tableBody.innerHTML = `<tr><td colspan="8" class="text-center text-danger">${error.message}</td></tr>`;
    }
}

async function approveContract(contractId) {
    const confirmed = await Swal.fire({
        icon: 'question',
        title: 'Approve purchase?',
        text: 'This will finalize the purchase and decrease inventory.',
        showCancelButton: true,
        confirmButtonText: 'Approve'
    });
    if (!confirmed.isConfirmed) return;

    try {
        const response = await fetch(`${API_BASE_URL}/admin/contracts/${contractId}/approve`, {
            method: 'POST',
            headers: getAuthHeaders()
        });
        const data = await response.json();
        if (!data.success) throw new Error(data.message || 'Approve failed');
        Swal.fire({ icon: 'success', title: 'Approved', timer: 1500, showConfirmButton: false });
        loadPendingContracts();
        loadDashboardStats();
        if (typeof loadCarsTable === 'function') loadCarsTable();
    } catch (error) {
        Swal.fire({ icon: 'error', title: 'Approve Failed', text: error.message });
    }
}

async function rejectContract(contractId) {
    const confirmed = await Swal.fire({
        icon: 'warning',
        title: 'Reject purchase?',
        text: 'The contract will be cancelled.',
        showCancelButton: true,
        confirmButtonText: 'Reject'
    });
    if (!confirmed.isConfirmed) return;

    try {
        const response = await fetch(`${API_BASE_URL}/admin/contracts/${contractId}/reject`, {
            method: 'POST',
            headers: getAuthHeaders()
        });
        const data = await response.json();
        if (!data.success) throw new Error(data.message || 'Reject failed');
        Swal.fire({ icon: 'success', title: 'Rejected', timer: 1500, showConfirmButton: false });
        loadPendingContracts();
        loadDashboardStats();
    } catch (error) {
        Swal.fire({ icon: 'error', title: 'Reject Failed', text: error.message });
    }
}

async function loadDashboardStats() {
    const statsContainer = document.getElementById('dashboard-stats');
    if (!statsContainer) return;

    Swal.fire({
        title: 'Loading Dashboard...',
        allowOutsideClick: false,
        showConfirmButton: false,
        didOpen: () => Swal.showLoading()
    });

    try {
        const response = await fetch(`${API_BASE_URL}/dashboard/stats`, {
            headers: getAuthHeaders()
        });
        const data = await response.json();
        if (data.success) {
            const stats = data.data;
            statsContainer.innerHTML = `
                <div class="row g-4 mb-5">
                    <div class="col-xl-2 col-md-4 col-6">
                        <div class="card border-0 shadow h-100">
                            <div class="card-body text-center">
                                <div class="h1 text-primary mb-2"><i class="fas fa-car"></i></div>
                                <h5>Total Cars</h5>
                                <h2 class="text-primary fw-bold">${stats.totalCars || 0}</h2>
                            </div>
                        </div>
                    </div>
                    <div class="col-xl-2 col-md-4 col-6">
                        <div class="card border-0 shadow h-100">
                            <div class="card-body text-center">
                                <div class="h1 text-success mb-2"><i class="fas fa-check-circle"></i></div>
                                <h5>Available</h5>
                                <h2 class="text-success fw-bold">${stats.availableCars || 0}</h2>
                            </div>
                        </div>
                    </div>
                    <div class="col-xl-2 col-md-4 col-6">
                        <div class="card border-0 shadow h-100 ">
                            <div class="card-body text-center">
                                <div class="h1 text-danger mb-2"><i class="fas fa-shopping-cart"></i></div>
                                <h5>Sold</h5>
                                <h2 class="text-danger fw-bold">${stats.soldCars || stats.rentedCars || 0}</h2>
                            </div>
                        </div>
                    </div>
                    <div class="col-xl-2 col-md-4 col-6">
                        <div class="card border-0 shadow h-100">
                            <div class="card-body text-center">
                                <div class="h1 text-warning mb-2"><i class="fas fa-dollar-sign"></i></div>
                                <h5>Revenue</h5>
                                <h2 class="text-warning fw-bold">$${ (stats.totalRevenue || 0).toLocaleString()}</h2>
                            </div>
                        </div>
                    </div>
                    <div class="col-xl-2 col-md-4 col-6">
                        <div class="card border-0 shadow h-100">
                            <div class="card-body text-center">
                                <div class="h1 text-info mb-2"><i class="fas fa-clock"></i></div>
                                <h5>Pending</h5>
                                <h2 class="text-info fw-bold">${stats.pendingContracts || 0}</h2>
                            </div>
                        </div>
                    </div>
                    
                </div>
            `;
            Swal.close();
        } else {
            throw new Error(data.message || 'Failed to load stats');
        }
    } catch (error) {
        console.error('Failed to load stats:', error);
        Swal.fire({
            icon: 'error',
            title: 'Dashboard Load Failed',
            text: error.message
        });
    }
}
