# **Car Rental Management System: Use Case Descriptions**

## **1. Authentication Module**

### **UC-01: User Registration**
- **Actor(s):** Potential Customer
- **Description:** Allows a new user to create a personal account and customer profile in the system.
- **Preconditions:** The user is not logged in and does not have an existing account.
- **Postconditions:** A new record is created in the `customer` and `accounts` tables.
- **Main Flow:**
    1. User selects "Sign Up".
    2. System requests personal details (SSN, Name, Address, Phone, Birthdate, Gender).
    3. User provides details and chooses a username, email, and password.
    4. System validates that the SSN and Username are unique.
    5. System creates a Customer profile and a linked Account with the "Customer" role.
    6. System confirms successful registration.
- **Alternative Flows:**
    - **Duplicate User:** If the SSN or Username already exists, the system displays an error and prompts for correction.

### **UC-02: User Login**
- **Actor(s):** Customer, Employee, Admin
- **Description:** Authenticates users to grant access to system features based on their role.
- **Preconditions:** The user has a registered account.
- **Postconditions:** User is granted a session token with role-based permissions.
- **Main Flow:**
    1. User enters username and password.
    2. System verifies credentials against the `accounts` table.
    3. System identifies the `role_id` and associated entity (`emp_ssn` or `cust_ssn`).
    4. System redirects the user to their respective dashboard (Customer Portal or Admin Panel).
- **Alternative Flows:**
    - **Invalid Credentials:** System displays "Invalid username or password" and denies access.

---

## **2. Car Management Module**

### **UC-03: Manage Car Inventory**
- **Actor(s):** Admin
- **Description:** Allows the Admin to add, update, or remove car specifications and physical units.
- **Preconditions:** Admin is logged in.
- **Postconditions:** `Car`, `car_branch`, and `car_color` tables are updated.
- **Main Flow:**
    1. Admin selects "Manage Inventory".
    2. Admin enters car details: Company, Model, Year, and Colors.
    3. Admin assigns the car to a specific Branch and sets the available quantity (`no_of_cars`).
    4. System saves the car details and updates the inventory levels.
- **Alternative Flows:**
    - **Invalid Specification:** If the Model or Company doesn't exist, Admin is prompted to create them first.

### **UC-04: Update Car Pricing**
- **Actor(s):** Admin
- **Description:** Records a new price for a specific car model for rental purposes.
- **Preconditions:** Admin is logged in; the car exists in the system.
- **Postconditions:** A new entry is added to the `Car_Price_History` table.
- **Main Flow:**
    1. Admin selects a car from the inventory.
    2. Admin enters a new rental price and the effective date.
    3. System stores the history to ensure past contracts remain accurate.

---

## **3. Branch & Employee Management**

### **UC-05: Manage Branches**
- **Actor(s):** Admin
- **Description:** Creation and modification of physical showroom/rental locations.
- **Preconditions:** Admin is logged in.
- **Postconditions:** `Branch` table is updated.
- **Main Flow:**
    1. Admin enters branch location (City, Street, Building) and contact number.
    2. System validates the city exists in the `city` table.
    3. System creates the branch.

### **UC-06: Employee Onboarding & Salary Management**
- **Actor(s):** Admin
- **Description:** Managing employee records, job assignments, and salary history.
- **Preconditions:** Admin is logged in.
- **Postconditions:** `employee` and `salary_history` tables are updated.
- **Main Flow:**
    1. Admin enters employee personal data and assigns a `job_id` and `branch_id`.
    2. Admin sets the supervisor (linking to another `employee` SSN).
    3. Admin enters initial salary and commission percentage.
    4. System saves the record and logs the initial salary in history.

---

## **4. Car Search & Renting Process**

### **UC-07: Search & Filter Cars**
- **Actor(s):** Customer, Employee
- **Description:** Finding available cars based on company, model, color, or location.
- **Preconditions:** None (for public search) or User logged in.
- **Main Flow:**
    1. User selects search criteria (e.g., "BMW", "Red", "New York Branch").
    2. System queries `Car`, `car_branch`, and `car_color` tables.
    3. System displays a list of matching cars with their current prices.
- **Alternative Flows:**
    - **No Results:** System suggests broader criteria or different branches.

### **UC-08: Create Rental Contract (Booking)**
- **Actor(s):** Customer (Initiates), Employee (Finalizes)
- **Description:** The core process of renting a car and generating a legal contract.
- **Preconditions:** Customer is registered; Car is available in the selected branch.
- **Postconditions:** A record is created in the `Contract` table; Inventory is decremented.
- **Main Flow:**
    1. Customer selects a car and branch.
    2. Employee verifies the Customer's SSN and the Car's availability.
    3. System retrieves the latest price from `Car_Price_History`.
    4. User selects a `payment_method`.
    5. System generates a `Contract` record linking all parties and the specific car.
    6. System updates `car_branch.no_of_cars` to reflect the rental.
- **Alternative Flows:**
    - **Out of Stock:** System blocks contract creation if `no_of_cars` is zero.

---

## **5. Summary List of Use Cases**

| ID | Use Case Name | Primary Actor | Module |
| :--- | :--- | :--- | :--- |
| UC-01 | User Registration | Customer | Authentication |
| UC-02 | User Login | All Roles | Authentication |
| UC-03 | Manage Car Inventory | Admin | Car Management |
| UC-04 | Update Car Pricing | Admin | Car Management |
| UC-05 | Manage Branches | Admin | Branch Management |
| UC-06 | Employee Onboarding | Admin | Employee Management |
| UC-07 | Search & Filter Cars | Customer/Employee | Search |
| UC-08 | Create Rental Contract | Customer/Employee | Renting Process |
| UC-09 | Manage User Roles | Admin | Account Management |

---

## **6. Use Case Diagram (Mermaid.js)**

You can copy the following code into any Mermaid-supported viewer (like GitHub, VS Code, or [Mermaid Live Editor](https://mermaid.live/)) to render the diagram.

```mermaid
usecaseDiagram
    actor Customer
    actor Employee
    actor Admin

    package "Car Rental Management System" {
        usecase "UC-01: User Registration" as UC01
        usecase "UC-02: User Login" as UC02
        usecase "UC-03: Manage Car Inventory" as UC03
        usecase "UC-04: Update Car Pricing" as UC04
        usecase "UC-05: Manage Branches" as UC05
        usecase "UC-06: Employee Onboarding" as UC06
        usecase "UC-07: Search & Filter Cars" as UC07
        usecase "UC-08: Create Rental Contract" as UC08
        usecase "UC-09: Manage User Roles" as UC09
        usecase "Select Payment Method" as SPM
    }

    Customer --> UC01
    Customer --> UC02
    Customer --> UC07
    Customer --> UC08

    Employee --> UC02
    Employee --> UC07
    Employee --> UC08

    Admin --> UC02
    Admin --> UC03
    Admin --> UC04
    Admin --> UC05
    Admin --> UC06
    Admin --> UC09

    UC08 ..> SPM : <<include>>
    UC08 ..> UC02 : <<include>>
    UC03 ..> UC04 : <<include>>
```

## **7. Suggestions for Use Case Diagram Layout**

1.  **System Boundary:** Draw a large rectangle labeled "Car Rental Management System".
2.  **Actors:** 
    - **Customer:** Place on the left.
    - **Employee:** Place on the right.
    - **Admin:** Place on the right (Employee can be a generalization of Admin, or separate).
3.  **Relationships:**
    - **Customer** connects to: *Register*, *Login*, *Search Cars*, *Initiate Rental*.
    - **Employee** connects to: *Login*, *Search Cars*, *Manage Customers*, *Finalize Contract*.
    - **Admin** connects to: *Login*, *Manage Cars*, *Manage Branches*, *Manage Employees*, *Manage Roles*.
4.  **Include/Extend:**
    - *Create Contract* **<<include>>** *Select Payment Method*.
    - *Create Contract* **<<include>>** *Login*.
    - *Manage Inventory* **<<include>>** *Update Pricing*.
