INSERT INTO users (username, email, password, role)
VALUES
    ('john_doe', 'john.doe@example.com', '$2a$12$PAj3iEy87jxVBnszAH9k5uqmkoZo4HVkh2NR/nExSV6.1DO6yVSMO', 'USER'),
    ('jane_smith', 'jane.smith@example.com', '$2a$12$PAj3iEy87jxVBnszAH9k5uqmkoZo4HVkh2NR/nExSV6.1DO6yVSMO', 'ADMIN'),
    ('alice_jones', 'alice.jones@example.com', '$2a$12$PAj3iEy87jxVBnszAH9k5uqmkoZo4HVkh2NR/nExSV6.1DO6yVSMO', 'USER'),
    ('bob_brown', 'bob.brown@example.com', '$2a$12$PAj3iEy87jxVBnszAH9k5uqmkoZo4HVkh2NR/nExSV6.1DO6yVSMO', 'USER'),
    ('kelvin_kowalsky', 'kelvin.kowalsky@example.com', '$2a$12$PAj3iEy87jxVBnszAH9k5uqmkoZo4HVkh2NR/nExSV6.1DO6yVSMO', 'USER');

INSERT INTO products (name, description, price, stock)
VALUES
    ('Laptop', 'A powerful laptop for professionals', 7500, 10),
    ('Smartphone', 'A modern smartphone with great features', 5000.50, 25),
    ('Headphones', 'Noise-cancelling headphones', 199.99, 50),
    ('Smartwatch', 'A stylish smartwatch with fitness tracking', 1250, 30);

INSERT INTO cart_items (user_id, product_id, quantity)
VALUES
    (1, 1, 2),               -- John Doe added 2 Laptops
    (1, 2, 3),               -- John Doe added 3 Smartphone
    (2, 2, 1),               -- Jane Smith added 1 Smartphone
    (3, 3, 1),               -- Alice Jones added 1 Headphones
    (4, 4, 3);               -- Bob Brown added 3 Smartwatches

INSERT INTO orders (user_id, order_date, status)
VALUES
    (1, NOW(), 'PENDING'),
    (2, NOW(), 'PAID'),
    (3, NOW(), 'DELIVERED'),
    (4, NOW(), 'CANCELLED');

INSERT INTO order_items (order_id, product_id, product_name, product_description, product_price, total_price, quantity)
VALUES
    (1, 1, 'Laptop', 'A powerful laptop for professionals',7500, 15000, 2),                  -- John Doe ordered 2 Laptops
    (2, 2, 'Smartphone', 'A modern smartphone with great features', 5000.50, 5000.50, 1),        -- Jane Smith ordered 1 Smartphone
    (3, 3, 'Headphones', 'Noise-cancelling headphones', 199.99, 199.99, 1),                     -- Alice Jones ordered 1 Headphones
    (4, 4, 'Smartwatch', 'A stylish smartwatch with fitness tracking', 1250, 3750, 3);         -- Bob Brown ordered 3 Smartwatches