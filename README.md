<<<<<<< HEAD

# Car Showroom & Rental Management System

A comprehensive web-based application for managing car showrooms, inventory, and rental services. Built with a Spring Boot backend and a modern HTML/CSS/JS frontend.

## 🚀 Features

### 🔐 Authentication Module

- **User Registration:** Create personal accounts and customer profiles.
- **User Login:** Role-based access for Customers, Employees, and Admins.
- **Session Management:** Secure access to personalized dashboards.

### 🚗 Car Management

- **Inventory Tracking:** Manage car specifications (Company, Model, Year, Color).
- **Physical Unit Management:** Track individual units across different branches.
- **Dynamic Pricing:** Maintain price history for accurate contract generation.

### 🏢 Branch & Employee Management

- **Branch Operations:** Create and modify physical showroom/rental locations.
- **Employee Onboarding:** Manage employee records, job assignments, and salary history.
- **Supervision:** Track employee hierarchy and reporting structures.

### 🔍 Search & Renting Process

- **Advanced Search:** Filter cars by company, model, color, or location.
- **Rental Contracts:** Streamlined booking process for customers.

## 🛠️ Technology Stack

- **Backend:** [Java](https://www.java.com/) with [Spring Boot](https://spring.io/projects/spring-boot)
- **Frontend:** HTML5, CSS3, JavaScript
- **Database:** SQL (Schema included in `schema_creation.sql`)
- **Build Tool:** Maven

## 📂 Project Structure

- `frontend/`: Contains all client-side assets (HTML, CSS, JS).
- `src/`: Java source code for the Spring Boot application.
- `schema_creation.sql`: SQL script for database setup.
- `use_case_descriptions.md`: Detailed functional requirements.

## ⚙️ Setup & Installation

1. **Database Setup:**
   - Execute the `schema_creation.sql` script in your SQL environment to create the necessary tables and relationships.
2. **Backend Configuration:**
   - Update `src/main/resources/application.properties` with your database credentials.
   - Run the application using Maven: `./mvnw spring-boot:run`
3. **Frontend Access:**
   - Open `frontend/index.html` in your browser or serve it using a local web server.

## 📄 License

# This project is open-source and available for educational purposes.

# Car Showroom Frontend

## Project Description

A comprehensive frontend for a car showroom based on SQL database. The interface is built using HTML, CSS, and JavaScript with 3D effects and modern responsive design.

## Features

### 🚗 **Car Management**

- Display all available cars with complete details
- Filter by company, year, and search functionality
- Show available colors and prices
- View available branches for each car

### 🏢 **Branch Management**

- Display all showroom branches
- Address and contact information
- Show available cars at each branch

### 👥 **Employee Management**

- Display team members
- Job titles and personal information
- Organized by branches

### 👤 **Customer Management**

- Table with all customers
- Contact information and addresses
- Easy search and browsing

### 📄 **Contract Management**

- Display all executed contracts
- Car, customer, and employee details
- Payment information and dates

## Technologies Used

- **HTML5** - Basic page structure
- **CSS3** - Styling and 3D effects
- **JavaScript (ES6+)** - Interactive functionality
- **Font Awesome** - Icons
- **Responsive Design** - All device compatibility

## Database Structure

The frontend is built based on the following schema:

### Main Tables:

- **company** - Car companies
- **model** - Car models
- **year\_of\_manufacture** - Manufacturing years
- **Car** - Cars
- **city** - Cities
- **Branch** - Branches
- **car\_branch** - Car distribution across branches
- **Car\_Price\_History** - Car prices
- **color** - Colors
- **car\_color** - Car colors
- **employee** - Employees
- **customer** - Customers
- **Contract** - Contracts

## Technical Features

### 🎨 **Design**

- Modern and elegant design
- Beautiful color gradients
- Professional hover effects
- Font Awesome icons

### 🎭 **Animations**

- 3D rotating car on homepage
- Smooth animations
- Professional transitions
- Progressive content loading

### 📱 **Compatibility**

- Responsive for all screen sizes
- Mobile-friendly sidebar menu
- Scrollable tables
- Adaptive design

### 🔍 **Search and Filter**

- Real-time car search
- Multi-level filtering
- Instant results
- User-friendly interface

## How to Use

1. **Open site**: Open `index.html` in your browser
2. **Navigation**: Use the top menu to navigate between sections
3. **Search**: Use the search bar and filters to find cars
4. **Details**: Click on any car or branch to view full details

## Files

```
car_showRoom/
├── index.html          # Main page
├── styles.css          # Styling file
├── script.js           # JavaScript file
├── README.md           # Documentation file
└── car_showroom.sql    # Database file
```

## Customization

### Adding New Data

You can modify the data in `script.js` in the `sampleData` section:

```javascript
// Add new company
sampleData.companies.push({
    company_id: 6,
    name: 'Tesla'
});

// Add new car
sampleData.cars.push({
    car_id: 7,
    company_id: 6,
    model_id: 11,
    year_id: 1
});
```

### Color Customization

You can modify colors in `styles.css`:

```css
:root {
    --primary-color: #667eea;
    --secondary-color: #764ba2;
    --accent-color: #ff6b6b;
}
```

## Browser Compatibility

- Chrome (Recommended)
- Firefox
- Safari
- Edge

## Future Updates

- [ ] Connect to real database
- [ ] Add login system
- [ ] Performance improvements
- [ ] More 3D effects
- [ ] Multi-language support

## Contact

For any inquiries or suggestions, please contact us:

- Email: <info@carshowroom.com>
- Phone: +20 123 456 789

***

**Note**: This project is frontend only. It needs to be connected to a backend to handle the real database.

> > > > > > > 1e825f0b9681f04db0b2f8d795974abd044696f5

