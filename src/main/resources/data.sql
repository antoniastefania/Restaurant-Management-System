-- Example admin user and regular user with bcrypt-hashed passwords
INSERT INTO users (email, password, full_name, role, enabled)
VALUES ('admin@example.com', '$2a$10$uN76v8W9OTn/PYUXZQ73gOUgcQRcm2kG7dcSvpkgF/278F0p3ECk2', 'Admin User', 'ADMIN',
        TRUE),
       ('user@example.com', '$2a$10$uN76v8W9OTn/PYUXZQ73gOQq8hFzB.2nZXQZZeKVBQuNNNx4pCi4.', 'Normal User', 'USER',
        TRUE);

-- Example menu items
INSERT INTO menu_item (name, description, price, category, ingredients, image_url)
VALUES ('Steak', 'Grilled beef steak', 25.00, 'Main Course', 'Beef, salt, pepper', 'https://example.com/steak.jpg'),
       ('Salad', 'Fresh vegetable salad', 8.50, 'Appetizer', 'Lettuce, tomato, cucumber',
        'https://example.com/salad.jpg'),
       ('Pasta', 'Creamy pasta', 12.50, 'Main Course', 'Pasta, cream, cheese', 'https://example.com/pasta.jpg'),
       ('Cheesecake', 'Sweet cheesecake', 6.00, 'Dessert', 'Cheese, sugar, crust',
        'https://example.com/cheesecake.jpg'),
       ('Coke', 'Refreshing drink', 2.00, 'Drink', 'Coke', 'https://example.com/coke.jpg');
