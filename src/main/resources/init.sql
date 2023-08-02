CREATE TABLE IF NOT EXISTS users (
                                     id INT PRIMARY KEY AUTO_INCREMENT,
                                     firstname VARCHAR(50) NOT NULL,
    lastname VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL,
    password VARCHAR(100) NOT NULL,
    role VARCHAR(50) NOT NULL
    );

CREATE TABLE IF NOT EXISTS mails (
                                     id INT PRIMARY KEY AUTO_INCREMENT,
                                     sender_id INT NOT NULL,
                                     title VARCHAR(100) NOT NULL,
    content VARCHAR(1000) NOT NULL,
    replyTo_id INT,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    senderdeleted BOOLEAN NOT NULL DEFAULT FALSE,
    FOREIGN KEY (sender_id) REFERENCES users(id),
    FOREIGN KEY (replyTo_id) REFERENCES mails(id)
    );

CREATE TABLE IF NOT EXISTS mail_recipients (
                                               mail_id INT NOT NULL,
                                               recipient_id INT NOT NULL,
                                               deleted BOOLEAN NOT NULL DEFAULT FALSE,
                                               PRIMARY KEY (mail_id, recipient_id),
    FOREIGN KEY (mail_id) REFERENCES mails(id),
    FOREIGN KEY (recipient_id) REFERENCES users(id)
    );

MERGE INTO USERS
    KEY(id)
VALUES (1, 'John', 'Smith', 'john_smith', '$2a$10$8gFX2IULVIQnN4bBYS535.EKGIfi.U/Zif5epPLmg20O7oAiiJo7W', 'USER'),
    (2, 'John', 'Doe', 'john_doe', '$2a$10$8gFX2IULVIQnN4bBYS535.EKGIfi.U/Zif5epPLmg20O7oAiiJo7W', 'APICALL'),
    (3, 'Jane', 'Doe', 'jane_doe', '$2a$10$8gFX2IULVIQnN4bBYS535.EKGIfi.U/Zif5epPLmg20O7oAiiJo7W', 'APICALL');



