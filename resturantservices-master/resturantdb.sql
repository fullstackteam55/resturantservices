    CREATE DATABASE resturantapi
    USE resturantapi;
    

CREATE TABLE restaurant (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,  -- Unique identifier for each restaurant
    name VARCHAR(255) NOT NULL,            -- Name of the restaurant
    address VARCHAR(255) NOT NULL,         -- Address of the restaurant
    cuisinetype VARCHAR(100) NOT NULL,     -- Type of cuisine (e.g., Indian, Italian, etc.)
    createdat TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- Record creation timestamp
    updatedat TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP -- Record update timestamp
);

CREATE TABLE menuitem (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,  -- Unique identifier for each menu item
    name VARCHAR(255) NOT NULL,            -- Name of the menu item
    description TEXT,                      -- Description of the menu item
    price DECIMAL(10, 2) NOT NULL,         -- Price of the menu item
    restaurantid BIGINT NOT NULL,          -- Foreign key to the Restaurant table
    createdat TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- Record creation timestamp
    updatedat TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, -- Record update timestamp
    FOREIGN KEY (restaurantid) REFERENCES Restaurant(id) ON DELETE CASCADE
);

CREATE TABLE user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255),
    phonenumber VARCHAR(15) NOT NULL UNIQUE,
    role ENUM('CUSTOMER', 'RESTAURANT_OWNER', 'DELIVERY_PERSONNEL', 'ADMIN') NOT NULL,
    address TEXT,
    active BOOLEAN DEFAULT TRUE
);

CREATE TABLE customer (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL,
    paymentdetails VARCHAR(255),
    deliveryaddress VARCHAR(255),
    orderid BIGINT,
    userid BIGINT UNIQUE, -- One-to-One relationship with User table
    FOREIGN KEY (userid) REFERENCES user(id) ON DELETE CASCADE
);

CREATE TABLE restaurantowner (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL,
    userid BIGINT UNIQUE, -- One-to-One relationship with User table
    restaurantid BIGINT NULL, -- One-to-One relationship with Restaurant table
    FOREIGN KEY (userid) REFERENCES User(id) ON DELETE CASCADE,
    FOREIGN KEY (restaurantid) REFERENCES restaurant(id) ON DELETE CASCADE
);

CREATE TABLE deliverypersonnel (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL,
    contactdetails VARCHAR(100) NOT NULL,
    vehicletype VARCHAR(50),
    available BOOLEAN DEFAULT TRUE,
    userid BIGINT UNIQUE, -- One-to-One relationship with User table
    FOREIGN KEY (userid) REFERENCES user(id) ON DELETE CASCADE
);

CREATE TABLE orderitem (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    quantity INT NOT NULL,
    menuitemid BIGINT, -- Many-to-One relationship with MenuItem table
    FOREIGN KEY (menuitemid) REFERENCES menuitem(id) ON DELETE CASCADE
);

CREATE TABLE orders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    customerid BIGINT NOT NULL,
    orderitemid BIGINT NOT NULL,
    restaurantid BIGINT NOT NULL,
    deliverypersonnelid BIGINT NULL, -- Nullable column for optional relationship
    status VARCHAR(50) NOT NULL, -- OrderStatus ENUM as a string
    createdat TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    deliveredat TIMESTAMP NULL, -- Nullable column for delivered timestamp
    FOREIGN KEY (customerid) REFERENCES customer(id) ON DELETE CASCADE,
    FOREIGN KEY (orderitemid) REFERENCES orderitem(id) ON DELETE CASCADE,
    FOREIGN KEY (restaurantid) REFERENCES restaurant(id) ON DELETE CASCADE,
    FOREIGN KEY (deliverypersonnelid) REFERENCES deliverypersonnel(id) ON DELETE SET NULL
);

INSERT INTO user (username, password, email, phonenumber, role, address, active) VALUES
('customer1', 'encryptedPassword1', 'customer1@example.com', '1234567890', 'CUSTOMER', '123 Street, City', TRUE),
('owner1', 'encryptedPassword2', 'owner1@example.com', '0987654321', 'RESTAURANT_OWNER', '456 Avenue, City', TRUE),
('delivery1', 'encryptedPassword3', 'delivery1@example.com', '1122334455', 'DELIVERY_PERSONNEL', '789 Road, City', TRUE);


INSERT INTO restaurant (name, address, cuisinetype) VALUES
('Tasty Bites', '123 Food St', 'Indian'),
('Pasta Paradise', '456 Italian Blvd', 'Italian');


INSERT INTO menuitem (name, description, price, restaurantid) VALUES
('Butter Chicken', 'Creamy Indian curry', 12.50, 1),
('Paneer Tikka', 'Grilled cottage cheese', 8.50, 1),
('Margherita Pizza', 'Classic Italian pizza', 10.00, 2),
('Spaghetti Carbonara', 'Pasta with creamy sauce', 13.00, 2);


INSERT INTO customer (email, paymentdetails, deliveryaddress, userid) VALUES
('customer1@example.com', 'VISA 1234', '123 Street, City', 1);


INSERT INTO restaurantowner (email, userid, restaurantid) VALUES
('owner1@example.com', 2, 1);


INSERT INTO deliverypersonnel (email, contactdetails, vehicletype, available, userid) VALUES
('delivery1@example.com', '999-888-7777', 'Bike', TRUE, 3);




INSERT INTO orderitem (quantity, menuitemid) VALUES
(2, 1),  -- 2 Butter Chicken
(1, 4);  -- 1 Spaghetti Carbonara


INSERT INTO orders (customerid, orderitemid, restaurantid, deliverypersonnelid, status, createdat, deliveredat) VALUES
(1, 1, 1, 1, 'OUT_FOR_DELIVERY', '2024-11-16 10:00:00', NULL), -- Customer 1, Tasty Bites, Butter Chicken
(1, 2, 2, NULL, 'PLACED', '2024-11-16 10:30:00', NULL);        -- Customer 1, Pasta Paradise, Carbonara
