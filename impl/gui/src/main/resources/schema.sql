DROP DATABASE IF EXISTS jade;

CREATE DATABASE jade;

USE jade;


CREATE TABLE agent (
	aid VARCHAR(30) PRIMARY KEY,
	content TEXT NOT NULL
);


CREATE TABLE result (
    execution INT,
    round INT,
    value DOUBLE,
    time TIMESTAMP
);