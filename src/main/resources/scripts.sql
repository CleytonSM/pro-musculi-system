CREATE TABLE tb_users (
                          id INTEGER PRIMARY KEY AUTO_INCREMENT,
                          email VARCHAR(100) UNIQUE NOT NULL,
                          password VARCHAR(100) NOT NULL,
                          active TINYINT NOT NULL DEFAULT(1),
                          created_at TIMESTAMP
);

CREATE TABLE tb_authorities (
                                id INTEGER PRIMARY KEY AUTO_INCREMENT,
                                name VARCHAR(20) NOT NULL,
                                user_id INTEGER,
                                CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES tb_users(id)
);