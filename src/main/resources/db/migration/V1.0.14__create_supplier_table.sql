CREATE TABLE suppliers
(
    id                 INT PRIMARY KEY AUTO_INCREMENT,
    `name`             VARCHAR(255)  NOT NULL,
    contact_person     VARCHAR(15)   NOT NULL,
    phone              VARCHAR(15),
    address            VARCHAR(255),
    payment_percentage DECIMAL(3, 0) NOT NULL,
    comments           VARCHAR(255)           DEFAULT NULL,
    effective          BOOLEAN       NOT NULL DEFAULT TRUE,
    create_time        TIMESTAMP              DEFAULT CURRENT_TIMESTAMP,
    update_time        TIMESTAMP              DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
