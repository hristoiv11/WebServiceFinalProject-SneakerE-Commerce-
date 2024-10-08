
DROP TABLE IF EXISTS inventories;

create table if not exists inventories (
    id SERIAL,
    inventory_id VARCHAR(36) UNIQUE,
    available_level VARCHAR(36),
    restock_level VARCHAR(36),
    PRIMARY KEY (id)
);
