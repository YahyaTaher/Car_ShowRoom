// Main JavaScript file for Car Showroom

// Wait for DOM to load
document.addEventListener('DOMContentLoaded', function() {
    console.log('Car Showroom loaded!');
    
    // Initialize tooltips
    initTooltips();
    
    // Initialize form validation
    initFormValidation();
    
    // Add animation to cards
    animateCards();
});

// Initialize Bootstrap tooltips
function initTooltips() {
    var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
    var tooltipList = tooltipTriggerList.map(function(tooltipTriggerEl) {
        return new bootstrap.Tooltip(tooltipTriggerEl);
    });
}

// Form validation
function initFormValidation() {
    const forms = document.querySelectorAll('.needs-validation');
    
    Array.from(forms).forEach(form => {
        form.addEventListener('submit', event => {
            if (!form.checkValidity()) {
                event.preventDefault();
                event.stopPropagation();
            }
            form.classList.add('was-validated');
        }, false);
    });
}

// Animate cards on scroll
function animateCards() {
    const cards = document.querySelectorAll('.card');
    
    const observer = new IntersectionObserver((entries) => {
        entries.forEach(entry => {
            if (entry.isIntersecting) {
                entry.target.classList.add('fade-in');
            }
        });
    });
    
    cards.forEach(card => {
        observer.observe(card);
    });
}

// Search with debounce
function debounce(func, wait) {
    let timeout;
    return function executedFunction(...args) {
        const later = () => {
            clearTimeout(timeout);
            func(...args);
        };
        clearTimeout(timeout);
        timeout = setTimeout(later, wait);
    };
}

// Live search
const searchInput = document.querySelector('input[name="company"]');
if (searchInput) {
    searchInput.addEventListener('keyup', debounce(function(e) {
        if (this.value.length >= 2 || this.value.length === 0) {
            this.form.submit();
        }
    }, 500));
}

// Confirm delete
window.confirmDelete = function(carId) {
    if (confirm('Are you sure you want to delete this car?')) {
        document.getElementById('deleteForm').action = '/cars/delete/' + carId;
        document.getElementById('deleteForm').submit();
    }
};

// Format price
function formatPrice(price) {
    return new Intl.NumberFormat('en-US', {
        style: 'currency',
        currency: 'USD',
        minimumFractionDigits: 0
    }).format(price);
}

// Show loading spinner
function showLoading() {
    const spinner = document.createElement('div');
    spinner.className = 'spinner';
    spinner.id = 'loadingSpinner';
    document.body.appendChild(spinner);
}

// Hide loading spinner
function hideLoading() {
    const spinner = document.getElementById('loadingSpinner');
    if (spinner) {
        spinner.remove();
    }
}

// Sort cars
function sortCars(sortBy) {
    const url = new URL(window.location.href);
    url.searchParams.set('sort', sortBy);
    window.location.href = url.toString();
}

// Filter by price
function filterByPrice(min, max) {
    const url = new URL(window.location.href);
    url.searchParams.set('minPrice', min);
    url.searchParams.set('maxPrice', max);
    window.location.href = url.toString();
}