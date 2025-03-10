INSERT INTO users (username, email, password, role)
VALUES
    ('john_doe', 'john.doe@example.com', 'password123', 'USER'),
    ('jane_smith', 'jane.smith@example.com', 'password123', 'ADMIN'),
    ('alice_jones', 'alice.jones@example.com', 'password123', 'USER'),
    ('bob_brown', 'bob.brown@example.com', 'password123', 'USER'),
    ('kelvin_kowalsky', 'kelvin.kowalsky@example.com', 'password123', 'USER');

INSERT INTO products (name, description, price, stock)
VALUES
    ('Laptop', 'A powerful laptop for professionals', 999.99, 10),
    ('Smartphone', 'A modern smartphone with great features', 499.99, 25),
    ('Headphones', 'Noise-cancelling headphones', 199.99, 50),
    ('Smartwatch', 'A stylish smartwatch with fitness tracking', 149.99, 30);

INSERT INTO cart_items (user_id, product_id, quantity)
VALUES
    (1, 1, 2),  -- John Doe added 2 Laptops
    (2, 2, 1),  -- Jane Smith added 1 Smartphone
    (3, 3, 1),  -- Alice Jones added 1 Headphones
    (4, 4, 3);  -- Bob Brown added 3 Smartwatches

INSERT INTO orders (user_id, order_date, status)
VALUES
    (1, NOW(), 'pending'),
    (2, NOW(), 'shipped'),
    (3, NOW(), 'delivered'),
    (4, NOW(), 'cancelled');

INSERT INTO order_items (order_id, product_id, quantity, price)
VALUES
    (1, 1, 2, 999.99),  -- John Doe ordered 2 Laptops
    (2, 2, 1, 499.99),  -- Jane Smith ordered 1 Smartphone
    (3, 3, 1, 199.99),  -- Alice Jones ordered 1 Headphones
    (4, 4, 3, 149.99);  -- Bob Brown ordered 3 Smartwatches