// Base URL for backend APIs (Assuming Spring Boot)
const API_BASE_URL = 'http://localhost:8080/api';

/**
 * Common Header Component with Role-based Navigation
 */
function updateNavbar() {
    const navbar = document.getElementById('main-nav');
    if (!navbar) return;

    const user = JSON.parse(localStorage.getItem('car_rental_user'));
    const role = localStorage.getItem('car_rental_role');

    let navLinks = '';

    if (user) {
        if (role === 'Admin') {
            navLinks = `
                <li><a href="dashboard.html">Dashboard</a></li>
                <li><a href="inventory.html">Inventory</a></li>
                <li><a href="branches.html">Branches</a></li>
                <li><a href="employees.html">Staff</a></li>
            `;
        } else if (role === 'Customer') {
            navLinks = `
                <li><a href="cars.html">Search Cars</a></li>
                <li><a href="my-rentals.html">My Rentals</a></li>
            `;
        } else if (role === 'Employee') {
            navLinks = `
                <li><a href="dashboard.html">Dashboard</a></li>
                <li><a href="cars.html">Search Cars</a></li>
                <li><a href="contracts.html">Contracts</a></li>
            `;
        }
        navLinks += `<li><a href="#" id="logout-btn">Logout (${user.username})</a></li>`;
    } else {
        navLinks = `
            <li><a href="login.html">Login</a></li>
            <li><a href="register.html">Register</a></li>
        `;
    }

    navbar.innerHTML = `
        <a href="index.html" class="logo">CarRental Pro</a>
        <ul class="nav-links">
            ${navLinks}
        </ul>
    `;

    const logoutBtn = document.getElementById('logout-btn');
    if (logoutBtn) {
        logoutBtn.addEventListener('click', (e) => {
            e.preventDefault();
            logout();
        });
    }
}

/**
 * Alert Helper
 */
function showAlert(id, message) {
    const alert = document.getElementById(id);
    if (alert) {
        alert.innerText = message;
        alert.style.display = 'block';
        setTimeout(() => { alert.style.display = 'none'; }, 5000);
    }
}

// Global initialization
document.addEventListener('DOMContentLoaded', () => {
    updateNavbar();
});
