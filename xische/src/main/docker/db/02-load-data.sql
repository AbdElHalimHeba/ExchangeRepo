-- LOAD DATA
-- Users
INSERT INTO users (username, password, role, tenure) 
VALUES ('Ali', '$2a$10$KIX/OhUP7E6Us5FZJb4xkOsTs2/t4wpjDeZwx7ME3tjTafIBGa9Qq', 'EMPLOYEE', '2022-03-09');
INSERT INTO users (username, password, role, tenure) 
VALUES ('Mona', '$2a$10$KIX/OhUP7E6Us5FZJb4xkOsTs2/t4wpjDeZwx7ME3tjTafIBGa9Qq', 'AFFILIATE', '2024-03-09');
INSERT INTO users (username, password, role, tenure) 
VALUES ('Ahmed', '$2a$10$KIX/OhUP7E6Us5FZJb4xkOsTs2/t4wpjDeZwx7ME3tjTafIBGa9Qq', 'REGULAR', '2024-03-09');

-- Items
INSERT INTO item (name, price, currency, category)  
VALUES ('Tea', 5.22, 'EUR', 'GROCERY');
INSERT INTO item (name, price, currency, category)  
VALUES ('TV', 450.50, 'USD', 'ELECTRONICS');