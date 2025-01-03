DROP TABLE IF EXISTS logging;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS categories;

CREATE TABLE users(
    username VARCHAR(255) PRIMARY KEY,
    password VARCHAR(60),
    fingerprint VARCHAR(255)
);

CREATE TABLE categories(
	idCat SERIAL PRIMARY KEY,
	category CARCHAR(15)
);

INSERT INTO categories(category)
VALUES
	("registered"),
	("unregistered");

CREATE TABLE logging(
	idLog SERIAL PRIMARY KEY,
	time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	username VARCHAR(255) NOT NULL REFERENCES users(username),
	category INT REFERENCES categories(idCat),
	intruder VARCHAR(255) REFERENCES users(username)
);