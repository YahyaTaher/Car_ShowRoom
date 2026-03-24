/**
 * Handle Login (UC-02)
 */
async function login(credentials) {
    try {
        const response = await fetch(`${API_BASE_URL}/login`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(credentials)
        });

        if (!response.ok) throw new Error('Invalid credentials');

        const data = await response.json();
        localStorage.setItem('car_rental_user', JSON.stringify(data.user));
        localStorage.setItem('car_rental_token', data.token);
        localStorage.setItem('car_rental_role', data.user.roleName);

        window.location.href = data.user.roleName === 'Admin' ? 'dashboard.html' : 'cars.html';
    } catch (error) {
        showAlert('alert-error', error.message);
    }
}

/**
 * Handle Registration (UC-01)
 */
async function register(userData) {
    try {
        const response = await fetch(`${API_BASE_URL}/register`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(userData)
        });

        if (!response.ok) throw new Error('Registration failed. Username or SSN may already exist.');

        showAlert('alert-success', 'Registration successful! Redirecting to login...');
        setTimeout(() => { window.location.href = 'login.html'; }, 2000);
    } catch (error) {
        showAlert('alert-error', error.message);
    }
}

/**
 * Logout
 */
function logout() {
    localStorage.removeItem('car_rental_user');
    localStorage.removeItem('car_rental_token');
    localStorage.removeItem('car_rental_role');
    window.location.href = 'index.html';
}
