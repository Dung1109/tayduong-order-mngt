--  use schema tayduong_order
CREATE SCHEMA IF NOT EXISTS tayduong_order;

SET search_path TO tayduong_order;

-- Drop existing tables if they exist
DROP TABLE IF EXISTS order_details CASCADE;
DROP TABLE IF EXISTS order_status_history CASCADE;
DROP TABLE IF EXISTS orders CASCADE;
DROP TABLE IF EXISTS products CASCADE;
DROP TABLE IF EXISTS employees CASCADE;
DROP TABLE IF EXISTS departments CASCADE;
DROP TABLE IF EXISTS customers CASCADE;

-- Drop existing types if they exist
DROP TYPE IF EXISTS order_status CASCADE;
DROP TYPE IF EXISTS product_type CASCADE;

-- Create enum for product type
CREATE TYPE product_type AS ENUM (
    'MEDICINE',
    'MEDICAL_SUPPLIES',
    'OTHER'
    );

-- Create departments table
CREATE TABLE departments
(
    department_id   VARCHAR(20) PRIMARY KEY,
    department_name VARCHAR(100) NOT NULL
);

-- Create employees table
CREATE TABLE employees
(
    employee_id     VARCHAR(20) PRIMARY KEY,
    username        VARCHAR(50)  NOT NULL UNIQUE CHECK (username ~ '^[A-Za-z]+[A-Za-z0-9]+$'),
    employee_name   VARCHAR(100) NOT NULL,
    manager_id      VARCHAR(20) REFERENCES employees (employee_id),
    manager_name    VARCHAR(100),
    department_id   VARCHAR(20) REFERENCES departments (department_id),
    department_name VARCHAR(100),
    CONSTRAINT fk_manager FOREIGN KEY (manager_id) REFERENCES employees (employee_id)
);

-- Create customers table
CREATE TABLE customers
(
    customer_id   VARCHAR(20) PRIMARY KEY,
    customer_name VARCHAR(200) NOT NULL,
    address       TEXT,
    city          VARCHAR(100),
    tax_code      VARCHAR(50),
    phone         VARCHAR(20),
    is_inactive   BOOLEAN DEFAULT FALSE
);

-- Create products table
CREATE TABLE products
(
    product_id   VARCHAR(20) PRIMARY KEY,
    product_name VARCHAR(200) NOT NULL,
    product_type product_type NOT NULL,
    main_unit    VARCHAR(50)  NOT NULL,
    is_inactive  BOOLEAN DEFAULT FALSE
);

-- Create orders table
CREATE TABLE orders
(
    order_id        VARCHAR(20) PRIMARY KEY,
    customer_id     VARCHAR(20)    NOT NULL REFERENCES customers (customer_id),
    customer_name   VARCHAR(200)   NOT NULL,
    order_date      DATE           NOT NULL,
    delivery_date   DATE,
    payment_date    DATE,
    payment_terms   TEXT,
    total_amount    DECIMAL(18, 2) NOT NULL  DEFAULT 0,
    discount_amount DECIMAL(18, 2) NOT NULL  DEFAULT 0,
    vat_amount      DECIMAL(18, 2) NOT NULL  DEFAULT 0,
    total_payment   DECIMAL(18, 2) NOT NULL  DEFAULT 0,
    status          VARCHAR(50)    NOT NULL  CHECK (status IN ('DRAFT', 'PENDING_ACCOUNTANT_APPROVAL', 'PENDING_WAREHOUSE_APPROVAL', 'PENDING_DELIVERY', 'COMPLETED')),
    created_at      TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT positive_amounts CHECK (
        total_amount >= 0 AND
        discount_amount >= 0 AND
        vat_amount >= 0 AND
        total_payment >= 0
        )
);

-- Create order details table
CREATE TABLE order_details
(
    order_detail_id     VARCHAR(20) PRIMARY KEY,
    order_id            VARCHAR(20)    NOT NULL REFERENCES orders (order_id),
    product_id          VARCHAR(20)    NOT NULL REFERENCES products (product_id),
    product_name        VARCHAR(200)   NOT NULL,
    batch_number        VARCHAR(50),
    expiry_date         DATE,
    unit                VARCHAR(50)    NOT NULL,
    quantity            DECIMAL(18, 2) NOT NULL,
    unit_price_with_vat DECIMAL(18, 2) NOT NULL,
    amount              DECIMAL(18, 2) NOT NULL,
    discount_percentage DECIMAL(5, 2)  DEFAULT 0,
    discount_amount     DECIMAL(18, 2) DEFAULT 0,
    vat_percentage      DECIMAL(5, 2)  DEFAULT 0,
    vat_amount          DECIMAL(18, 2) DEFAULT 0,
    total_payment       DECIMAL(18, 2) NOT NULL,
    employee_id         VARCHAR(20)    NOT NULL REFERENCES employees (employee_id),
    employee_name       VARCHAR(100)   NOT NULL,
    customer_id         VARCHAR(20)    NOT NULL REFERENCES customers (customer_id),
    CONSTRAINT positive_values CHECK (
        quantity > 0 AND
        unit_price_with_vat >= 0 AND
        amount >= 0 AND
        discount_percentage >= 0 AND
        discount_amount >= 0 AND
        vat_percentage >= 0 AND
        vat_amount >= 0 AND
        total_payment >= 0
        )
);

-- Create order status history table
CREATE TABLE order_status_history
(
    history_id BIGSERIAL PRIMARY KEY,
    order_id   VARCHAR(20)  NOT NULL REFERENCES orders (order_id),
    old_status VARCHAR(50),
    new_status VARCHAR(50) NOT NULL,
    changed_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    changed_by VARCHAR(20) REFERENCES employees (employee_id),
    notes      TEXT
);

-- Create trigger to update timestamps
CREATE OR REPLACE FUNCTION update_updated_at_column()
    RETURNS TRIGGER
    LANGUAGE plpgsql
AS '
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
';

CREATE TRIGGER update_orders_updated_at
    BEFORE UPDATE
    ON orders
    FOR EACH ROW
EXECUTE FUNCTION update_updated_at_column();

-- Create indexes for better performance
CREATE INDEX idx_orders_customer ON orders (customer_id);
CREATE INDEX idx_orders_status ON orders (status);
CREATE INDEX idx_order_details_order ON order_details (order_id);
CREATE INDEX idx_order_details_product ON order_details (product_id);
CREATE INDEX idx_order_details_employee ON order_details (employee_id);
CREATE INDEX idx_employees_department ON employees (department_id);
CREATE INDEX idx_employees_manager ON employees (manager_id);
