CREATE TABLE attachments
(
    id               INT AUTO_INCREMENT PRIMARY KEY,
    uploader_id      INT          NOT NULL,
    file_name        VARCHAR(255) NOT NULL,
    file_type        VARCHAR(50)  NOT NULL,
    image_width      INT,
    image_height     INT,
    link             VARCHAR(255) NOT NULL,
    upload_timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (uploader_id) REFERENCES users (id)
);