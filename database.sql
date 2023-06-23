show databases;

create database colecao;

use colecao;

show tables;


CREATE TABLE filmes (
	id int PRIMARY KEY AUTO_INCREMENT,
	nome char(100),
	ano int(10)
);

CREATE TABLE musicas (
	id int PRIMARY KEY AUTO_INCREMENT,
	nome char(100),
	cantor char(100),
	ano int(10)
);

INSERT INTO filmes (nome, ano) 
	VALUES ('Poderoso Chefão', 1972);
	
INSERT INTO musicas (nome, cantor, ano) 
	VALUES ('Help', 'the beatles', 1965);

	
SELECT * FROM filmes;
SELECT * FROM musicas;