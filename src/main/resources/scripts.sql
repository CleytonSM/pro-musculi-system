CREATE TABLE users (
                       id INTEGER PRIMARY KEY AUTO_INCREMENT,
                       email VARCHAR(100) UNIQUE NOT NULL,
                       password VARCHAR(100) NOT NULL,
                       created_at TIMESTAMP
);

CREATE TABLE authorities (
                             id INTEGER PRIMARY KEY AUTO_INCREMENT,
                             name VARCHAR(20) NOT NULL,
                             user_id INTEGER,
                             CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES users(id)
);

ALTER TABLE users
ADD COLUMN active TINYINT NOT NULL DEFAULT(1);