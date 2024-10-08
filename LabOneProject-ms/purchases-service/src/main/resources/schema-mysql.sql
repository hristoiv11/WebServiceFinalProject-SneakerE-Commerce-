/*
USE `laboneproject-db`;

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

create table if not exists inventories (
    id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    inventory_id VARCHAR(36) UNIQUE,
    available_level VARCHAR(36),
    restock_level VARCHAR(36)
    );

create table if not exists brand_founders (
    id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    brand_id INTEGER UNIQUE,
    f_name VARCHAR(36),
    l_name VARCHAR(36),
    dob DATE NOT NULL,
    country VARCHAR(36)
    );

create table if not exists brands (
    id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    brand_id VARCHAR(36) UNIQUE,
    name VARCHAR(50),
    associated_celebrity VARCHAR(50),
    location_of_main_headquarters VARCHAR(50),
    description VARCHAR(50)
    );

create table if not exists sneakers (
    id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    sneaker_id VARCHAR(36) UNIQUE,
    model VARCHAR(36),
    price VARCHAR(36),
    size INTEGER,
    color VARCHAR(36),
    release_year VARCHAR(36),
    available_store VARCHAR(36),
    description VARCHAR(36),
    type VARCHAR(36)
    );


 create table if not exists shipping_address(
    order_id INTEGER,
    street VARCHAR(36),
    city VARCHAR(36),
    state VARCHAR(36),
    country VARCHAR(36),
    postal_code VARCHAR(36)
);

create table if not exists orders (
    id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    order_id VARCHAR(36) UNIQUE,
    inventory_id VARCHAR(36) UNIQUE,
    customer_id VARCHAR(36) UNIQUE,
    sneaker_id VARCHAR(36) UNIQUE,
    order_status VARCHAR(50),
    quantity_bought INTEGER

    );


 */