-- Seed data for demo (runs on startup)
INSERT IGNORE INTO branch (name, location) VALUES 
('Main Branch', 'NYC'),
('Downtown', 'LA');

INSERT IGNORE INTO cars (plate_number, brand, model, year, price_per_day, branch_id, status) VALUES 
('BMW001', 'BMW', '3 Series', 2023, 85.00, 1, 'AVAILABLE'),
('AUD002', 'Audi', 'A4', 2022, 75.00, 2, 'AVAILABLE'),
('MER003', 'Mercedes', 'C-Class', 2023, 90.00, 1, 'AVAILABLE');

INSERT IGNORE INTO users (username, password, role) VALUES 
('admin', '$2a$10$vovYq5f5mJ6X.7zQ9z6H7.vY/1yPzHh8O6uG5QZ.Z8S/zJp/t3zWq', 'ROLE_ADMIN');
