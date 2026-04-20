// Base URL for backend APIs (Assuming Spring Boot)
const API_BASE_URL = 'http://localhost:8080/api';

/**
 * Common Header Component 
 */
function updateNavbar() {
    const navbar = document.getElementById('main-nav');
    if (!navbar) return;

    const user = JSON.parse(localStorage.getItem('car_showroom_user') || 'null');

    let navLinks = '';

    if (user) {
        if (user.role === 'ROLE_ADMIN') {
            navLinks = `
                <li><a href="/dashboard">Dashboard</a></li>
                <li><a href="/cars">Manage Inventory</a></li>
            `;
        } else {
            navLinks = `
                <li><a href="/cars">Search Cars</a></li>
                <li><a href="/purchase">My Purchases</a></li>
            `;
        }
        navLinks += `<li><a href="#" id="logout-btn">Logout (${user.username})</a></li>`;
    } else {
        navLinks = `
            <li><a href="/login">Login</a></li>
            <li><a href="/register">Register</a></li>
        `;
    }

    navbar.innerHTML = `
        <a href="/" class="logo">Car Showroom</a>
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

