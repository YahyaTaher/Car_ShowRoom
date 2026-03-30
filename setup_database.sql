USE car_rental;

-- Create lookup tables and insert data first to avoid FK errors

CREATE TABLE IF NOT EXISTS company (
    company_id int PRIMARY KEY,
    name varchar(255) NOT NULL
);

INSERT INTO company (company_id, name) VALUES 
(1, 'BMW'), (2, 'Toyota'), (3, 'Ford'), (4, 'Tesla') ON DUPLICATE KEY UPDATE name=name;

CREATE TABLE IF NOT EXISTS model (
    model_id int PRIMARY KEY,
    model_name varchar(255) NOT NULL
);

INSERT INTO model (model_id, model_name) VALUES 
(1, 'X5'), (2, 'Camry'), (3, 'F-150'), (4, 'Model 3'), (5, '3 Series'), (6, 'Corolla') ON DUPLICATE KEY UPDATE model_name=model_name;

CREATE TABLE IF NOT EXISTS year_of_manufacture (
    year_id int PRIMARY KEY,
    year int NOT NULL
);

INSERT INTO year_of_manufacture (year_id, year) VALUES 
(1, 2020), (2, 2021), (3, 2022), (4, 2023), (5, 2024) ON DUPLICATE KEY UPDATE year=year;

CREATE TABLE IF NOT EXISTS city (
    city_id int PRIMARY KEY,
    city_name varchar(255) NOT NULL
);

INSERT INTO city (city_id, city_name) VALUES 
(1, 'New York'), (2, 'Los Angeles'), (3, 'Cairo'), (4, 'London') ON DUPLICATE KEY UPDATE city_name=city_name;

CREATE TABLE IF NOT EXISTS color (
    color_id int PRIMARY KEY,
    color_name varchar(255) NOT NULL
);

INSERT INTO color (color_id, color_name) VALUES 
(1, 'Red'), (2, 'Blue'), (3, 'Black'), (4, 'White'), (5, 'Silver') ON DUPLICATE KEY UPDATE color_name=color_name;

CREATE TABLE IF NOT EXISTS gender (
    gender_id int PRIMARY KEY,
    gender varchar(10) NOT NULL
);

INSERT INTO gender (gender_id, gender) VALUES 
(1, 'Male'), (2, 'Female') ON DUPLICATE KEY UPDATE gender=gender;

CREATE TABLE IF NOT EXISTS job (
    job_id int PRIMARY KEY,
    title varchar(255) NOT NULL
);

INSERT INTO job (job_id, title) VALUES 
(1, 'Sales Manager'), (2, 'Salesperson'), (3, 'Service Tech'), (4, 'Admin') ON DUPLICATE KEY UPDATE title=title;

CREATE TABLE IF NOT EXISTS roles (
    role_id int PRIMARY KEY,
    role_name varchar(255) NOT NULL
);

INSERT INTO roles (role_id, role_name) VALUES 
(1, 'Customer'), (2, 'Employee'), (3, 'Admin') ON DUPLICATE KEY UPDATE role_name=role_name;

CREATE TABLE IF NOT EXISTS payment_method (
    method_id int PRIMARY KEY,
    method varchar(100) NOT NULL
);

INSERT INTO payment_method (method_id, method) VALUES 
(1, 'Cash'), (2, 'Credit Card'), (3, 'Bank Transfer') ON DUPLICATE KEY UPDATE method=method;

-- Main tables
CREATE TABLE IF NOT EXISTS Car (
    car_id int PRIMARY KEY,
    company_id int NOT NULL,
    model_id int NOT NULL,
    year_id int NOT NULL,
    FOREIGN KEY (company_id) REFERENCES company(company_id),
    FOREIGN KEY (model_id) REFERENCES model(model_id),
    FOREIGN KEY (year_id) REFERENCES year_of_manufacture(year_id)
);

CREATE TABLE IF NOT EXISTS Branch (
    branch_id int PRIMARY KEY,
    street varchar(255) NOT NULL,
    city_id int NOT NULL,
    building_number int NOT NULL,
    contact_number int NOT NULL,
    FOREIGN KEY (city_id) REFERENCES city(city_id)
);

CREATE TABLE IF NOT EXISTS car_branch (
    car_id int NOT NULL,
    branch_id int NOT NULL,
    no_of_cars int NOT NULL,
    PRIMARY KEY (car_id, branch_id),
    FOREIGN KEY (car_id) REFERENCES Car(car_id),
    FOREIGN KEY (branch_id) REFERENCES Branch(branch_id)
);

CREATE TABLE IF NOT EXISTS Car_Price_History (
    car_price_id int PRIMARY KEY,
    car_id int NOT NULL,
    price float NOT NULL,
    price_date date NOT NULL,
    FOREIGN KEY (car_id) REFERENCES Car(car_id)
);

CREATE TABLE IF NOT EXISTS car_color (
    car_id int NOT NULL,
    color_id int NOT NULL,
    PRIMARY KEY (car_id, color_id),
    FOREIGN KEY (car_id) REFERENCES Car(car_id),
    FOREIGN KEY (color_id) REFERENCES color(color_id)
);

CREATE TABLE IF NOT EXISTS employee (
    SSN int PRIMARY KEY,
    fname varchar(255) NOT NULL,
    lname varchar(255) NOT NULL,
    street varchar(255) NOT NULL,
    city_id int NOT NULL,
    building_number int NOT NULL,
    phone_1 BIGINT NOT NULL,
    phone_2 BIGINT NOT NULL,
    gender_id int NOT NULL,
    birth_date date NOT NULL,
    job_id int NOT NULL,
    supervisor_id int NOT NULL,
    branch_id int NOT NULL,
    FOREIGN KEY (job_id) REFERENCES job(job_id),
    FOREIGN KEY (supervisor_id) REFERENCES employee(SSN),
    FOREIGN KEY (branch_id) REFERENCES Branch(branch_id),
    FOREIGN KEY (city_id) REFERENCES city(city_id),
    FOREIGN KEY (gender_id) REFERENCES gender(gender_id)
);

CREATE TABLE IF NOT EXISTS salary_history (
    job_id int NOT NULL,
    EMP_SSN int NOT NULL,
    salary float NOT NULL,
    comm_pct float NULL,
    date date NOT NULL,
    PRIMARY KEY (job_id, EMP_SSN),
    FOREIGN KEY (job_id) REFERENCES job(job_id),
    FOREIGN KEY (EMP_SSN) REFERENCES employee(SSN)
);

CREATE TABLE IF NOT EXISTS customer (
    SSN int PRIMARY KEY,
    fname varchar(255) NOT NULL,
    lname varchar(255) NOT NULL,
    street varchar(255) NOT NULL,
    city_id int NOT NULL,
    building_number int NOT NULL,
    phone_1 bigint NOT NULL,
    phone_2 bigint NOT NULL,
    gender_id int NOT NULL,
    birthdate date NOT NULL,
    FOREIGN KEY (city_id) REFERENCES city(city_id),
    FOREIGN KEY (gender_id) REFERENCES gender(gender_id)
);

CREATE TABLE IF NOT EXISTS accounts (
    account_id int PRIMARY KEY AUTO_INCREMENT,
    username varchar(100) NOT NULL UNIQUE,
    email varchar(255) NOT NULL UNIQUE,
    password_hash varchar(255) NOT NULL,
    role_id int NOT NULL,
    emp_ssn int NULL,
    cust_ssn int NULL,
    created_at datetime DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (emp_ssn) REFERENCES employee(SSN),
    FOREIGN KEY (cust_ssn) REFERENCES customer(SSN),
    FOREIGN KEY (role_id) REFERENCES roles(role_id)
);

CREATE TABLE IF NOT EXISTS Contract (
    contract_id int PRIMARY KEY AUTO_INCREMENT,
    car_id int NOT NULL,
    branch_id int NOT NULL,
    EMP_SSN int NOT NULL,
    CUST_SSN int NOT NULL,
    account_id int NOT NULL,
    method_id int NOT NULL,
    car_price_id int NOT NULL,
    contract_date date NOT NULL,
    FOREIGN KEY (car_id) REFERENCES Car(car_id),
    FOREIGN KEY (branch_id) REFERENCES Branch(branch_id),
    FOREIGN KEY (EMP_SSN) REFERENCES employee(SSN),
    FOREIGN KEY (CUST_SSN) REFERENCES customer(SSN),
    FOREIGN KEY (method_id) REFERENCES payment_method(method_id),
    FOREIGN KEY (car_price_id) REFERENCES Car_Price_History(car_price_id),
    FOREIGN KEY (account_id) REFERENCES accounts(account_id)
);

SHOW TABLES;
SELECT * FROM company;

