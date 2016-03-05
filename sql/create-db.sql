connect 'jdbc:derby://localhost:1527/testing;create=true;user=root;password=root';

DROP TABLE users_tests;
DROP TABLE tests_questions;
DROP TABLE answers;
DROP TABLE questions;
DROP TABLE tests;
DROP TABLE complexitys;
DROP TABLE subjects;
DROP TABLE users;
DROP TABLE roles;


CREATE TABLE roles (
	id INT NOT NULL PRIMARY KEY,
	name VARCHAR(20) NOT NULL UNIQUE
);
CREATE TABLE users(
	id INT NOT NULL GENERATED ALWAYS AS IDENTITY  PRIMARY KEY,
	role_id INT NOT NULL REFERENCES roles(id)
	ON DELETE CASCADE 
	ON UPDATE RESTRICT,
	login VARCHAR(20) NOT NULL UNIQUE,
	password VARCHAR(33) NOT NULL,
	email VARCHAR(40) NOT NULL UNIQUE, 
	firstName VARCHAR(20) NOT NULL,
	lastName VARCHAR(20) NOT NULL,
	status BOOLEAN NOT NULL DEFAULT FALSE,
	block_message VARCHAR(255),
	locale VARCHAR(5) DEFAULT 'ru'
);
CREATE TABLE subjects (
	id INT NOT NULL GENERATED ALWAYS AS IDENTITY  PRIMARY KEY,
	name VARCHAR(20) NOT NULL UNIQUE
);
CREATE TABLE complexitys (
	id INT NOT NULL PRIMARY KEY,
	name VARCHAR(20) NOT NULL UNIQUE
);
CREATE TABLE tests (
	id INT NOT NULL GENERATED ALWAYS AS IDENTITY  PRIMARY KEY,
	subject_id INT NOT NULL REFERENCES subjects(id)
	ON DELETE CASCADE 
	ON UPDATE RESTRICT,
	complexity_id INT REFERENCES complexitys(id)
	ON DELETE CASCADE 
	ON UPDATE RESTRICT,
	name VARCHAR(20) NOT NULL,
	timer INT NOT NULL
);

CREATE TABLE questions (
	id INT NOT NULL GENERATED ALWAYS AS IDENTITY  PRIMARY KEY,
	body VARCHAR(255) NOT NULL
);
CREATE TABLE answers (
	id INT NOT NULL GENERATED ALWAYS AS IDENTITY  PRIMARY KEY,
	question_id INT NOT NULL REFERENCES questions(id)
	ON DELETE CASCADE 
	ON UPDATE RESTRICT,
	body VARCHAR(255) NOT NULL,
	correct BOOLEAN NOT NULL DEFAULT FALSE
);
CREATE TABLE tests_questions(
	id INT NOT NULL GENERATED ALWAYS AS IDENTITY  PRIMARY KEY,
	test_id INT NOT NULL REFERENCES tests(id)
	ON DELETE CASCADE 
	ON UPDATE RESTRICT,
	question_id INT NOT NULL REFERENCES questions(id)
	ON DELETE CASCADE 
	ON UPDATE RESTRICT
);
CREATE TABLE users_tests (
	id INT NOT NULL GENERATED ALWAYS AS IDENTITY  PRIMARY KEY,
	user_id INT NOT NULL REFERENCES users(id)
	ON DELETE CASCADE 
	ON UPDATE RESTRICT,
	test_id INT NOT NULL REFERENCES tests(id)
	ON DELETE CASCADE 
	ON UPDATE RESTRICT,
	result FLOAT NOT NULL
);

INSERT INTO roles (id, name) VALUES (0, 'admin');
INSERT INTO roles (id, name) VALUES (1, 'user');

INSERT INTO subjects (name) VALUES ('Harry Potter');
INSERT INTO subjects (name) VALUES ('Star wars');
INSERT INTO subjects (name) VALUES ('Java');
INSERT INTO subjects (name) VALUES ('C++');

INSERT INTO complexitys (id,name) VALUES (0, 'Low');
INSERT INTO complexitys (id,name) VALUES (1, 'Middle');
INSERT INTO complexitys (id,name) VALUES (2, 'Hard');

INSERT INTO users (role_id, login, password, email, firstName, lastName) VALUES (0, 'admin' ,'21232f297a57a5a743894a0e4a801fc3', 'admin@mail.ru', 'Administator', 'Admin');
INSERT INTO users (role_id, login, password, email, firstName, lastName) VALUES (1, 'user' ,'ee11cbb19052e40b07aac0ca060c23ee', 'user@mail.ru', 'Igor', 'Bogdanov');
INSERT INTO users (role_id, login, password, email, firstName, lastName, status, block_message) VALUES (1, 'user1' ,'24c9e15e52afc47c225b757e7bee1f9d', 'user1@mail.ru', 'Заброкированый', 'Пользователь', 'true', 'smap');

INSERT INTO tests (subject_id, complexity_id, name, timer) VALUES (1,2,'Test 1',30);
INSERT INTO tests (subject_id, complexity_id, name, timer) VALUES (2,1,'Тест 2',25);

INSERT INTO questions (body) VALUES ('Как звали рыжего друга Гарри?');

INSERT INTO tests_questions (test_id, question_id) VALUES (1,1);

INSERT INTO answers (question_id, body, correct) VALUES (1, 'Рон', 'true');
INSERT INTO answers (question_id, body, correct) VALUES (1, 'Том', 'false');
INSERT INTO answers (question_id, body, correct) VALUES (1, 'Седрик', 'false');
INSERT INTO answers (question_id, body, correct) VALUES (1, 'Ron', 'true');

