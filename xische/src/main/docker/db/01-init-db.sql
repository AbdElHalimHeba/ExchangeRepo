-- DROP TABLES IF EXISTS
DROP TABLE IF EXISTS bill_items CASCADE;
DROP TABLE IF EXISTS item CASCADE;
DROP TABLE IF EXISTS bill CASCADE;
DROP TABLE IF EXISTS users CASCADE;

-- CREATE TABLES
-- User Table
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(255) NOT NULL,
    tenure DATE
);
CREATE INDEX idx_user_username ON users(username);

-- Item Table
CREATE TABLE item (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    price NUMERIC(19,2) NOT NULL,
    currency VARCHAR(255) NOT NULL,
    category VARCHAR(255) NOT NULL
);

-- Bill Table
CREATE TABLE bill (
    id SERIAL PRIMARY KEY,
    currency VARCHAR(255) NOT NULL,
    discount_amount NUMERIC(19,2),
    amount NUMERIC(19,2) NOT NULL,
    user_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Bill-items Table
CREATE TABLE bill_items (
    bill_id BIGINT NOT NULL,
    item_id BIGINT NOT NULL,
    PRIMARY KEY (bill_id, item_id),
    FOREIGN KEY (bill_id) REFERENCES bill(id) ON DELETE CASCADE,
    FOREIGN KEY (item_id) REFERENCES item(id) ON DELETE CASCADE
);
