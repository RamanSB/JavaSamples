CREATE DATABASE zoo;
SHOW DATABASES;
USE zoo;

CREATE TABLE animals (
	id INTEGER AUTO_INCREMENT PRIMARY KEY,     
    name VARCHAR(100) DEFAULT NULL, 
    species VARCHAR(100) DEFAULT NULL,     
    birthday DATETIME DEFAULT NULL 
);

SELECT * FROM animals LIMIT 0, 1000;

INSERT INTO animals (name, species, birthday) VALUES  
('Max', 'Tiger', '2006-03-12 00:45:00'), 
('Asia', 'Elephant', '2014-07-26 07:32:00'), 
('Serena', 'Tiger', '1996-07-01 12:04:21');

INSERT INTO animals (species, name, birthday) VALUES ('Panda', 'Nelson', '2010-12-25 17:45');
INSERT INTO animals (name, species, birthday) VALUES ('Leo', 'Arctic Fox', '2016-02-29');
INSERT INTO animals (name, species, birthday) VALUES  ('Maino', 'Elephant', '2017-02-29');
INSERT INTO animals (name, species, birthday) VALUES  ('Maino', 'Elephant', '2019-02-1');

