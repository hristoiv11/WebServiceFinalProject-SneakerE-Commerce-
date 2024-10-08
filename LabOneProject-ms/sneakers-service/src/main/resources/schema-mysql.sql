USE `sneakers-db`;


DROP TABLE IF EXISTS sneakers;


create table if not exists brand_founders (
    id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    brand_id INTEGER UNIQUE,
    f_name VARCHAR(36),
    l_name VARCHAR(36),
    dob DATE NOT NULL,
    country VARCHAR(36)
    );

DROP TABLE IF EXISTS brands;

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