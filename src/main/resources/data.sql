-- Set the search path to use tayduong_order schema
SET search_path TO tayduong_order;

-- Insert departments
INSERT INTO tayduong_order.departments (department_id, department_name) VALUES
('DEP-001', 'Accounting'),
('DEP-002', 'Warehouse'),
('DEP-003', 'Sales');

-- Insert employees
INSERT INTO tayduong_order.employees (employee_id, username, employee_name, department_id, department_name) VALUES
('EMP-001', 'nguyenvana', 'Nguyen Van A', 'DEP-001', 'Accounting'),
('EMP-002', 'nguyenvanb', 'Nguyen Van B', 'DEP-002', 'Warehouse'),
('EMP-003', 'nguyenvanc', 'Nguyen Van C', 'DEP-003', 'Sales');

-- Insert customers
INSERT INTO tayduong_order.customers (customer_id, customer_name, address, city, tax_code, phone, is_inactive) VALUES
('CUST-001', 'Benh Vien Da Khoa Trung Uong', '123 Duong Lang', 'Ha Noi', '0123456789', '0987654321', false),
('CUST-002', 'Benh Vien Bach Mai', '456 Giai Phong', 'Ha Noi', '9876543210', '0123456789', false),
('CUST-003', 'Benh Vien Cho Ray', '789 Nguyen Chi Thanh', 'Ho Chi Minh', '5432109876', '0369852147', false);

-- Insert products
INSERT INTO tayduong_order.products (product_id, product_name, product_type, main_unit, is_inactive) VALUES
('MED-001', 'Paracetamol 500mg', 'MEDICINE', 'Box', false),
('MED-002', 'Amoxicillin 500mg', 'MEDICINE', 'Box', false),
('SUP-001', 'Disposable Syringe 5ml', 'MEDICAL_SUPPLIES', 'Box', false),
('SUP-002', 'Surgical Mask', 'MEDICAL_SUPPLIES', 'Box', false);
