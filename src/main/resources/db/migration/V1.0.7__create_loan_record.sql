CREATE TABLE loan_record
(
    id             INT PRIMARY KEY AUTO_INCREMENT,
    user_id        INT           NOT NULL,
    amount         DECIMAL(8, 0) NOT NULL,
    purpose        VARCHAR(255)  NOT NULL,
    payment_date   DATE,
    payment_method VARCHAR(255),
    is_processed   BOOLEAN DEFAULT FALSE,
    notes          VARCHAR(255)
);
