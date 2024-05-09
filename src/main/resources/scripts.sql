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

CREATE TABLE tb_clients (
                            id INTEGER AUTO_INCREMENT,
                            gym_plan_id INTEGER,
                            name VARCHAR(60) NOT NULL,
                            email VARCHAR(100) UNIQUE,
                            phone VARCHAR(11) UNIQUE,
                            active TINYINT NOT NULL DEFAULT(1),
                            created_at TIMESTAMP,
                            CONSTRAINT pk_tb_clients_id PRIMARY KEY (id),
                            CONSTRAINT fk_tb_clients_gym_plan_id FOREIGN KEY (gym_plan_id)
                                REFERENCES tb_gym_plan(id)
);

CREATE TABLE tb_gym_plan (
                             id INTEGER AUTO_INCREMENT,
                             name VARCHAR(40) NOT NULL,
                             price DECIMAL(5,2) NOT NULL,
                             duration INTEGER,
                             CONSTRAINT pk_tb_gym_plan_id PRIMARY KEY (id)
);

CREATE TABLE tb_instructors (
                                id 			INTEGER AUTO_INCREMENT,
                                name 		VARCHAR(60) NOT NULL,
                                salary		DECIMAL(6,2) NOT NULL,
                                cpf 		CHAR(11) NOT NULL UNIQUE,
                                active		TINYINT,
                                created_at	TIMESTAMP,
                                phone		CHAR(11),
                                CONSTRAINT pk_tb_instructors_id PRIMARY KEY (id)
);

CREATE TABLE tb_dance_classes (
                                  id 			INTEGER AUTO_INCREMENT,
                                  client_id		INTEGER,
                                  instructor_id	INTEGER NOT NULL,
                                  name			VARCHAR(60),
                                  dt_start		TIMESTAMP UNIQUE,
                                  dt_end		TIMESTAMP UNIQUE,
                                  description 	VARCHAR(100),
                                  CONSTRAINT pk_tb_dance_classes_id PRIMARY KEY (id),
                                  CONSTRAINT fk_tb_dance_classes_client_id FOREIGN KEY (client_id)
                                      REFERENCES tb_clients(id),
                                  CONSTRAINT fk_tb_dance_classes_instructor_id FOREIGN KEY (instructor_id)
                                      REFERENCES tb_instructors(id)
);