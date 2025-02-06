create database restaurant;
CREATE TABLE IF NOT EXISTS users
(
    id        BIGINT AUTO_INCREMENT PRIMARY KEY,
    email     VARCHAR(100) NOT NULL UNIQUE,
    password  VARCHAR(255) NOT NULL,
    full_name VARCHAR(100),
    role      VARCHAR(50)  NOT NULL,
    enabled   BOOLEAN      NOT NULL DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS menu_item
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(100)   NOT NULL,
    description TEXT,
    price       DECIMAL(10, 2) NOT NULL,
    category    VARCHAR(50),
    ingredients TEXT,
    image_url   VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS orders
(
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id      BIGINT      NOT NULL,
    total_amount DECIMAL(10, 2),
    status       VARCHAR(50) NOT NULL,
    created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_user_order FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS order_detail
(
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id     BIGINT         NOT NULL,
    menu_item_id BIGINT         NOT NULL,
    quantity     INT            NOT NULL,
    price        DECIMAL(10, 2) NOT NULL,
    CONSTRAINT fk_order_detail_order FOREIGN KEY (order_id) REFERENCES orders (id),
    CONSTRAINT fk_order_detail_menu FOREIGN KEY (menu_item_id) REFERENCES menu_item (id)
);

CREATE TABLE IF NOT EXISTS reservation
(
    id               BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id          BIGINT NOT NULL,
    reservation_date DATE   NOT NULL,
    reservation_time TIME   NOT NULL,
    number_of_people INT    NOT NULL,
    status           VARCHAR(50),
    CONSTRAINT fk_user_reservation FOREIGN KEY (user_id) REFERENCES users (id)
);
