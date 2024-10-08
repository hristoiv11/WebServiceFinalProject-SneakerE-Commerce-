USE `customers-db`;

DROP TABLE IF EXISTS customers;

create table if not exists customers (
    id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    customer_id VARCHAR(36) UNIQUE,
    customerFName VARCHAR(50),
    customerLName VARCHAR(50),
    number VARCHAR(50),
    email VARCHAR(50),
    customer_preferred_sneaker VARCHAR(50),
    customer_preferred_brand VARCHAR(50),
    customer_preferred_size VARCHAR(50),
    customer_status VARCHAR(50)
    );