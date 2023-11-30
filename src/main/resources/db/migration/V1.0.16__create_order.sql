CREATE TABLE orders
(
    id           INT PRIMARY KEY AUTO_INCREMENT,
    client_id    INT,
    order_number VARCHAR(255),
    product      VARCHAR(255),
    order_date   DATE,
    quantity     DECIMAL(7, 0)      DEFAULT 0,
    price        DECIMAL(5, 3)      DEFAULT 0,
    amount       DECIMAL(8, 3)      DEFAULT 0,
    operator_id  INT,
    operator     VARCHAR(255),
    effective    BOOLEAN            DEFAULT FALSE,
    create_time  TIMESTAMP NOT NULL DEFAULT current_timestamp,
    update_time  TIMESTAMP NOT NULL DEFAULT current_timestamp ON UPDATE current_timestamp,

    FOREIGN KEY (client_id) REFERENCES clients (id),
    FOREIGN KEY (operator_id) REFERENCES users (id)
);
