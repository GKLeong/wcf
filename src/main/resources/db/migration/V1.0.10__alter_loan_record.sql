ALTER TABLE loan_record
    CHANGE COLUMN is_processed paid BOOLEAN DEFAULT FALSE,
    ADD COLUMN posted      BOOLEAN DEFAULT FALSE AFTER paid,
    ADD COLUMN creator_id  INT       NOT NULL AFTER posted,
    ADD COLUMN create_time TIMESTAMP NOT NULL DEFAULT current_timestamp,
    ADD COLUMN update_time TIMESTAMP NOT NULL DEFAULT current_timestamp ON UPDATE current_timestamp;